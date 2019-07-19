package com.example.demo.consumer.service;

import com.example.demo.consumer.json.ModelWrapper;
import com.example.demo.consumer.repository.PrimaryRepository;
import com.example.demo.consumer.repository.SecondaryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * Created by hungnguyenv3 on 10/19/15.
 */
@Component
public class MainService {

    private static Logger log = LoggerFactory.getLogger(MainService.class);

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PrimaryRepository pRepository;

    @Autowired
    private SecondaryRepository sRepository;

    @Transactional
    @RabbitListener(queues = "si.test.queue")
    public void process(byte[] json) {
        try {
            log.info("Received: <{}>", new String(json));
            ModelWrapper wrapper = mapper.readValue(json, ModelWrapper.class);
            pRepository.save(wrapper.getPrimary());
            sRepository.save(wrapper.getSecondary());
        } catch (IOException e) {
            log.warn("Failed to deserialize: <{}>", new String(json));
            e.printStackTrace();
        }
    }
}
