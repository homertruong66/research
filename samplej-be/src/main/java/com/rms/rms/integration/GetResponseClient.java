package com.rms.rms.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rms.rms.common.config.properties.GetResponseServerProperties;
import com.rms.rms.common.dto.AffiliateDto;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import com.rms.rms.common.util.MyGetResponseUtil;
import com.rms.rms.integration.exception.GetResponseIntegrationException;
import com.rms.rms.integration.model.GetResponseContact;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Scope("prototype")
public class GetResponseClient {

    private Logger logger = Logger.getLogger(GetResponseClient.class);

    @Autowired
    private GetResponseServerProperties getResponseServerProperties;

    public boolean createContactFromAffiliateDto(AffiliateDto affiliateDto, String apiKey, SubsGetResponseConfigDto configDto) throws GetResponseIntegrationException {
        logger.info("createContactFromAffiliateDto: " + affiliateDto + ", apiKey: " + apiKey + ", configDto: " + configDto);

        try {
            String url = getResponseServerProperties.getContactUrl();
            URL obj = new URL(url);
            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.POST_METHOD, apiKey);

            // Request Body
            JsonObject requestBody = new JsonObject();
            MyGetResponseUtil.mapAffiliateDto2GetResponseContact(affiliateDto, configDto, requestBody);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(requestBody.toString());
            wr.flush();

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 202) {
                logger.error("createContactFromAffiliateDto fail: " + responseCode);
                return false;
            }
        }
        catch (Exception ex) {
            throw new GetResponseIntegrationException(ex);
        }

        return true;
    }

    public boolean createContactFromCustomerDto(CustomerDto customerDto, String apiKey, SubsGetResponseConfigDto configDto) throws GetResponseIntegrationException {
        logger.info("createContactFromCustomerDto: " + customerDto + ", apiKey: " + apiKey + ", configDto: " + configDto);

        try {
            String url = getResponseServerProperties.getContactUrl();
            URL obj = new URL(url);
            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.POST_METHOD, apiKey);

            // Request Body
            JsonObject requestBody = new JsonObject();
            MyGetResponseUtil.mapCustomerDto2GetResponseContact(customerDto, configDto, requestBody);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(requestBody.toString());
            wr.flush();

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 202) {
                logger.error("createContactFromCustomerDto fail: " + responseCode);
                return false;
            }
        }
        catch (Exception ex) {
            throw new GetResponseIntegrationException(ex);
        }

        return true;
    }

//    public String createCustomFields(SubsGetResponseConfigDto configDto, String fieldName) throws GetResponseIntegrationException {
//        logger.info("createCustomFields: " + configDto + ", fieldName: " + fieldName);
//
//        String apiKey = configDto.getApiKey();
//        try {
//            String url = getResponseServerProperties.getCustomFieldUrl();
//            URL obj = new URL(url);
//            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.POST_METHOD, apiKey);
//
//            // Request Body
//            JsonObject requestBody = new JsonObject();
//            MyGetResponseUtil.mapFieldName2GetResponseCustomField(fieldName, requestBody);
//
//            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
//            wr.write(requestBody.toString());
//            wr.flush();
//
//            // Get Response
//            int responseCode = con.getResponseCode();
//            logger.info("Sending 'POST' request to URL : " + url);
//            logger.info("Response Code : " + responseCode);
//
//            if (responseCode != 201) {
//                logger.error("createCustomFields fail: " + responseCode);
//                return null;
//            }
//            else {
//                // process response
//                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // check response
//                JsonElement responseElement = new JsonParser().parse(response.toString());
//                if (responseElement == null) {
//                    logger.error("createCustomFields response empty: " + responseCode);
//                    return null;
//                }
//
//                JsonObject customFieldObject = responseElement.getAsJsonObject();
//                JsonElement customFieldIdElement = customFieldObject.get("customFieldId");
//                if (customFieldIdElement == null) {
//                    logger.error("createCustomFields response empty: " + responseCode);
//                    return null;
//                }
//                String customFieldId = customFieldIdElement.getAsString();
//                return customFieldId;
//            }
//        }
//        catch (Exception ex) {
//            throw new GetResponseIntegrationException(ex);
//        }
//    }

    public GetResponseContact getContactByEmail(String email, String apiKey) throws GetResponseIntegrationException {
        logger.info("getContactByEmail: " + email + ", apiKey: " + apiKey);

        GetResponseContact contact = new GetResponseContact();
        try {
            String url = MyGetResponseUtil.buildGetContactUrl(getResponseServerProperties.getContactUrl(), email);
            URL obj = new URL(url);
            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.GET_METHOD, apiKey);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 200) {
                logger.error("getContactByEmail failed: " + responseCode);
                return null;
            }
            else {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseElement = new JsonParser().parse(response.toString());
                if (responseElement == null) {
                    return null;
                }

                JsonArray contactsArray = responseElement.getAsJsonArray();
                if (contactsArray != null && contactsArray.size() > 0) {
                    JsonObject contactObject = contactsArray.get(0).getAsJsonObject();
                    JsonElement contactIdElement = contactObject.get("contactId");
                    if (contactIdElement != null) {
                        String contactId = contactIdElement.getAsString();
                        if (!StringUtils.isBlank(contactId)) {
                            contact.setId(contactId);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            throw new GetResponseIntegrationException(ex);
        }

        return contact;

    }

    public String getCustomFieldIdByName(String name, SubsGetResponseConfigDto configDto) throws GetResponseIntegrationException {
        logger.info("getCustomFieldIdByName: " + name + ", configDto: " + configDto);

        String apiKey = configDto.getApiKey();
        try {
            String url = getResponseServerProperties.getCustomFieldUrl();
            URL obj = new URL(url);
            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.GET_METHOD, apiKey);

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 200) {
                logger.error("getCustomFieldIdByName fail: " + responseCode);
                return null;
            }
            else {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseElement = new JsonParser().parse(response.toString());
                if (responseElement == null) {
                    logger.error("getCustomFieldIdByName response empty: " + responseCode);
                    return null;
                }

                JsonArray customFieldsArray = responseElement.getAsJsonArray();
                for (JsonElement customFieldElement : customFieldsArray) {
                    JsonObject customFieldObject = customFieldElement.getAsJsonObject();
                    JsonElement nameElement = customFieldObject.get("name");
                    if (nameElement != null && nameElement.getAsString().equals(name)) {
                        JsonElement idElement = customFieldObject.get("customFieldId");
                        if (idElement != null) {
                            return idElement.getAsString();
                        }
                    }
                }

                return null;
            }
        }
        catch (Exception ex) {
            throw new GetResponseIntegrationException(ex);
        }
    }

    public boolean test(String apiKey, String campaignDefaultId) {
        logger.info("test: " + apiKey + ", campaignDefaultId: " + campaignDefaultId);

        URL obj;
        try {
            String url = getResponseServerProperties.getCampaignUrl() + "/" + campaignDefaultId;
            obj = new URL(url);
            HttpURLConnection con = MyGetResponseUtil.createConnection(obj, MyGetResponseUtil.GET_METHOD, apiKey);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 200) {
                logger.error("test fail: " + responseCode);
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            logger.error("Error test: ", e);
            return false;
        }
    }
}