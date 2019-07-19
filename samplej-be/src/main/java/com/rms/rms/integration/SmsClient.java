package com.rms.rms.integration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.rms.rms.common.config.properties.SmsServerProperties;
import com.rms.rms.integration.exception.SmsIntegrationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
@Scope("prototype")
public class SmsClient {

    private Logger logger = Logger.getLogger(SmsClient.class);

    @Autowired
    private SmsServerProperties smsServerProperties;

    public String send(String phone, String message) throws SmsIntegrationException {
        logger.info("send: " + phone + " - message: " + message);

        URL obj;
        try {
            StringBuilder restUrlBuilder = new StringBuilder();
            restUrlBuilder.append(smsServerProperties.getUrl());
            restUrlBuilder.append("?ApiKey=" + URLEncoder.encode(smsServerProperties.getApiKey(), "UTF-8"));
            restUrlBuilder.append("&SecretKey=" + URLEncoder.encode(smsServerProperties.getSecretKey(), "UTF-8"));
            restUrlBuilder.append("&Phone=" + URLEncoder.encode(phone, "UTF-8"));
            restUrlBuilder.append("&Content=" + URLEncoder.encode(message, "UTF-8"));
            restUrlBuilder.append("&SmsType=2");
            restUrlBuilder.append("&Brandname=DN.TINHGON");

            obj = new URL(restUrlBuilder.toString());

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // you need to encode ONLY the values of the parameters
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            int responseCode = con.getResponseCode();
            logger.debug("Sending 'GET' request to URL : " + restUrlBuilder.toString());
            logger.debug("Response Code : " + responseCode);

            // process response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // check response and print result
            JsonElement jsonElement = new JsonParser().parse(response.toString());
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonPrimitive codeResultObject = jsonObject.getAsJsonPrimitive("CodeResult");
            JsonPrimitive smsidObject = jsonObject.getAsJsonPrimitive("SMSID");
            JsonPrimitive errorMessageObject = jsonObject.getAsJsonPrimitive("ErrorMessage");
            if (responseCode == 200) {
                // Check CodeResult from response to make sure sms has been sent successfully
                if (codeResultObject.getAsInt() != 100) {
                    if (errorMessageObject != null) {
                        logger.info(errorMessageObject.getAsString());
                    }
                    throw new RuntimeException("Request ok but error with your account");
                }
            }
            logger.info(response.toString());

            String result = "sms_id: ";
            if (smsidObject != null) {
                result += smsidObject.getAsString();
            }
            else {
                result += "not found";
            }

            return result;
        }
        catch (Exception ex) {
            throw new SmsIntegrationException(ex);
        }
    }
}
