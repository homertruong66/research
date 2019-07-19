package com.rms.rms.common.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rms.rms.common.dto.CustomerDto;
import com.rms.rms.common.dto.DiscountCodeAppliedDto;
import com.rms.rms.common.dto.OrderDto;
import com.rms.rms.common.dto.OrderLineDto;
import com.rms.rms.integration.model.GetflyAccount;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Set;

public final class MyGetflyUtil {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    public static String buildCreateOrderUrl(String baseUrl, String orderUrl) {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append(orderUrl);

        return restUrlBuilder.toString();
    }

    public static String buildGetAccountUrl(String baseUrl, String getAccountUrl, String email) throws UnsupportedEncodingException {
        StringBuilder restUrlBuilder = new StringBuilder();
        restUrlBuilder.append(baseUrl);
        restUrlBuilder.append(getAccountUrl);
        restUrlBuilder.append("?email=").append(URLEncoder.encode(email, "UTF-8"));
        restUrlBuilder.append("&limit=1");

        return restUrlBuilder.toString();
    }

    public static HttpURLConnection createConnection(URL url, String method, String apiKey) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);
        con.setRequestProperty("X-API-KEY", apiKey);

        return con;
    }

    public static void mapOrderDto2GetflyOrder(OrderDto orderDto, String accountCode, JsonObject orderObject) {
        if (orderDto == null || orderObject == null) {
            return;
        }
        CustomerDto customerDto = orderDto.getCustomer();
        DiscountCodeAppliedDto discountCodeAppliedDto = orderDto.getDiscountCodeApplied();
        Set<OrderLineDto> orderLineDtos = orderDto.getOrderLines();

        // map order info to request body
        JsonObject orderInfo = new JsonObject();
        if (!StringUtils.isBlank(accountCode)) {
            orderInfo.addProperty("account_code", accountCode);
        }
        orderInfo.addProperty("account_email", customerDto.getEmail());
        orderInfo.addProperty("account_name", customerDto.getFullname());
        if (!StringUtils.isBlank(customerDto.getAddress())) {
            orderInfo.addProperty("account_address", customerDto.getAddress());
        }
        if (!StringUtils.isBlank(customerDto.getPhone())) {
            orderInfo.addProperty("account_phone", customerDto.getPhone());
        }
        Double discount = 0.0;
        if (discountCodeAppliedDto != null) {
            discount = discountCodeAppliedDto.getDiscount();
            orderInfo.addProperty("discount", discount * 100);
            orderInfo.addProperty("discount_amount", discount * orderDto.getTotal());
        }
        orderInfo.addProperty("amount", orderDto.getTotal() * (1 - discount));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        orderInfo.addProperty("order_date", format.format(orderDto.getCreatedAt()));

        orderObject.add("order_info", orderInfo);

        // map orderLines info to request body
        JsonArray products = new JsonArray();
        for (OrderLineDto orderLineDto : orderLineDtos) {
            JsonObject product = new JsonObject();
            product.addProperty("product_code", orderLineDto.getProduct().getCode());
            product.addProperty("product_name", orderLineDto.getProduct().getName());
            product.addProperty("quantity", orderLineDto.getQuantity());
            product.addProperty("price", orderLineDto.getPrice());
            products.add(product);
        }
        orderObject.add("products", products);
    }

    public static void mapResponseJson2GetflyAccount(JsonElement responseJson, GetflyAccount account) {
        if (responseJson == null || account == null) {
            return;
        }

        JsonObject jsonObject = responseJson.getAsJsonObject();

        JsonElement accountCodeElement = jsonObject.get("account_code");
        if (accountCodeElement != null) {
            String accountCode = accountCodeElement.getAsString();
            if (!StringUtils.isBlank(accountCode)) {
                account.setCode(accountCode);
            }
        }

        JsonElement accountNameElement = jsonObject.get("account_name");
        if (accountNameElement != null) {
            String accountName = accountNameElement.getAsString();
            if (!StringUtils.isBlank(accountName)) {
                account.setFullname(accountName);
            }
        }

        JsonElement addressElement = jsonObject.get("address");
        if (addressElement != null) {
            String address = addressElement.getAsString();
            if (!StringUtils.isBlank(address)) {
                account.setAddress(address);
            }
        }

        JsonElement emailElement = jsonObject.get("email");
        if (emailElement != null) {
            String email = emailElement.getAsString();
            if (!StringUtils.isBlank(email)) {
                account.setEmail(email);
            }
        }

        JsonElement phoneElement = jsonObject.get("phone");
        if (phoneElement != null) {
            String phone = phoneElement.getAsString();
            if (!StringUtils.isBlank(phone)) {
                account.setPhone(phone);
            }
        }
    }
}