package com.example.demo.producer;

import com.example.demo.producer.domain.Primary;
import com.example.demo.producer.domain.Secondary;
import com.example.demo.producer.json.ModelWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hungnguyenv3 on 10/16/15.
 */
@SpringBootApplication
public class Application {

    private final static Logger log = LoggerFactory.getLogger(Application.class);
    private final static ObjectMapper mapper = new ObjectMapper();
    private final static DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

    final static String queueName = "si.test.queue";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(10000000);
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        return executor;
    }

    @Bean
    public List<Declarable> defaultChannel() {
        return Arrays.asList(
                new DirectExchange("si.test.exchange"),
                new Queue("si.test.queue", true),
                new Binding("si.test.queue",
                        Binding.DestinationType.QUEUE,
                        "si.test.exchange",
                        "si.test.binding",
                        null
                )
        );
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return (args) -> {
            while (true) {
                System.out.print("Number of messages to send: ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                final int count = Integer.parseInt(input);
                final CountDownLatch doneSignal = new CountDownLatch(count);
                for (int i = 0; i < count; i++) {
                    executor().execute(worker(doneSignal));
                }
                doneSignal.await();
            }
        };
    }

    @Bean
    @Scope(value = "prototype")
    public Runnable worker(CountDownLatch doneSignal) {
        return () -> {
            ModelWrapper wrapper = new ModelWrapper();
            wrapper.setPrimary(new Primary("1 - " + formatter.format(new Date())));
            wrapper.setSecondary(new Secondary("2 - " + formatter.format(new Date())));
            try {
                String json = mapper.writeValueAsString(wrapper);
                log.info("Sending: <{}>", json);
                rabbitTemplate.convertAndSend(queueName, json.getBytes());
            } catch (JsonProcessingException e) {
                log.warn("Failed to serialize: <{}>", wrapper);
                e.printStackTrace();
            }
            doneSignal.countDown();
        };
    }
}
