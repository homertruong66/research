package com.rms.rms.service;

import com.rms.rms.common.CustomCriteria;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.constant.SystemRole;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.dto.UserDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.CustomerCreateModel;
import com.rms.rms.common.view_model.CustomerSearchCriteria;
import com.rms.rms.persistence.GenericDao;
import com.rms.rms.persistence.SpecificDao;
import com.rms.rms.service.model.*;
import com.rms.rms.task_processor.TaskProcessorImportCustomers;
import com.rms.rms.task_processor.TaskProcessorImportProducts;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */

@Service
@Transactional(value = "transactionManager", propagation = Propagation.SUPPORTS)
public class CustomerServiceImpl implements CustomerService {

    private Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private ModelMapper beanMapper;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenericDao genericDao;

    @Autowired
    private SpecificDao specificDao;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorImportCustomers tpImportCustomers;

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public Long count() throws BusinessException {
        logger.info("count: ");

        // do authorization
          // Admin can count all Customers
          // SubsAdmin can only count Customers of its Subscriber
          // Affiliate can only count its Customers
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            return specificDao.countCustomersBySubscriberId(loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            return specificDao.countCustomersByAffiliateId(loggedUserDto.getId());
        }

        return Long.valueOf(genericDao.count(Customer.class));
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countByAffiliateId(String affiliateId) throws BusinessException {
        logger.info("countByAffiliateId: " + affiliateId);

        // validate biz rules
        validationService.getAffiliate(affiliateId, false);

        // do authorization
          // Admin can count all Customers of the Affiliate
          // SubsAdmin can only count Customers of the Affiliate whose Subscribers must contain its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndAffiliate(loggedUserDto.getId(), affiliateId);
        }

        return specificDao.countCustomersByAffiliateId(affiliateId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countByChannelId(String channelId) throws BusinessException {
        logger.info("countByChannelId: " + channelId);

        // validate biz rules
        Channel channel = validationService.getChannel(channelId, false);

        // do authorization
          // Admin can count all Customers from the Channel
          // SubsAdmin can only count Customers from the Channel whose Subscriber must be its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            String subscriberId = channel.getSubscriberId();
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countCustomersByChannelId(channelId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public Long countBySubscriberId(String subscriberId) throws BusinessException {
        logger.info("countBySubscriberId: " + subscriberId);

        // validate biz rules
        validationService.getSubscriber(subscriberId, false);

        // do authorization
          // Admin can count all Customers of the Subscriber
          // SubsAdmin can only count Customers of its Subscriber who must be the Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubsAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), subscriberId);
        }

        return specificDao.countCustomersBySubscriberId(subscriberId);
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CustomerDto create(CustomerCreateModel createModel) throws BusinessException {
        logger.info("create: " + createModel.toString());

        // process view model
        createModel.escapeHtml();
        String errors = createModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        // validate biz rules
        Customer newDO = beanMapper.map(createModel, Customer.class);
        Subscriber subscriber = validationService.getSubscriber(createModel.getSubscriberId(),false);
        newDO.setSubscriber(subscriber);
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", newDO.getEmail());
        Customer existingDO = genericDao.readUnique(Customer.class, customerParams, false);
        if (existingDO != null) {
            throw new BusinessException(BusinessException.CUSTOMER_ALREADY_EXIST,
                    String.format(BusinessException.CUSTOMER_ALREADY_EXIST_MESSAGE, existingDO.getId()));
        }

        // do authorization
            // SubsAdmin can only create customer for its Subscriber
        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        if (isSubAdmin) {
            authorService.checkSubsAdminAndSubscriber(loggedUserDto.getId(), newDO.getSubscriberId());
        }
        // do biz action
        Customer pdo = genericDao.create(newDO);

        return beanMapper.map(pdo, CustomerDto.class);
    }

    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    public byte[] export(Date from, Date to) throws BusinessException {
        logger.info("export: " + " - from: " + from + " -> to: " + to);

        // create excel work book
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Customers");

        // create headers
        Row row = sheet.createRow(0);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue("Họ và Tên");
        Cell cell2 = row.createCell(1);
        cell2.setCellValue("Địa chỉ");
        Cell cell3 = row.createCell(2);
        cell3.setCellValue("Thư điện tử");
        Cell cell4 = row.createCell(3);
        cell4.setCellValue("Số điện thoại");
        Cell cell5 = row.createCell(4);
        cell5.setCellValue("Công ty");
        Cell cell6 = row.createCell(5);
        cell6.setCellValue("Tên miền");

        // add Customers page by page
        SearchCriteria<CustomerSearchCriteria> searchCriteria = new SearchCriteria<>();
        int pageSize = 1000;
        int startRow = 1;
        searchCriteria.setCriteria(new CustomerSearchCriteria());
        searchCriteria.setPageIndex(1);
        searchCriteria.setPageSize(pageSize);

        // set up search criteria
        CustomCriteria customCriteria = new CustomCriteria();
        customCriteria.setValue("createdAt>=", from);
        customCriteria.setValue("createdAt<=", to);

        // render first page
        searchCriteria.setCustomCriteria(customCriteria);
        SearchResult<CustomerDto> searchResult = this.search(searchCriteria);    // authorization done in this
        logger.info("Export Page 1: " + searchResult.getList().size() + " items");
        this.renderCustomersToExcelSheet(searchResult.getList(), sheet, startRow);

        // render the rest
        int numberOfPages = searchResult.getNumOfPages();
        for (int pageIndex = 2; pageIndex <= numberOfPages; pageIndex++) {
            searchCriteria.setPageIndex(pageIndex);
            searchResult = this.search(searchCriteria);
            logger.info("Export Page " + pageIndex + ": " + searchResult.getList().size() + " items");
            startRow += pageSize;
            this.renderCustomersToExcelSheet(searchResult.getList(), sheet, startRow);
        }

        // write excel work book to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not write data to output stream");
        }
        finally {
            try {
                workbook.close();
                bos.close();
            } catch (IOException e) {
            }
        }
        byte[] contents = bos.toByteArray();

        return contents;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN, SystemRole.ROLE_AFFILIATE})
    public SearchResult<CustomerDto> search(SearchCriteria<CustomerSearchCriteria> vmSearchCriteria) throws BusinessException {
        logger.info("search: " + vmSearchCriteria.toString());

        // setup search criteria
        SearchCriteria<Customer> searchCriteria = new SearchCriteria<>();
        beanMapper.map(vmSearchCriteria, searchCriteria);       // map sort, page info
        if (vmSearchCriteria.getCriteria() != null) {
            // map search info
            Customer criteria = beanMapper.map(vmSearchCriteria.getCriteria(), Customer.class);
            searchCriteria.setCriteria(criteria);
        }
        else {
            // no search info found, use default
            searchCriteria.setCriteria(new Customer());
        }

        // do authorization
            // Admin can search all Customers
            // SubsAdmin can only search Customers of its Subscriber
            // Affiliate can only search Customers of its
        SearchResult<Customer> searchResult;
        SearchResult<CustomerDto> dtoSearchResult;

        UserDto loggedUserDto = authenService.getLoggedUser();
        boolean isSubsAdmin = SystemRole.hasSubsAdminRole(loggedUserDto.getRoles());
        boolean isAffiliate = SystemRole.hasAffiliateRole(loggedUserDto.getRoles());

        if (isSubsAdmin) {
            SubsAdmin loggedSubsAdmin = genericDao.read(SubsAdmin.class, loggedUserDto.getId(), false);
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("subscriberId", loggedSubsAdmin.getSubscriberId());
        }
        else if (isAffiliate) {
            List<String> customerIds = specificDao.getCustomerIdsByAffiliateId(loggedUserDto.getId());
            if(customerIds.size() == 0){
                searchResult = genericDao.generateEmptySearchResult(searchCriteria);
                dtoSearchResult = createDtoSearchResult(searchResult);
                return dtoSearchResult;
            }
            CustomCriteria customCriteria = searchCriteria.getCustomCriteria();
            customCriteria.setValue("id", customerIds);
        }

        // do biz action
        searchResult = genericDao.search(searchCriteria);
        dtoSearchResult = createDtoSearchResult(searchResult);

        return dtoSearchResult;
    }

    @Override
    @Secured({SystemRole.ROLE_ADMIN, SystemRole.ROLE_SUBS_ADMIN})
    @Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void upload(MultipartFile file)  throws BusinessException {
        logger.info("upload with file: " + file.getName());

        // import Customers in background mode
        UserDto loggedUserDto = authenService.getLoggedUser();
        Runnable task = () -> tpImportCustomers.process(file, loggedUserDto);
        taskExecutorService.submit(task);
    }


    // Utilities
    private SearchResult<CustomerDto> createDtoSearchResult(SearchResult<Customer> searchResult) {
        SearchResult<CustomerDto> result = new SearchResult<>();
        beanMapper.map(searchResult, result);
        List<CustomerDto> dtos = new ArrayList<>();
        for (Customer pdo : searchResult.getList()) {
            dtos.add(beanMapper.map(pdo, CustomerDto.class));
        }
        result.setList(dtos);

        return result;
    }

    private void renderCustomersToExcelSheet(List<CustomerDto> customerDtos, XSSFSheet sheet, int startRow) {
        for (CustomerDto customerDto : customerDtos) {
            Row row = sheet.createRow(startRow++);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(customerDto.getFullname());
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(customerDto.getAddress());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(customerDto.getEmail());
            Cell cell4 = row.createCell(3);
            cell4.setCellValue(customerDto.getPhone());
            Cell cell5 = row.createCell(4);
            cell5.setCellValue(customerDto.getSubscriber().getCompanyName());
            Cell cell6 = row.createCell(5);
            cell6.setCellValue(customerDto.getSubscriber().getDomainName());
        }
    }
}
