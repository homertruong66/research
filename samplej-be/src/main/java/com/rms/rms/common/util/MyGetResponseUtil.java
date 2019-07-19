package com.rms.rms.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rms.rms.common.dto.AffiliateDto;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.dto.SubsGetResponseConfigDto;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public final class MyGetResponseUtil {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    public static String buildGetContactUrl(String getContactUrl, String email) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(getContactUrl);
        restUrlBuilder.append("?query[email]=").append(URLEncoder.encode(email, "UTF-8"));

        return restUrlBuilder.toString();
    }

    public static HttpURLConnection createConnection(URL url, String method, String apiKey) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.setRequestProperty("X-Auth-Token", "api-key " + apiKey);

        return con;
    }

    public static void mapAffiliateDto2GetResponseContact(AffiliateDto affiliateDto, SubsGetResponseConfigDto configDto, JsonObject contactObject) {
        if (affiliateDto == null || configDto == null || contactObject == null) {
            return;
        }

        // map affiliate info to request body
        // name
        String name = affiliateDto.getFirstName() + " " + affiliateDto.getLastName();
        contactObject.addProperty("name", name);
        // email
        String email = affiliateDto.getUser().getEmail();
        contactObject.addProperty("email", email);

        JsonArray customFieldValuesArray = new JsonArray(); // birthday, phone_number, address, facebook_link, password, referrer_email
//        // address
//        if (affiliateDto.getAddress() != null) {
//            JsonObject customFieldObject = new JsonObject();
//            customFieldObject.addProperty("customFieldId", configDto.getAddressFieldId());
//
//            JsonArray value = new JsonArray();
//            value.add(affiliateDto.getAddress());
//            customFieldObject.add("value", value);
//            customFieldValuesArray.add(customFieldObject);
//        }

        // phone
        if (affiliateDto.getPhone() != null && configDto.getPhoneFieldId() != null) {
            if (isOldPhonePrefix(affiliateDto.getPhone())) {
                JsonObject customFieldObject = new JsonObject();
                customFieldObject.addProperty("customFieldId", configDto.getPhoneFieldId());

                JsonArray value = new JsonArray();
                String phone = processPhoneString(affiliateDto.getPhone());
                value.add(phone);
                customFieldObject.add("value", value);
                customFieldValuesArray.add(customFieldObject);
            }
        }

        if (!StringUtils.isBlank(affiliateDto.getMetadata())) {
            JsonElement metadataElement = new JsonParser().parse(affiliateDto.getMetadata());
            JsonObject metadataObject = metadataElement.getAsJsonObject();

            // campaign
            JsonElement campaignIdElement = metadataObject.get("campaign_id");
            String campaignId;
            if (campaignIdElement != null) {
                campaignId = campaignIdElement.getAsString();
            }
            else {
                campaignId = configDto.getCampaignDefaultId();
            }
            JsonObject campaignObject = new JsonObject();
            campaignObject.addProperty("campaignId", campaignId);
            contactObject.add("campaign", campaignObject);

//            // birthday
//            JsonElement birthdayElement = metadataObject.get("birthday");
//            if (birthdayElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getBirthdayFieldId());
//
//                String birthday = birthdayElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(birthday);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // facebook_link
//            JsonElement facebookLinkElement = metadataObject.get("facebook_link");
//            if (facebookLinkElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getFacebookLinkFieldId());
//
//                String facebookLink = facebookLinkElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(facebookLink);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // password
//            JsonElement passwordElement = metadataObject.get("password");
//            if (passwordElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getPasswordFieldId());
//
//                String password = passwordElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(password);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // referrer_email
//            JsonElement referrerEmailElement = metadataObject.get("referrer_email");
//            if (referrerEmailElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getReferrerEmailFieldId());
//
//                String referrerEmail = referrerEmailElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(referrerEmail);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
        }
        else {
            String campaignId = configDto.getCampaignDefaultId();
            JsonObject campaignObject = new JsonObject();
            campaignObject.addProperty("campaignId", campaignId);
            contactObject.add("campaign", campaignObject);
        }

        contactObject.add("customFieldValues", customFieldValuesArray);
    }

    public static void mapCustomerDto2GetResponseContact(CustomerDto customerDto, SubsGetResponseConfigDto configDto, JsonObject contactObject) {
        if (customerDto == null || configDto == null || contactObject == null) {
            return;
        }

        // map customer info to request body
        // name
        contactObject.addProperty("name", customerDto.getFullname());
        // email
        contactObject.addProperty("email", customerDto.getEmail());

        JsonArray customFieldValuesArray = new JsonArray(); // birthday, phone_number, address, facebook_link, password, referrer_email
//        // address
//        if (customerDto.getAddress() != null) {
//            JsonObject customFieldObject = new JsonObject();
//            customFieldObject.addProperty("customFieldId", configDto.getAddressFieldId());
//
//            JsonArray value = new JsonArray();
//            value.add(customerDto.getAddress());
//            customFieldObject.add("value", value);
//            customFieldValuesArray.add(customFieldObject);
//        }

        // phone
        if (customerDto.getPhone() != null && configDto.getPhoneFieldId() != null) {
            if (isOldPhonePrefix(customerDto.getPhone())) {
                JsonObject customFieldObject = new JsonObject();
                customFieldObject.addProperty("customFieldId", configDto.getPhoneFieldId());

                JsonArray value = new JsonArray();
                String phone = processPhoneString(customerDto.getPhone());
                value.add(phone);
                customFieldObject.add("value", value);
                customFieldValuesArray.add(customFieldObject);
            }
        }

        if (!StringUtils.isBlank(customerDto.getMetadata())) {
            JsonElement metadataElement = new JsonParser().parse(customerDto.getMetadata());
            JsonObject metadataObject = metadataElement.getAsJsonObject();

            // campaign
            JsonElement campaignIdElement = metadataObject.get("campaign_id");
            String campaignId;
            if (campaignIdElement != null) {
                campaignId = campaignIdElement.getAsString();
            }
            else {
                campaignId = configDto.getCampaignDefaultId();
            }
            JsonObject campaignObject = new JsonObject();
            campaignObject.addProperty("campaignId", campaignId);
            contactObject.add("campaign", campaignObject);

//            // birthday
//            JsonElement birthdayElement = metadataObject.get("birthday");
//            if (birthdayElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getBirthdayFieldId());
//
//                String birthday = birthdayElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(birthday);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // facebook_link
//            JsonElement facebookLinkElement = metadataObject.get("facebook_link");
//            if (facebookLinkElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getFacebookLinkFieldId());
//
//                String facebookLink = facebookLinkElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(facebookLink);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // password
//            JsonElement passwordElement = metadataObject.get("password");
//            if (passwordElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getPasswordFieldId());
//
//                String password = passwordElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(password);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
//
//            // referrer_email
//            JsonElement referrerEmailElement = metadataObject.get("referrer_email");
//            if (referrerEmailElement != null) {
//                JsonObject customFieldObject = new JsonObject();
//                customFieldObject.addProperty("customFieldId", configDto.getReferrerEmailFieldId());
//
//                String referrerEmail = referrerEmailElement.getAsString();
//                JsonArray value = new JsonArray();
//                value.add(referrerEmail);
//                customFieldObject.add("value", value);
//                customFieldValuesArray.add(customFieldObject);
//            }
        }
        else {
            String campaignId = configDto.getCampaignDefaultId();
            JsonObject campaignObject = new JsonObject();
            campaignObject.addProperty("campaignId", campaignId);
            contactObject.add("campaign", campaignObject);
        }

        contactObject.add("customFieldValues", customFieldValuesArray);
    }

    public static void mapFieldName2GetResponseCustomField(String fieldName, JsonObject customFieldObject) {
        if (fieldName == null || customFieldObject == null) {
            return;
        }

        customFieldObject.addProperty("name", fieldName);
        customFieldObject.addProperty("type", "text");
        customFieldObject.addProperty("hidden", "false");
        customFieldObject.add("value", new JsonArray());
    }

    // Utilities
    private static String processPhoneString(String phone) {
        String[] phoneArray = phone.split("", 2);
        if (phoneArray[0].equals("0")) {
            return "+84" + phoneArray[1];
        }
        else {
            return "+84" + phone;
        }
    }

    private static boolean isOldPhonePrefix(String phone) {
        if (phone.startsWith("01") || phone.startsWith("09")) {
            return true;
        }
        if (phone.startsWith("1") || phone.startsWith("9")) {
            return true;
        }
        return false;
    }

}