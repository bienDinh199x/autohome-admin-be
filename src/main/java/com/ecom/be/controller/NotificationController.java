package com.ecom.be.controller;

import com.ecom.be.config.AppConfig;
import com.ecom.be.dto.request.NotificationRequestDto;
import com.ecom.be.dto.request.SubscriptionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private AppConfig config;

    @PostMapping("/send")
    public String sendNotification() {
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        config.sendPnsToDevice(notificationRequestDto);
        return "OK";
    }

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        config.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        config.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
        return config.sendPnsToDevice(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return config.sendPnsToTopic(notificationRequestDto);
    }

}
