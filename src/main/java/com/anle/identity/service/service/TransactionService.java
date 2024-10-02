package com.anle.identity.service.service;

import com.anle.identity.service.dto.transaction.TransactionRequest;
import com.anle.identity.service.dto.transaction.TransactionResponse;
import com.anle.identity.service.entity.Transaction;
import com.anle.identity.service.mapstruct.TransactionMapper;
import com.anle.identity.service.publisher.RabbitMQProducer;
import com.anle.identity.service.publisher.TransactionProducer;
import com.anle.identity.service.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class TransactionService {
    TransactionProducer transactionProducer;
    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    public TransactionResponse transferMoney(TransactionRequest request) {
        Transaction transaction = transactionMapper.toTransaction(request);
        TransactionResponse response = transactionMapper.toTransactionResponse(transaction);
        logger.info(getClass() + " transfering money " + request);
        transactionProducer.sendTransactionMessage(request);
        return transactionMapper.toTransactionResponse(transactionRepository.save(transaction));
    }
}
