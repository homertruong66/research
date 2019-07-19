package com.rms.rms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.config.properties.ApplicationProperties;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyEmailUtil;
import com.rms.rms.common.util.MyJwtUtil;
import com.rms.rms.common.view_model.*;
import com.rms.rms.service.*;
import com.rms.rms.service.model.Affiliate;
import com.rms.rms.service.model.Email;
import com.rms.rms.service.model.SubsEmailTemplate;
import com.rms.rms.task_processor.TaskProcessorCreateEmailAndNotification;
import com.rms.rms.task_processor.TaskProcessorHandleBigReport;
import com.rms.rms.task_processor.TaskProcessorPushDataToGetResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * homertruong
 */
public class AffiliateController {

    private Logger logger = Logger.getLogger(AffiliateController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private TaskProcessorCreateEmailAndNotification tpCreateEmailAndNotification;

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SubsEmailTemplateService subsEmailTemplateService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SubsGetResponseConfigService subsGetResponseConfigService;

    @Autowired
    private TaskProcessorHandleBigReport tpHandleBigReport;

    @Autowired
    private TaskProcessorPushDataToGetResponse tpPushDataToGetResponse;

    @Autowired
    @Qualifier("taskExecutorService")
    private ExecutorService taskExecutorService;

    // for sign in Affiliate only <--
    private static final String HEADER_SECURITY_TOKEN_KEY = "X-Security-Token";
    private static final String HEADER_USER_PROFILE_KEY = "X-User-Profile";

    @Autowired
    private AuthenService authenService;
    @Autowired
    private MyJwtUtil myJwtUtil;
    private ObjectMapper jsonMapper = new ObjectMapper();
    // -->

    public ResponseDto assign(String id, AffiliateAssignModel assignModel) throws BusinessException {
        AffiliateDto dto = personService.assignAffiliate(id, assignModel);
        return new ResponseDto(dto);
    }

    public void calculateAffiliates() throws BusinessException {
        personService.calculateAffiliates();
    }

    public ResponseDto confirmPhone(String phone) throws BusinessException {
        String dto = personService.confirmPhone(phone);

        return new ResponseDto(dto);
    }

    public ResponseDto convertTo(AffiliateConvertModel convertModel) throws BusinessException, IOException {
        OrderDto orderDto = orderService.get(convertModel.getOrderId());
        String feUrl = convertModel.getFeUrl();
        String email = convertModel.getEmail();
        if (StringUtils.isBlank(email)) {
            email = orderDto.getCustomer().getEmail();
        }

        // check if this is a new Affiliate in RMS to send activation email
        boolean isNewAffiliate = !personService.isAffiliateExistent(email);

        // create a new Affiliate or assign the existing Affiliate to the selected Subscriber
        AffiliateDto dto = personService.convertTo(convertModel);

        UserDto loggedUserDto = authenService.getLoggedUser();
        Runnable task = () -> tpCreateEmailAndNotification.processOnAffiliateConverted(dto, loggedUserDto, orderDto,
                feUrl, isNewAffiliate);
        taskExecutorService.submit(task);

        return new ResponseDto(dto);
    }

    public ResponseDto create(AffiliateCreateModel createModel) throws BusinessException, IOException {

        boolean isNewAffiliate = !personService.isAffiliateExistent(createModel.getEmail());

        // create a new Affiliate or assign the existing Affiliate to the selected Subscriber
        AffiliateDto dto = personService.createAffiliate(createModel);

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnAffiliateCreated(dto, loggedUserDto,createModel,isNewAffiliate);
        });

