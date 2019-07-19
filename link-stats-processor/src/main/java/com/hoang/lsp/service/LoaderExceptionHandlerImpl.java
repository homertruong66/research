package com.hoang.lsp.service;

import java.io.*;
import java.util.List;

import com.hoang.lsp.exception.BusinessException;
import com.hoang.lsp.model.IncrementEvent;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;

@Service("loaderExceptionHandler")
public class LoaderExceptionHandlerImpl implements LoaderExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoaderExceptionHandlerImpl.class);

    @Value("${exception.handler.file.location}")
    private String handlerFileLocation;

    @Value("${exception.handler.file.name}")
    private String handlerFileName;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void writeFailedEventsToFile (List<IncrementEvent> incrementEventList) throws BusinessException {
        File file = new File(createFilePath());
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(FileUtils.openOutputStream(file, true), "UTF-8"))) {
            write(writer, incrementEventList);
        }
        catch (IOException e) {
            LOGGER.error("Cannot write StatsList to file. Exception={}", e);
            throw new BusinessException(e + "");
        }
    }

    private String createFilePath () {
        String dateTimePattern = "yyyy-MM-dd-hh";
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateTimePattern);
        String dateTime = formatter.print(new DateTime());
        return Joiner.on('/').join(handlerFileLocation, dateTime, handlerFileName);
    }

    public void write (Writer writer, List<IncrementEvent> incrementEventList) throws IOException {
        writer.append(objectMapper.writeValueAsString(incrementEventList));
        writer.append(System.lineSeparator());
    }

}
