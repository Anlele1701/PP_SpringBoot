package com.anle.identity.service.controller;

import com.anle.identity.service.dto.ApiResponse;
import com.anle.identity.service.dto.transaction.TransactionRequest;
import com.anle.identity.service.dto.transaction.TransactionResponse;
import com.anle.identity.service.dto.user.response.UserResponse;
import com.anle.identity.service.service.TransactionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping
    ApiResponse<TransactionResponse> transferMoney(@RequestBody TransactionRequest request) {
        return ApiResponse.<TransactionResponse>builder()
                .result(transactionService.transferMoney(request)).
                build();
    }
}
