package com.rms.rms.controller.filter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * homertruong
 */

@Component
public class HttpLogFilter extends GenericFilterBean {

    private Logger logger = Logger.getLogger(HttpLogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
//        StringBuilder requestSB = new StringBuilder();
//        requestSB.append("{");
//        Map<String, String[]> requestMap = request.getParameterMap();
//        requestMap.keySet().stream().forEach(key -> {
//            String[] values = requestMap.get(key);
//            requestSB.append(key + ": " + Arrays.toString(values) + ", ");
//        });
//        requestSB.append("}");
        HttpServletRequest hsr = ((HttpServletRequest) request);
        logger.info(hsr.getMethod() + " " + hsr.getRequestURI());// + " , Params: " + requestSB.toString());

        chain.doFilter(request, response);
    }

}