        return new ResponseDto(dto);
    }

    public ResponseEntity<byte[]> export(Date from, Date to, HttpServletResponse response) throws BusinessException {
        if(from == null || to == null) {
            throw new InvalidViewModelException("'from' or 'to' can not be null !");
        }

        if (from.after(to)) {
            throw new InvalidViewModelException("'from' must <= 'to' !");
        }

        // export Affiliates to byte array
        byte[] contents = personService.exportAffiliates(from, to);
        String filename = "affiliates-" + from.toString() + "-to-" + to.toString() + ".xlsx";

        // handle if report is big
        if( contents.length > applicationProperties.getMaxFileSize() ) {
            PersonDto personDto = personService.getByEmail(authenService.getLoggedUser().getEmail());
            Runnable task = () -> tpHandleBigReport.process(contents, filename, personDto, Affiliate.class.getSimpleName());
            taskExecutorService.submit(task);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(contents, headers, HttpStatus.OK);

        return responseEntity;

        // return excel: it's difficult for FE React to process a file via AJAX, disable this for now
//        String filename = "affiliates-" + MyDateUtil.getYYMMDDString(from) + "-to-" + MyDateUtil.getYYMMDDString(to) + ".xlsx";
//        response.addHeader("Content-disposition", "attachment; filename=" + filename);
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        try {
//            OutputStream ouputStream = response.getOutputStream();
//            ouputStream.write(contents);
//            ouputStream.close();
//        }
//        catch (IOException e) {
//            logger.error("error exporting excel file", e);
//            throw new RuntimeException("error exporting excel file: " + e.getMessage());
//        }
    }

    public ResponseDto get(String id) throws BusinessException {
        AffiliateDto dto = personService.getAffiliate(id);
        return new ResponseDto(dto);
    }

    public void invite(AffiliateInviteModel inviteModel) throws BusinessException, IOException {

        String errors = inviteModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnAffiliatesInvite(inviteModel, loggedUserDto);
        });

    }

    public void remind(AffiliateRemindModel remindModel) throws BusinessException, IOException {
        String errors = remindModel.validate();
        if (errors != null) {
            throw new InvalidViewModelException(errors);
        }

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnAffiliateRemind(remindModel, loggedUserDto);
        });

    }

    public void remindToUpdateProfile (AffiliateRemindModel remindModel) throws BusinessException {
        String errors = remindModel.validate();
        if ( errors != null ) {
            throw new InvalidViewModelException(errors);
        }

        for (String emailString : remindModel.getEmails()) {
            PersonDto personDto = personService.getByEmail(emailString);
            if ( personDto == null || !personDto.getDiscriminator().equals(PersonDto.TYPE_AFFILIATE )) {
                logger.error("Can't get Affiliate with '" + emailString + "' OR the Person is not an Affiliate");
                continue;
            }

            SubsEmailTemplateDto subsEmailTemplate = subsEmailTemplateService.getByType(
                SubsEmailTemplate.TYPE_AFF_REMIND_UPDATE_BANK_INFO);
            String content = subsEmailTemplate.getContent();
            content = content.replace("[full_name]",String.join(" ", personDto.getLastName(), personDto.getFirstName()));
            subsEmailTemplate.setContent(content);
            Email email = MyEmailUtil.createEmailFromSubsEmailTemplate(
                subsEmailTemplate, emailString,
                String.join(" ", personDto.getLastName(), personDto.getFirstName())
            );

            emailService.send(email);
        }
    }

    public ResponseDto search(SearchCriteria<AffiliateSearchCriteria> vmSearchCriteria) throws BusinessException {
        SearchResult<AffiliateDto> dtoSearchResult = personService.searchAffiliates(vmSearchCriteria);
        return new ResponseDto(dtoSearchResult);
    }

    public void sendCustomEmails(CustomEmailModel customEmailModel) throws BusinessException, IOException {
        personService.sendCustomEmailsToAffiliates(customEmailModel);
    }

    public ResponseDto signIn(AffiliateSignInModel signInModel, HttpServletResponse response) throws BusinessException {
        LoginDto loginDto = personService.signInAffiliate(signInModel);

        if (loginDto.getConfirmMessage() == null) {
            UserDto userDto = new UserDto() {
                {
                    this.setId(loginDto.getId());
                    this.setEmail(loginDto.getEmail());
                    this.setRoles(loginDto.getRoles());
                    this.setStatus(loginDto.getStatus());
                }
            };
            String token = generateToken(userDto);
            String headerSecurityToken = "Bearer " + token;
            response.setHeader(HEADER_SECURITY_TOKEN_KEY, headerSecurityToken);
            try {
                response.setHeader(HEADER_USER_PROFILE_KEY, jsonMapper.writeValueAsString(loginDto));
            } catch (JsonProcessingException jpe) {
                logger.error("can not process loginDto: ", jpe);
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return new ResponseDto();
            }

            // set Authentication object for Spring Security Context
            setAuthenticationForSpringSecurityContext(userDto);
        }

        return new ResponseDto(loginDto);
    }

    public ResponseDto signUp(AffiliateCreateModel createModel) throws BusinessException, IOException {
        ResponseDto responseDto = this.create(createModel);

        UserDto loggedUserDto = authenService.getLoggedUser();

        taskExecutorService.submit(() -> {
            tpCreateEmailAndNotification.processOnAffiliateSignedUp(createModel, loggedUserDto);
        });

        AffiliateDto dto = (AffiliateDto) responseDto.getData();
        SubsGetResponseConfigDto sgrConfigDto = subsGetResponseConfigService.get(dto.getSubscriberId());
        if (dto.getConfirmMessage() == null && sgrConfigDto.getSendAffiliateData()) {
            Runnable task = () -> tpPushDataToGetResponse.process(dto, sgrConfigDto, loggedUserDto);
            taskExecutorService.submit(task);
        }

        return responseDto;
    }

    public ResponseDto update(String id, AffiliateUpdateModel updateModel) throws BusinessException {
        AffiliateDto dto = personService.updateAffiliate(id, updateModel);
        return new ResponseDto(dto);
    }

    public ResponseDto view(String id) throws BusinessException {
        AffiliateViewDto dto = personService.viewAffiliate(id);
        return new ResponseDto(dto);
    }

    public ResponseDto viewNetwork(AffiliateViewNetworkModel viewNetworkModel) throws BusinessException {
        List<AffiliateDto> affiliateDtos = personService.getAffiliatesInNetwork(viewNetworkModel);
        return new ResponseDto(affiliateDtos);
    }

    public ResponseDto viewTransaction(AffiliateTransactionModel viewModel) throws BusinessException {
        AffiliateTransactionDto affiliateViewDto = personService.viewAffiliateTransaction(viewModel);
        return new ResponseDto(affiliateViewDto);
    }

    // Utilities

    private String generateToken(UserDto userDto) {
        // get current token
        String token = userDto.getToken();
        if (token != null) {
            try {
                myJwtUtil.isTokenCorrect(token, userDto);
                return token;
            }
            catch (ExpiredJwtException ex) {
                logger.info("token expired, generate new token");
            }
        }

        // generate new token
        token = myJwtUtil.generateToken(userDto, new Date());
        authenService.insertToken(userDto.getId(), token);

        return token;
    }

    private List<GrantedAuthority> getAuthorities(UserDto userDto) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        userDto.getRoles().stream().forEach(roleName -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(grantedAuthority);
        });

        return authorities;
    }

    private void setAuthenticationForSpringSecurityContext (UserDto userDto) {
        boolean needTakeAction = true;
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuthentication != null) {
            UserDto loggedDto = (UserDto) currentAuthentication.getPrincipal();
            if (loggedDto.getEmail().equals(userDto.getEmail())) {       // returning User, take no action
                needTakeAction = false;
            }
        }

        if (needTakeAction) {
            List<GrantedAuthority> authorities = getAuthorities(userDto);
            Authentication authentication = new PreAuthenticatedAuthenticationToken(
                    userDto, userDto.getPassword(), authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

}
