package com.rms.rms.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rms.rms.common.config.properties.InfusionServerProperties;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.exception.InvalidViewModelException;
import com.rms.rms.common.util.MyInfusionUtil;
import com.rms.rms.integration.exception.InfusionIntegrationException;
import com.rms.rms.integration.model.InfusionContact;
import com.rms.rms.integration.model.InfusionData;
import com.rms.rms.integration.model.InfusionTag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("prototype")
public class InfusionClient {

    private Logger logger = Logger.getLogger(InfusionClient.class);

    @Autowired
    private InfusionServerProperties infusionServerProperties;

    public boolean applyTagsToContact(String contactId, List<String> tagIds, String accessToken) throws InfusionIntegrationException {
        logger.info("applyTagsToContact: " + contactId + " tagIds: " + tagIds + " accessToken: " + accessToken);

        // process view model
        if (tagIds == null || tagIds.size() == 0) {
            throw new InvalidViewModelException("'tagIds' can not be null or empty !");
        }

        URL obj;
        try {
            String url = MyInfusionUtil.buildApplyTagsToContactUrl(infusionServerProperties.getContactUrl(), contactId, accessToken);
            obj = new URL(url);
            HttpURLConnection con = MyInfusionUtil.createConnection(obj, MyInfusionUtil.POST_METHOD);

            // Request Body
            JsonObject requestBody = new JsonObject();

            // add tagIds to request body
            JsonArray reqTagIds = new JsonArray();
            for (String tagId : tagIds) {
                reqTagIds.add(tagId);
            }
            requestBody.add("tagIds", reqTagIds);

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(requestBody.toString());
            wr.flush();

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 200) {
                logger.error("applyTagsToContact returns response code: " + responseCode);
                return false;
            }
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }

