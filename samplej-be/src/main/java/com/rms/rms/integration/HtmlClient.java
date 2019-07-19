package com.rms.rms.integration;

import com.rms.rms.common.exception.BusinessException;
import com.rms.rms.common.exception.InvalidViewModelException;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class HtmlClient {

    private Logger logger = Logger.getLogger(HtmlClient.class);

    public Document get(String url) throws BusinessException {
        logger.info("get HTML of url: " + url);

        Connection connection = Jsoup.connect(url);
        try {
            return connection.get();
        }
        catch (IOException ioe) {
            logger.error("Error getting HTML from url: " + url, ioe);
            throw new InvalidViewModelException("Error getting HTML from url: " + url);
        }
    }
}
