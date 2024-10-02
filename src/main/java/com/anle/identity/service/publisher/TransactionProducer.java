package com.anle.identity.service.publisher;

import com.anle.identity.service.dto.transaction.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.queue.json.name}")
    private String queueJson;
    @Value("${rabbitmq.routing.json.key}")
    private String keyJson;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionProducer.class);


    public void sendTransactionMessage(TransactionRequest request) {
        String message = String.format
                ("User %s transferred %s to User %s", request.getSenderID(), request.getAmount(), request.getRecipientID());
        logger.info(message);
        rabbitTemplate.convertAndSend(exchange, keyJson, request);
    }
}
