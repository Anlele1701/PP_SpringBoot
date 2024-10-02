package com.anle.identity.service.mapstruct;

import com.anle.identity.service.dto.transaction.TransactionRequest;
import com.anle.identity.service.dto.transaction.TransactionResponse;
import com.anle.identity.service.entity.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "senderID", target = "sender.id")
    @Mapping(source = "recipientID", target = "recipient.id")
    Transaction toTransaction(TransactionRequest request);
    @Mapping(source = "sender.id", target = "senderID")
    @Mapping(source = "recipient.id", target = "recipientID")
    TransactionResponse toTransactionResponse(Transaction transaction);
}
