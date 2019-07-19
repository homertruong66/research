package com.rms.rms.integration;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rms.rms.common.config.properties.GetflyServerProperties;
import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.util.MyGetflyUtil;
import com.rms.rms.integration.exception.GetflyIntegrationException;
import com.rms.rms.integration.model.GetflyAccount;
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
public class GetflyClient {

    private Logger logger = Logger.getLogger(GetflyClient.class);

    @Autowired
    private GetflyServerProperties getflyServerProperties;

    public boolean createOrder(OrderDto orderDto, String baseUrl, String apiKey, String accountCode) throws GetflyIntegrationException {
        logger.info("createOrder: " + orderDto + " baseUrl: " + baseUrl + " apiKey: " + apiKey + " accountCode: " + accountCode);

        try {
            String url = MyGetflyUtil.buildCreateOrderUrl(baseUrl, getflyServerProperties.getOrderUrl());
            URL obj = new URL(url);
            HttpURLConnection con = MyGetflyUtil.createConnection(obj, MyGetflyUtil.POST_METHOD, apiKey);

            // Request Body
            JsonObject requestBody = new JsonObject();
            MyGetflyUtil.mapOrderDto2GetflyOrder(orderDto, accountCode, requestBody);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(requestBody.toString());
            wr.flush();

            // Get Response
            int responseCode = con.getResponseCode();
            logger.info("Sending 'POST' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode != 200) {
                logger.error("createOrder fail: " + responseCode);
                return false;
            }
        }
        catch (Exception ex) {
            throw new GetflyIntegrationException(ex);
        }

        return true;
    }

    public GetflyAccount getAccountByEmail(String email, String baseUrl, String apiKey) throws GetflyIntegrationException {
        logger.info("getAccountByEmail: " + email + " baseUrl: " + baseUrl + " apiKey: " + apiKey);

        GetflyAccount account = new GetflyAccount();
        try {
            String url = MyGetflyUtil.buildGetAccountUrl(baseUrl, getflyServerProperties.getGetAccountUrl(), email);
            URL obj = new URL(url);
            HttpURLConnection con = MyGetflyUtil.createConnection(obj, MyGetflyUtil.GET_METHOD, apiKey);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

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
                JsonElement recordsElement = responseObject.get("records");
                if (recordsElement == null) {
                    return null;
                }

                JsonArray accountsArray = recordsElement.getAsJsonArray();
                if (accountsArray != null && accountsArray.size() > 0) {
                    JsonObject accountObject = accountsArray.get(0).getAsJsonObject();
                    MyGetflyUtil.mapResponseJson2GetflyAccount(accountObject, account);
                }
            }
            else {
                logger.error("getAccountByEmail failed: " + responseCode);
            }
        }
        catch (Exception ex) {
            throw new GetflyIntegrationException(ex);
        }

        return account;

    }

    public boolean test(String baseUrl, String apiKey) {
        logger.info("test: " + baseUrl + " apiKey: " + apiKey);

        URL obj;
        try {
            String url = baseUrl + getflyServerProperties.getGetAccountUrl();
            obj = new URL(url);
            HttpURLConnection con = MyGetflyUtil.createConnection(obj, MyGetflyUtil.GET_METHOD, apiKey);

            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            if (responseCode == 200) {
                return true;
            }
            else {
                logger.error("test fail: " + responseCode);
            }
        }
        catch (Exception e) {
            logger.error("Error test: ", e);
        }
        return false;
    }
}