        return true;
    }

    public InfusionContact createContact(CustomerDto customerDto, String accessToken) throws InfusionIntegrationException {
        logger.info("createContact: " + customerDto + " accessToken: " + accessToken);

        URL obj;
        try {
            String url = MyInfusionUtil.buildCreateContactUrl(infusionServerProperties.getContactUrl(), accessToken);
            obj = new URL(url);
            HttpURLConnection con = MyInfusionUtil.createConnection(obj, MyInfusionUtil.POST_METHOD);

            // Request Body
            JsonObject requestBody = new JsonObject();
            MyInfusionUtil.mapCustomerDto2Contact(customerDto, requestBody);

            OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
            wr.write(requestBody.toString());
            wr.flush();

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            InfusionContact contact = new InfusionContact();
            if (responseCode == 201) {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseJson = new JsonParser().parse(response.toString());

                MyInfusionUtil.mapResponseJson2InfusionContact(responseJson, contact);
            }
            else {
                logger.error("Create fail: " + responseCode);
            }

            return contact;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }

    public InfusionContact getContact(String contactId, String accessToken) throws InfusionIntegrationException {
        logger.info("get contact: " + contactId + " accessToken: " + accessToken);

        URL obj;
        try {
            String url = MyInfusionUtil.buildGetContactUrl(infusionServerProperties.getContactUrl(), contactId, accessToken);
            obj = new URL(url);
            HttpURLConnection con = MyInfusionUtil.createConnection(obj, MyInfusionUtil.GET_METHOD);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            InfusionContact contact = new InfusionContact();
            if (responseCode == 200) {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseJson = new JsonParser().parse(response.toString());

                MyInfusionUtil.mapResponseJson2InfusionContact(responseJson, contact);
            }
            else {
                logger.error("Get fail: " + responseCode);
            }

            return contact;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }

    public InfusionContact getContactByEmail(String email, String accessToken) throws InfusionIntegrationException {
        logger.info("getContactByEmail: " + email + " accessToken: " + accessToken);

        URL obj;
        try {
            String url = MyInfusionUtil.buildSearchContactUrl(infusionServerProperties.getContactUrl(), email, accessToken);
            obj = new URL(url);
            HttpURLConnection con = MyInfusionUtil.createConnection(obj, MyInfusionUtil.GET_METHOD);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            InfusionContact contact = new InfusionContact();
            if (responseCode == 200) {
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
                JsonObject responseObject = responseElement.getAsJsonObject();
                JsonElement contactsElement = responseObject.get("contacts");
                if (contactsElement == null) {
                    return null;
                }
                JsonArray contactsArray = contactsElement.getAsJsonArray();
                if (contactsArray != null && contactsArray.size() > 0) {
                    JsonObject contactObject = contactsArray.get(0).getAsJsonObject();
                    MyInfusionUtil.mapResponseJson2InfusionContact(contactObject, contact);
                }
            }
            else {
                logger.error("Search fail: " + responseCode);
            }

            return contact;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }

    public InfusionTag getTagByName(String name, String accessToken) throws InfusionIntegrationException {
        logger.info("getTagByName: " + name + " accessToken: " + accessToken);

        URL obj;
        try {
            String url = MyInfusionUtil.buildSearchTagUrl(infusionServerProperties.getTagUrl(), name, accessToken);
            obj = new URL(url);
            HttpURLConnection con = MyInfusionUtil.createConnection(obj, MyInfusionUtil.GET_METHOD);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            InfusionTag tag = new InfusionTag();
            if (responseCode == 200) {
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
                JsonObject responseObject = responseElement.getAsJsonObject();
                JsonElement tagsElement = responseObject.get("tags");
                if (tagsElement == null) {
                    return null;
                }
                JsonArray tagsArray = tagsElement.getAsJsonArray();
                if (tagsArray != null && tagsArray.size() > 0) {
                    JsonObject tagObject = tagsArray.get(0).getAsJsonObject();
                    MyInfusionUtil.mapResponseJson2InfusionTag(tagObject, tag);
                }
            }
            else {
                logger.error("Search tag fail: " + responseCode);
            }

            return tag;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }

    public InfusionData refreshAccessToken(String clientId, String clientSecret, String refreshToken) throws InfusionIntegrationException {
        logger.info("refreshAccessToken: " + clientId + " clientSecret: " + clientSecret + " refreshToken: " + refreshToken);

        URL obj;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("grant_type", "refresh_token");
            params.put("refresh_token", refreshToken);
            String urlParameters  = createUrlParameters(params);

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String refreshAccessTokenUrl = infusionServerProperties.getTokenUrl();
            obj = new URL(refreshAccessTokenUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod(MyInfusionUtil.POST_METHOD);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            conn.setUseCaches(false);
            String basicAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
            conn.setRequestProperty ("Authorization", "Basic " + basicAuth);

            try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write( postData );
            }
            int responseCode = conn.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + refreshAccessTokenUrl);
            logger.info("Response Code : " + responseCode);

            InfusionData data = new InfusionData();
            if (responseCode == 200) {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseJson = new JsonParser().parse(response.toString());

                MyInfusionUtil.mapResponseJson2InfusionData(responseJson, data);
            }
            else {
                logger.error("Refresh Access Token fail: " + responseCode);
            }

            return data;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }

    public InfusionData requestAccessToken(String clientId, String clientSecret, String code, String redirectUri) throws InfusionIntegrationException {
        logger.info("requestAccessToken: " + clientId + " clientSecret: " + clientSecret + " code: " + code + " redirectUri: " + redirectUri);

        URL obj;
        try {
            HashMap<String, String> params = new HashMap<>();
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("code", code);
            params.put("grant_type", "authorization_code");
            params.put("redirect_uri", redirectUri);
            String urlParameters  = createUrlParameters(params);

            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            String requestAccessTokenUrl = infusionServerProperties.getTokenUrl();
            obj = new URL(requestAccessTokenUrl);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod(MyInfusionUtil.POST_METHOD);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            conn.setUseCaches(false);
            try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write( postData );
            }
            int responseCode = conn.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + requestAccessTokenUrl);
            logger.info("Response Code : " + responseCode);

            InfusionData data = new InfusionData();
            if (responseCode == 200) {
                // process response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // check response
                JsonElement responseJson = new JsonParser().parse(response.toString());

                MyInfusionUtil.mapResponseJson2InfusionData(responseJson, data);
            }
            else {
                logger.error("Request Access Token fail: " + responseCode);
            }

            return data;
        }
        catch (Exception ex) {
            throw new InfusionIntegrationException(ex);
        }
    }


    // Utilities
    private String createUrlParameters(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}