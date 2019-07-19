package com.rms.rms.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.integration.model.InfusionContact;
import com.rms.rms.integration.model.InfusionData;
import com.rms.rms.integration.model.InfusionTag;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public final class MyInfusionUtil {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    public static String buildApplyTagsToContactUrl(String baseUrl, String contactId, String accessToken) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append("/" + URLEncoder.encode(contactId, "UTF-8"));
        restUrlBuilder.append("/tags");
        restUrlBuilder.append("?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));

        return restUrlBuilder.toString();
    }

    public static String buildCreateContactUrl(String baseUrl, String accessToken) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append("?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));

        return restUrlBuilder.toString();
    }

    public static String buildGetContactUrl(String baseUrl, String contactId, String accessToken) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append("/" + URLEncoder.encode(contactId, "UTF-8"));
        restUrlBuilder.append("?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));

        return restUrlBuilder.toString();
    }

    public static String buildSearchContactUrl(String baseUrl, String email, String accessToken) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append("?access_token=" + URLEncoder.encode(accessToken, "UTF-8"));
        restUrlBuilder.append("&email=" + URLEncoder.encode(email, "UTF-8"));

        return restUrlBuilder.toString();
    }

    public static String buildSearchTagUrl(String baseUrl, String name, String accessToken) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append("?access_token=").append(URLEncoder.encode(accessToken, "UTF-8"));
        restUrlBuilder.append("&name=").append(URLEncoder.encode(name, "UTF-8"));
        restUrlBuilder.append("&limit=1");

        return restUrlBuilder.toString();
    }

    public static boolean checkExpired(Date expiredDate) {
        return !(new Date().before(expiredDate));
    }

    public static HttpURLConnection createConnection(URL url, String method) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod(method);
        if (method.equals(POST_METHOD)) {
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(true);
        }

        return con;
    }

    public static void mapCustomerDto2Contact(CustomerDto customerDto, JsonObject contact) {
        if (customerDto == null || contact == null) {
            return;
        }

        // add customer email to request body
        JsonObject email = new JsonObject();
        JsonArray emails = new JsonArray();
        email.addProperty("email", customerDto.getEmail());
        email.addProperty("field", "EMAIL1");
        emails.add(email);
        contact.add("email_addresses", emails);

        // add customer name to request body
        contact.addProperty("given_name", customerDto.getFullname());

        // add customer phone to request body
        if (customerDto.getPhone() != null) {
            JsonObject phone = new JsonObject();
            JsonArray phones = new JsonArray();
            phone.addProperty("number", customerDto.getPhone());
            phone.addProperty("field", "PHONE1");
            phones.add(phone);
            contact.add("phone_numbers", phones);
        }

        // add customer address to request body
        if (customerDto.getAddress() != null) {
            JsonObject address = new JsonObject();
            JsonArray addresses = new JsonArray();
            address.addProperty("country_code", "VNM");
            address.addProperty("field", "BILLING");
            address.addProperty("line1", customerDto.getAddress());
            addresses.add(address);
            contact.add("addresses", addresses);
        }
    }

    public static void mapResponseJson2InfusionContact(JsonElement responseJson, InfusionContact contact) {
        if (responseJson == null || contact == null) {
            return;
        }

        JsonObject jsonObject = responseJson.getAsJsonObject();

        JsonElement id = jsonObject.get("id");
        if (id != null) {
            contact.setId(id.getAsString());
        }

        JsonElement emailAddressesElement = jsonObject.get("email_addresses");
        if (emailAddressesElement != null) {
            JsonArray emailAddresses = emailAddressesElement.getAsJsonArray();
            if (emailAddresses != null && emailAddresses.size() > 0) {
                JsonObject emailAddress = emailAddresses.get(0).getAsJsonObject();
                JsonElement emailAddressElement = emailAddress.get("email");
                if (emailAddressElement != null) {
                    contact.setEmail(emailAddressElement.getAsString());
                }
            }
        }

        JsonElement phoneNumbersElement = jsonObject.get("phone_numbers");
        if (phoneNumbersElement != null) {
            JsonArray phoneNumbers = phoneNumbersElement.getAsJsonArray();
            if (phoneNumbers != null && phoneNumbers.size() > 0) {
                JsonObject phoneNumber = phoneNumbers.get(0).getAsJsonObject();
                JsonElement phoneNumberElement = phoneNumber.get("number");
                if (phoneNumberElement != null) {
                    contact.setPhone(phoneNumberElement.getAsString());
                }
            }
        }

        JsonElement givenNameElement = jsonObject.get("given_name");
        if (givenNameElement != null) {
            String givenName = givenNameElement.getAsString();
            if (!StringUtils.isBlank(givenName)) {
                contact.setFullname(givenName);
            }
        }

        JsonElement addressesElement = jsonObject.get("addresses");
        if (addressesElement != null) {
            JsonArray addresses = addressesElement.getAsJsonArray();
            if (addresses != null && addresses.size() > 0) {
                JsonObject address = addresses.get(0).getAsJsonObject();
                JsonElement addressElement = address.get("line1");
                if (addressElement != null) {
                    contact.setAddress(addressElement.getAsString());
                }
            }
        }
    }

    public static void mapResponseJson2InfusionData(JsonElement responseJson, InfusionData data) {
        if (responseJson == null || data == null) {
            return;
        }

        JsonObject jsonObject = responseJson.getAsJsonObject();

        JsonElement accessTokenElement = jsonObject.get("access_token");
        if (accessTokenElement != null) {
            String accessToken = accessTokenElement.getAsString();
            if (!StringUtils.isBlank(accessToken)) {
                data.setAccessToken(accessToken);
            }
        }

        JsonElement refreshTokenElement = jsonObject.get("refresh_token");
        if (refreshTokenElement != null) {
            String refreshToken = refreshTokenElement.getAsString();
            if (!StringUtils.isBlank(refreshToken)) {
                data.setRefreshToken(refreshToken);
            }
        }

        JsonElement expiresInElement = jsonObject.get("expires_in");
        if (expiresInElement != null) {
            int expiresIn = expiresInElement.getAsInt();
            data.setExpiresInSeconds(expiresIn);
        }
    }

    public static void mapResponseJson2InfusionTag(JsonElement responseJson, InfusionTag tag) {
        if (responseJson == null || tag == null) {
            return;
        }

        JsonObject jsonObject = responseJson.getAsJsonObject();

        JsonElement id = jsonObject.get("id");
        if (id != null) {
            tag.setId(id.getAsString());
        }

        JsonElement name = jsonObject.get("name");
        if (name != null) {
            tag.setName(name.getAsString());
        }
    }
}