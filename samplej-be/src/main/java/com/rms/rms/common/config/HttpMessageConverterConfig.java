package com.rms.rms.common.config;

import com.rms.rms.common.util.MyJsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * homertruong
 *
 * Use GsonHttpMessageConverter for HTTP message converter
 */

@Configuration
public class HttpMessageConverterConfig {

    @Bean
    public GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(MyJsonUtil.gson);

        return converter;
    }

}