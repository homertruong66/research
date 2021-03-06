package com.rms.rms.task_processor;

import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.ProductCreateModel;
import com.rms.rms.service.ProductService;
import com.rms.rms.service.UploadErrorService;
import com.rms.rms.service.ValidationService;
import com.rms.rms.service.model.Channel;
import com.rms.rms.service.model.UploadError;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class TaskProcessorImportProducts {

    private Logger logger = Logger.getLogger(TaskProcessorImportProducts.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UploadErrorService uploadErrorService;

    @Autowired
    private ValidationService validationService;

    private static final int maxColumn = 6;

    public void process(MultipartFile file, UserDto loggedUserDto) {
        logger.info("process: " + file);

        try {
            // validate file type base on content type
            if (file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    && file.getOriginalFilename().endsWith("xlsx")) {
                // put Authentication object to Spring Security Context
                Authentication authentication = TaskProcessorUtil.createAuthentication(loggedUserDto);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // process task
                importExcelFile(file, maxColumn);
            }
            else {
                throw new IllegalArgumentException(String.format("Couldn't handle file contentType: %s", file.getContentType()));
            }
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }


    // Utilities
    private void importExcelFile(MultipartFile file, int maxColumn) throws Exception {
        OPCPackage xlsxPackage = OPCPackage.open(file.getInputStream());
        ReadOnlySharedStringsTable stringsTable = new ReadOnlySharedStringsTable(xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
        while (iter.hasNext()) {
            InputStream stream = iter.next();
            processSheet(styles, stringsTable, new SheetHandler(maxColumn), stream);
            stream.close();
        }
    }

    private void handleException(Exception e) throws BusinessException {
        UploadError uploadError = new UploadError();
        uploadError.setError(e.getMessage());
        uploadErrorService.create(uploadError);
    }

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable stringsTable,
                              XSSFSheetXMLHandler.SheetContentsHandler sheetHandler,
                              InputStream sheetInputStream) throws Exception {
        DataFormatter formatter = new DataFormatter(true);
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, stringsTable, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        }
        catch (Exception e) {
            handleException(e);
        }
    }

    private class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        List<String> rowData = new ArrayList<>();
        private int currentRow;
        private int currentCol;
        private int maxColumn;

        public SheetHandler(int maxColumn) {
            currentRow = -1;
            currentCol = -1;
            this.maxColumn = maxColumn;
        }

        public void startRow(int rowNum) {
            currentRow = rowNum;
            currentCol = -1;
        }

        public void endRow(int rowNum) {
            // skip header
            if (rowNum == 0) {
                rowData.clear();
                return;
            }

            // miss any column value in current row ?
            for (int i = currentCol + 1; i < maxColumn; i++) {
                rowData.add("");
            }

            // create a Product based on row data
            try {
                ProductCreateModel createModel = new ProductCreateModel();
                String channelDomainName = rowData.get(0);
                Channel channel = validationService.getChannelByDomainName(channelDomainName, false);
                createModel.setChannelId(channel.getId());
                createModel.setCode(rowData.get(1));
                createModel.setDescription(rowData.get(2));
                createModel.setImage(rowData.get(3));
                createModel.setName(rowData.get(4));
                createModel.setPrice(Double.valueOf(rowData.get(5)));
                productService.create(createModel);
            }
            catch (Throwable e) {
                String errorMessage = String.format("Error %s processing record: %s at row " + currentRow,
                                                           e.getMessage(), rowData);
                logger.debug(errorMessage, e);

                UploadError uploadError = new UploadError();
                uploadError.setError(errorMessage);
                uploadError.setDomainObjectType("Product");
                try {
                    uploadErrorService.create(uploadError);
                }
                catch (Throwable throwable) {
                    throw new RuntimeException(throwable);
                }
            }
            finally {
                rowData.clear();
            }
        }

        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            // gracefully handle missing cellReference here in a similar way as XSSFCell does
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            // we missed any cells?
            int thisCol = (new CellReference(cellReference)).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                rowData.add("");
            }
            currentCol = thisCol;
            rowData.add(formattedValue.trim());
        }

        public void headerFooter(String text, boolean isHeader, String tagName) {
            // process to insert data, ignore this
            logger.info(text + " - " + isHeader + " - " + tagName);
        }
    }

}
