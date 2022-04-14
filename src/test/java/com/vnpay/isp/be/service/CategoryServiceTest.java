package com.vnpay.isp.be.service;

import com.vnpay.isp.be.model.LangPhrase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * @since 6/28/2021
 **/
@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    CategoryService categoryService;

    @Test
    public void getPhrases_ISP_notEmpty() {
        String rsp = "{\"data\": [{}]}";
        when(restTemplate.exchange(ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(rsp, HttpStatus.OK));
        List<LangPhrase> langPhrases = categoryService.getPhrases("ISP");
        assertThat(langPhrases).isNotEmpty();
    }
}
