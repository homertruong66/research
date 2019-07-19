package com.rms.rms.controller.v1;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.dto.ResponseDto;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.view_model.*;
import com.rms.rms.controller.AffiliateController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * homertruong
 */

@RestController
@RequestMapping(value = "/v1/affiliates")
public class AffiliateControllerV1 extends AffiliateController {

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto assign(@PathVariable String id,
                              @RequestBody AffiliateAssignModel assignModel) throws BusinessException {
        return super.assign(id, assignModel);
    }

    @GetMapping(value = "/calculate_affiliates")
    public void calculateAffiliates() throws BusinessException {
        super.calculateAffiliates();
    }

    @PostMapping(value = {"/phone/confirm", "/phone/confirm/{phone}"} )
    public ResponseDto confirmPhone(@PathVariable Optional<String> phone) throws BusinessException {
        return super.confirmPhone(phone.isPresent()? phone.get() : null);
    }

    @PostMapping(value = "/convert_to", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto convertTo(@RequestBody AffiliateConvertModel convertModel) throws BusinessException, IOException {
        return super.convertTo(convertModel);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto create(@RequestBody AffiliateCreateModel createModel) throws BusinessException, IOException {
        return super.create(createModel);
    }

    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> export(@RequestParam("from") String from,
                                         @RequestParam("to") String to, HttpServletResponse response) throws BusinessException {
        Date fromDate, toDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            fromDate = simpleDateFormat.parse(from);
            toDate = simpleDateFormat.parse(to);
        }
        catch (ParseException e) {
            throw new InvalidViewModelException("Error parsing date strings: " + from + " or " + to);
        }

        return super.export(fromDate, toDate, response);
    }

    @GetMapping(value = "/{id}")
    public ResponseDto get(@PathVariable String id) throws BusinessException {
        return super.get(id);
    }

    @PostMapping(value = "/invite", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void invite(@RequestBody AffiliateInviteModel inviteModel) throws BusinessException, IOException {
        super.invite(inviteModel);
    }

    @PostMapping(value = "/remind", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void remind(@RequestBody AffiliateRemindModel remindModel) throws BusinessException, IOException {
        super.remind(remindModel);
    }

    @Override
    @PostMapping(value = "/remind_to_update_profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void remindToUpdateProfile (@RequestBody AffiliateRemindModel remindModel) throws BusinessException {
        super.remindToUpdateProfile(remindModel);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto search(@RequestBody SearchCriteria<AffiliateSearchCriteria> searchCriteria) throws BusinessException {
        return super.search(searchCriteria);
    }

    @PostMapping(value = "/send_custom_emails", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void sendCustomEmails(@RequestBody CustomEmailModel customEmailModel) throws BusinessException, IOException {
        super.sendCustomEmails(customEmailModel);
    }

    @PostMapping(value = "/sign_in", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto signIn(@RequestBody AffiliateSignInModel signInModel, HttpServletResponse response) throws BusinessException {
        return super.signIn(signInModel, response);
    }

    @PostMapping(value = "/sign_up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto signUp(@RequestBody @Valid AffiliateCreateModel createModel) throws BusinessException, IOException {
        return super.signUp(createModel);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto update(@PathVariable String id,
                              @RequestBody AffiliateUpdateModel updateModel) throws BusinessException {
        return super.update(id, updateModel);
    }

    @Override
    @PostMapping(value = "/{id}/view")
    public ResponseDto view(@PathVariable String id) throws BusinessException {
        return super.view(id);
    }

    @Override
    @PostMapping(value = "/view_network")
    public ResponseDto viewNetwork(@RequestBody AffiliateViewNetworkModel viewNetworkModel) throws BusinessException {
        return super.viewNetwork(viewNetworkModel);
    }

    @Override
    @PostMapping(value = "/view_transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDto viewTransaction(@RequestBody AffiliateTransactionModel viewModel) throws BusinessException {
        return super.viewTransaction(viewModel);
    }
}
