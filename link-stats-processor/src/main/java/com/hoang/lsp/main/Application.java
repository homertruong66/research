package com.hoang.lsp.main;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hoang.lsp.model.Constant;

public class Application {

    static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // configure log4j
        BasicConfigurator.configure();

        new ClassPathXmlApplicationContext(Constant.DATA_PROCESSOR_APP_CONFIG_PATH);
    }
}
