package com.babyblackdog.ddogdog;

import com.babyblackdog.ddogdog.util.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
class DdogdogApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    @WithMockCustomUser
    void contextLoads() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logger.info("{}", authentication);
    }

}
