package com.vnpay.isp.be.service;

import com.vnpay.isp.be.enums.CardConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * @since 7/12/2021
 **/
@SpringBootTest
public class IspServiceTest {

    @Autowired
    IspService ispService;

    @Test
    public void verifyCardScheme_cardSchemeSupported_true() {
        boolean isMatch = ispService.verifyCardScheme(CardConfig.VISA.getScheme(), "4123456789876543");
        assertThat(isMatch).isTrue();
    }

    @Test
    public void verifyCardScheme_cardNumberNotSupported_false() {
        boolean isMatch = ispService.verifyCardScheme(CardConfig.VISA.getScheme(), "5123456789876543");
        assertThat(isMatch).isFalse();
    }
}
