package com.babyblackdog.ddogdog.notification;

import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class NotificationService {

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EmitterRepository emitterRepository;
    private final JwtSimpleAuthentication authentication;

    public NotificationService(EmitterRepository emitterRepository, JwtSimpleAuthentication authentication) {
        this.emitterRepository = emitterRepository;
        this.authentication = authentication;
    }

    public SseEmitter subscribe(String email) {
        SseEmitter emitter = createEmitter(email);
        sendToClient(email, "EventStream Created. [email=" + email + "]");
        return emitter;
    }

    public void notify(Object event) {
        sendToClient(authentication.getEmailAddress(), event);
    }

    private SseEmitter createEmitter(String email) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(email, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteByEmail(email));
        emitter.onTimeout(() -> emitterRepository.deleteByEmail(email));
        emitter.onError(e -> emitterRepository.deleteByEmail(email));

        return emitter;
    }

    private void sendToClient(String email, Object data) {
        SseEmitter emitter = emitterRepository.get(email);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter
                        .event()
                        .id(email)
                        .name("alarm")
                        .data(data));
            } catch (IOException exception) {
                emitterRepository.deleteByEmail(email);
                emitter.completeWithError(exception);
            }
        }
    }

}
