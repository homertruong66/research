package com.example.demo.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hungnguyenv3 on 10/19/15.
 */
@Configuration
@EnableRabbit
public class RabbitConfig {

    @Value("${rabbitmq.concurrent.consumers}")
    private int concurrentConsumers;

    @Value("${rabbitmq.concurrent.consumers.max}")
    private int maxConcurrentConsumers;

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setConcurrentConsumers(concurrentConsumers);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        return factory;
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

}
