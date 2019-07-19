package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.dto.PersonDto;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.CustomerSearchCriteria;
import com.rms.rms.service.AuthenService;
import com.rms.rms.service.CustomerService;
import com.rms.rms.service.PersonService;
import com.rms.rms.service.model.Customer;
import com.rms.rms.task_processor.TaskProcessorHandleBigReport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class CustomerController {

    private Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private PersonService personService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorHandleBigReport tpHandleBigReport;

    public ResponseEntity<byte[]> export(Date from, Date to, HttpServletResponse response) throws BusinessException {
        if(from == null || to == null) {
            throw new InvalidViewModelException("'from' or 'to' can not be null !");
        }

        if (from.after(to)) {
            throw new InvalidViewModelException("'from' must <= 'to' !");
        }


        // export Customers to byte array
        byte[] contents = customerService.export(from, to);
        String filename = "customers-" + from.toString() + "-to-" + to.toString() + ".xlsx";

        // handle if report is big
        if( contents.length > applicationProperties.getMaxFileSize() ) {
            PersonDto personDto = personService.getByEmail(authenService.getLoggedUser().getEmail());
            Runnable task = () -> tpHandleBigReport.process(contents, filename, personDto, Customer.class.getSimpleName());
            taskExecutorService.submit(task);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return responseEntity;
    }

    public ResponseDto search(SearchCriteria<CustomerSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<CustomerDto> dtoSearchResult = customerService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public ResponseDto upload(MultipartFile uploadedFile) throws BusinessException {
        customerService.upload(uploadedFile);

        return new ResponseDto("ok");
    }

}
