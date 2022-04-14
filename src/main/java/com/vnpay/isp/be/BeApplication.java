package com.vnpay.isp.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author vinhnt
 */
@SpringBootApplication
public class BeApplication {
     public static void main(String[] args) {
        try {
            SpringApplication.run(BeApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
