package com.anle.identity.service.consumer;

import com.anle.identity.service.dto.transaction.TransactionRequest;
import com.anle.identity.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConsumer {
    private static final Logger logger = LoggerFactory.getLogger(UserConsumer.class);

    @Autowired
    private UserService userService;

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consume(TransactionRequest request) {
        logger.info(getClass() + " received request" + request.toString());
        userService.transferMoney(request.getSenderID(), request.getRecipientID(), request.getAmount());
    }
}
