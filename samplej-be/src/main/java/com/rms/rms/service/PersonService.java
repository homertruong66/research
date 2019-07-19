package com.rms.rms.service;

import com.rms.rms.common.SearchCriteria;
import com.rms.rms.common.SearchResult;
import com.rms.rms.common.dto.*;
import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.view_model.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * homertruong
 */

public interface PersonService {

    SubsAdminDto addRoleForSubsAdmin(String subsAdminId, String roleName) throws BusinessException;

    AffiliateDto assignAffiliate(String affiliateId, AffiliateAssignModel assignModel) throws BusinessException;

    void calculateAffiliates() throws BusinessException;

    PersonDto changePassword(ChangePasswordModel model) throws BusinessException;

    String confirmPhone (String phone) throws BusinessException;

    AffiliateDto convertTo(AffiliateConvertModel convertModel) throws BusinessException;

    Long countAffiliates() throws BusinessException;

    Long countAffiliatesByAffiliateIdAndSubscriberId(String affiliateId, String subscriberId) throws BusinessException;

    Long countAffiliatesBySubscriberId(String subscriberId) throws BusinessException;

    AdminDto createAdmin(AdminCreateModel createModel) throws BusinessException;

    AffiliateDto createAffiliate(AffiliateCreateModel createModel) throws BusinessException;

    SubsAdminDto createSubsAdmin(String subscriberId, SubsAdminCreateModel createModel) throws BusinessException;

    void deleteAdmin(String adminId) throws BusinessException;

    void deleteSubsAdmin(String subsAdminId) throws BusinessException;

    byte[] exportAffiliates(Date from, Date to) throws BusinessException;

    AdminDto getAdmin(String adminId) throws BusinessException;

    AffiliateDto getAffiliate(String affiliateId) throws BusinessException;

    AffiliateDto getAffiliateByNickname(String nickname) throws BusinessException;

    List<AffiliateDto> getAffiliatesInNetwork(AffiliateViewNetworkModel viewNetworkModel) throws BusinessException;

    PersonDto getProfile(String id) throws BusinessException;

    SubsAdminDto getSubsAdmin(String subsAdminId) throws BusinessException;

    List<String> getSubsAdminIdsBySubscriberId(String subscriberId) throws BusinessException;

    List<String> getSubsAdminAccountantIdsBySubscriberId(String subscriberId) throws BusinessException;

    List<String> getSubsAdminEmailsBySubscriberId(String subscriberId) throws BusinessException;

    PersonDto getByEmail(String email) throws BusinessException;

    SubsAdminDto grantRootForSubsAdmin(String subsAdminId) throws BusinessException;

    boolean isAffiliateExistent (String email) throws BusinessException;

    SubsAdminDto removeRoleForSubsAdmin(String subsAdminId, String roleName) throws BusinessException;

    PersonDto resetPassword(String email, String newPassword) throws BusinessException;

    SubsAdminDto revokeRootForSubsAdmin(String subsAdminId) throws BusinessException;

    SearchResult<AdminDto> searchAdmins(SearchCriteria<AdminSearchCriteria> vmSearchCriteria) throws BusinessException;

    SearchResult<AffiliateDto> searchAffiliates(SearchCriteria<AffiliateSearchCriteria> vmSearchCriteria) throws BusinessException;

    SearchResult<SubsAdminDto> searchSubsAdmins(SearchCriteria<SubsAdminSearchCriteria> vmSearchCriteria) throws BusinessException;

    void sendCustomEmailsToAffiliates(CustomEmailModel customEmailModel) throws BusinessException, IOException;

    AffiliateDto sendDataToGetResponse(AffiliateDto affiliateDto) throws BusinessException, IOException;

    LoginDto signInAffiliate(AffiliateSignInModel signInModel) throws BusinessException;

    AdminDto updateAdmin(String adminId, AdminUpdateModel updateModel) throws BusinessException;

    AffiliateDto updateAffiliate(String affiliateId, AffiliateUpdateModel updateModel) throws BusinessException;

    void updateGetResponseSuccess(String id, boolean success);

    PersonDto updateProfile(String id, PersonUpdateModel updateModel) throws BusinessException;

    SubsAdminDto updateSubsAdmin(String subsAdminId, SubsAdminUpdateModel updateModel) throws BusinessException;

    AffiliateViewDto viewAffiliate(String id) throws BusinessException;

    AffiliateTransactionDto viewAffiliateTransaction(AffiliateTransactionModel viewModel) throws BusinessException;

}