package com.babyblackdog.ddogdog.notification;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping(path = "/notifications", produces = APPLICATION_JSON_VALUE)
public class NotificationController {

    private final NotificationService notificationService;
    private final JwtSimpleAuthentication authentication;

    public NotificationController(NotificationService notificationService, JwtSimpleAuthentication authentication) {
        this.notificationService = notificationService;
        this.authentication = authentication;
    }

    @GetMapping(path = "/subscribe")
    public SseEmitter subscribe() {
        return notificationService.subscribe(authentication.getEmailAddress());
    }

    @PostMapping(path = "/send-data")
    public void sendData() {
        notificationService.notify("alarm");
    }

}
