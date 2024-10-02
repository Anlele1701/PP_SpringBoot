package com.anle.identity.service.publisher;

import com.anle.identity.service.dto.transaction.TransactionRequest;
import com.anle.identity.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String key;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        logger.info(String.format(getClass() + " sent message -> %s", message));
        rabbitTemplate.convertAndSend(exchange, key, message);
    }
}
