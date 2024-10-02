package com.anle.identity.service.dto.transaction;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    String id;
    String senderID;
    String recipientID;
    Float amount;
}
