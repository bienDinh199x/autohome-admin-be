package com.autohome.be.dto.mapper;

import com.autohome.be.dto.response.IspTxnDto;
import com.autohome.be.model.IspTxn;
import org.springframework.util.StringUtils;

public class IspTxnMapper {

    public static IspTxnDto ispTxnToIspTxnDtoForIsp(IspTxn ispTxn) {
        IspTxnDto dto = new IspTxnDto();
        dto.setLocale(ispTxn.getLocale());
        dto.setTmnLogoImg(ispTxn.getTmnLogoImg());
        dto.setTmnName(ispTxn.getTmnName());
        dto.setAmount(ispTxn.getAmount());
        dto.setCurrCode(ispTxn.getCurrCode());
        dto.setOrderReference(ispTxn.getOrderReference());
        dto.setTmnCode(ispTxn.getTmnCode());
        dto.setIssuerCode(ispTxn.getIssuerCode());
        dto.setIssuerName(ispTxn.getIssuerName());
        dto.setScheme(ispTxn.getScheme());
        dto.setRecurringAmount(ispTxn.getRecurringAmount());
        dto.setRecurringFrequency(ispTxn.getRecurringFrequency());
        dto.setRecurringNumberOfIsp(ispTxn.getRecurringNumberOfIsp());
        dto.setOrderInfo(ispTxn.getOrderInfo());
        dto.setTotalIspAmount(ispTxn.getTotalIspAmount());
        dto.setIspPayMethod(ispTxn.getIspPayMethod());
        if (StringUtils.isEmpty(ispTxn.getIdentityCode())) {
            dto.setNotHaveIdentityCode(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToMobile())) {
            dto.setNotHaveMobile(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToForename()) || StringUtils.isEmpty(ispTxn.getBillToSurname())) {
            dto.setNotHaveCardHolder(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToEmail())) {
            dto.setNotHaveEmail(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToAddress())) {
            dto.setNotHaveAddress(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToCity())) {
            dto.setNotHaveCity(true);
        }
        if (StringUtils.isEmpty(ispTxn.getBillToCountry())) {
            dto.setNotHaveCountry(true);
        }
        return dto;
    }

    public static IspTxnDto ispTxnToIspTxnDtoForRecurring(IspTxn ispTxn) {
        IspTxnDto dto = new IspTxnDto();
        dto.setLocale(ispTxn.getLocale());
        dto.setTmnLogoImg(ispTxn.getTmnLogoImg());
        dto.setTmnName(ispTxn.getTmnName());
        dto.setCommand(ispTxn.getCommand());
        return dto;
    }
}
