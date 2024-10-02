package com.anle.identity.service.dto.transaction;

import com.anle.identity.service.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {
    String id;
    String senderID;
    String recipientID;
    Float amount;
}
