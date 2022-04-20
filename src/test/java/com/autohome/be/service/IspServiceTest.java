package com.autohome.be.service;

import com.autohome.be.enums.CardConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IspServiceTest {

//    @Autowired
//    IspService ispService;
//
//    @Test
//    public void verifyCardScheme_cardSchemeSupported_true() {
//        boolean isMatch = ispService.verifyCardScheme(CardConfig.VISA.getScheme(), "4123456789876543");
//        assertThat(isMatch).isTrue();
//    }
//
//    @Test
//    public void verifyCardScheme_cardNumberNotSupported_false() {
//        boolean isMatch = ispService.verifyCardScheme(CardConfig.VISA.getScheme(), "5123456789876543");
//        assertThat(isMatch).isFalse();
//    }
}
