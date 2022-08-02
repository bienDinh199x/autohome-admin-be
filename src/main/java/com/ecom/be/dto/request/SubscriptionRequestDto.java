package com.ecom.be.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDto {

    String topicName;
    List<String> tokens;
}
