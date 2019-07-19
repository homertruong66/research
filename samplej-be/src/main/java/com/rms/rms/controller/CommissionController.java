package com.rms.rms.controller;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.AuthenService;
import com.rms.rms.service.CommissionService;
import com.rms.rms.service.PersonService;
import com.rms.rms.service.model.Commission;
import com.rms.rms.task_processor.TaskProcessorCalculateCommissions;
import com.rms.rms.task_processor.TaskProcessorEvaluateCOASV;
import com.rms.rms.task_processor.TaskProcessorHandleBigReport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */

public class CommissionController {

    private Logger logger = Logger.getLogger(CommissionController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthenService authenService;

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private TaskProcessorHandleBigReport tpHandleBigReport;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    @Autowired
    private TaskProcessorCalculateCommissions tpCalculateCommissions;

    @Autowired
    private TaskProcessorEvaluateCOASV tpEvaluateCOASV;

    public ResponseDto calculate(CommissionCalculateModel calculateModel) throws BusinessException {
        UserDto loggedUserDto = authenService.getLoggedUser();

        // calculate Commissions on input Order in background mode
        Runnable task = () -> tpCalculateCommissions.process(calculateModel.getOrderId(), loggedUserDto);
        taskExecutorService.submit(task);

        return new ResponseDto("OK");
    }

    public ResponseDto checkCommissions(CommissionCheckModel checkModel) throws BusinessException {
        CommissionExperienceDto dto = commissionService.checkCommissions(checkModel);
        return new ResponseDto(dto);
    }

    public ResponseDto evaluateCOASV() throws BusinessException {
        UserDto loggedUserDto = authenService.getLoggedUser();

        // evaluate COASV in background mode
        Runnable task = () -> tpEvaluateCOASV.process(loggedUserDto);
        taskExecutorService.submit(task);

        return new ResponseDto("OK");
    }

    public ResponseDto experience(CommissionExperienceModel experienceModel) {
        logger.info("experience: " + experienceModel);

        // process view model
        experienceModel.escapeHtml();
        String errors = experienceModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        OrderExperienceModel order = experienceModel.getOrder();
        Set<OrderLineExperienceModel> orderLines = order.getOrderLines();
        Double COPS = experienceModel.getCops();
        Double COPPR = experienceModel.getCoppr();
        Double CAD = experienceModel.getCad();
        Double COANSeller = experienceModel.getCoanSeller();
        Double COANLayer1 = experienceModel.getCoanReferrer1();
        Double COANLayer2 = experienceModel.getCoanReferrer2();
        Double COOV = experienceModel.getCoov();
        Double COPG = experienceModel.getCopg();
        Double CDC = experienceModel.getCdc();
        Double COPQ = experienceModel.getCopq();

        // init dto
        CommissionExperienceDto dto = new CommissionExperienceDto();
        dto.setOrder(order);
        dto.setCbs(0d);
        dto.setCoanReferrer1(0d);
        dto.setCoanReferrer2(0d);
        dto.setCoov(0d);
        dto.setCopg(0d);
        dto.setCdc(0d);
        dto.setCopq(0d);

        double orderEarning = 0d;
        double orderTotal = 0d;

        // scan Products to apply COPP or COPPR or CAD;
        for (OrderLineExperienceModel orderLine : orderLines) {
            double productPrice = orderLine.getPrice();
            double productQuantity = orderLine.getQuantity();
            boolean hasCOPP = false;
            boolean hasCOPS = false;
            boolean hasCOPPR = false;

            // check COPP
            Double productCommission = orderLine.getCommission();
            if (productCommission != null && productCommission > 0) {
                orderEarning += productCommission * productPrice * productQuantity;
                hasCOPP = true;
            }

            // no have COPP, check COPS
            if (!hasCOPP && COPS != null) {
                if (COPS > 0) {
                    orderEarning += COPS * productPrice * productQuantity;
                    hasCOPS = true;
                }
            }

            // neither have COPP nor COPS, check COPPR
            if (!hasCOPP && !hasCOPS && COPPR != null) {
                if (COPPR > 0) {
                    orderEarning += COPPR * productPrice * productQuantity;
                    hasCOPPR = true;
                }
            }

            // neither have COPP nor COPS nor COPPR, check CAD
            if (!hasCOPP && !hasCOPS && !hasCOPPR && CAD != null) {
                if (CAD > 0) {
                    orderEarning += CAD * productPrice * productQuantity;
                }
            }

            orderTotal += productPrice * productQuantity;
        }

        dto.setOrderEarning(orderEarning);
        dto.setOrderTotal(orderTotal);

        // process COAN for seller and its ancestors (TOWER)
        if (COANLayer1 == null && COANLayer2 == null) {
            dto.setCbs(orderEarning);
        }
        else {
            // create CBS for seller at layer = 1
            if (COANSeller != null && COANSeller > 0) {
                double cbs = orderEarning * COANSeller;
                dto.setCbs(cbs);
            }

            // create COAN for its ancestors at layer = 2,3
            if (COANLayer1 != null && COANLayer1 > 0) {
                double coanEarningLayer1 = orderEarning * COANLayer1;
                dto.setCoanReferrer1(coanEarningLayer1);
            }

            if (COANLayer2 != null && COANLayer2 > 0) {
                double coanEarningLayer2 = orderEarning * COANLayer2;
                dto.setCoanReferrer2(coanEarningLayer2);
            }
        }

        // find COOV
        if (COOV != null && COOV > 0) {
            double coovEarning = COOV * orderTotal;
            dto.setCoov(coovEarning);
        }

        // find COPG
        if (COPG != null && COPG > 0) {
            double copgEarning = COPG * orderTotal;
            dto.setCopg(copgEarning);
        }

        // apply DiscountCode if any
        if (CDC != null && CDC > 0) {
            double CDCValue = 0 - CDC * orderTotal;
            dto.setCdc(CDCValue);
        }

        // find COPQ
        if (COPQ != null && COPQ > 0) {
            double copqValue = COPQ * orderTotal;
            dto.setCopq(copqValue);
        }

        dto.formatDoubleProps();

        return new ResponseDto(dto);
    }

    public ResponseEntity<byte[]> export(Date from, Date to) throws BusinessException {
        if(from == null || to == null) {
            throw new InvalidViewModelException("'from' or 'to' can not be null !");
        }

        if (from.after(to)) {
            throw new InvalidViewModelException("'from' must <= 'to' !");
        }

        // export Commissions to byte array
        byte[] contents = commissionService.export(from, to);
        String filename = "commissions-" + from.toString() + "-to-" + to.toString() + ".xlsx";

        // handle if report is big
        if( contents.length > applicationProperties.getMaxFileSize() ) {
            PersonDto personDto = personService.getByEmail(authenService.getLoggedUser().getEmail());
            Runnable task = () -> tpHandleBigReport.process(contents, filename, personDto, Commission.class.getSimpleName());
            taskExecutorService.submit(task);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    public ResponseDto search(SearchCriteria<CommissionSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<CommissionDto> dtoSearchResult = commissionService.search(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }
}
