package com.autohome.be.controller;

import com.autohome.be.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CategoryService categoryService;
//
//    @Test
//    public void ping__pong() throws Exception {
//        mockMvc.perform(get("/category/ping"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("pong"));
//    }
//
//    @Test
//    public void getTranslations_langSupported_notEmpty() throws Exception {
//        String lang = "en";
//
//        Map<String, String> mockedValue = Map.of("card_number", "Card number");
//        when(categoryService.getTranslations(lang)).thenReturn(mockedValue);
//
//        mockMvc.perform(get("/category/get-translations").param("lang", lang))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"card_number\":\"Card number\"}"));
//    }
//
//    @Test
//    public void getErrorText_labelAndLangSupported_notEmpty() throws Exception {
//        String label = "invalid_isp_conditions";
//        String lang = "en";
//        when(categoryService.getErrorText(label, lang)).thenReturn("Invalid installment payment conditions");
//
//        mockMvc.perform(get("/category/get-error-text").param("label", label).param("lang", lang))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("Invalid installment payment conditions"));
//    }
}
