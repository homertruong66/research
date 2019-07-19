package com.hoang.srj.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class HttpLogFilter extends GenericFilterBean {

    private Logger logger = Logger.getLogger(HttpLogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info(request.toString());
        logger.info(response.toString());

        chain.doFilter(request, response);
    }

}