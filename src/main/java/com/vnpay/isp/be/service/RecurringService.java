package com.vnpay.isp.be.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vnpay.isp.be.common.Utils;
import com.vnpay.isp.be.dto.mapper.IspTxnMapper;
import com.vnpay.isp.be.dto.request.CardRequest;
import com.vnpay.isp.be.dto.response.*;
import com.vnpay.isp.be.enums.CardConfig;
import com.vnpay.isp.be.enums.ErrorCode;
import com.vnpay.isp.be.enums.VerifyErrorCode;
import com.vnpay.isp.be.model.CardVerify;
import com.vnpay.isp.be.model.CustomerInfo;
import com.vnpay.isp.be.model.IspTxn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author sonnt2 (sonnt2@vnpay.vn)
 * @since 10/20/2021
 **/
@Slf4j
@Service
public class RecurringService extends BaseService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    static final String TRAN_ID_KEY = "TRAN_ID_%s";

    protected Response<IspTxn> getIspTxn(String accessToken) {
        Response<IspTxn> response = new Response<>();
        try {
            log.info("Begin getIspTxn: accessToken={}", accessToken);
            String url = ispApiUrl + "/recurring/get-isp-txn?accessToken=" + accessToken;
            String rsp = restGet(url);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End getIspTxn: accessToken={} - rspData={}", accessToken, rsp);
        } catch (Exception ex) {
            log.error("Exception getIspTxn: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<IspTxnDto> getIspTxnDto(String accessToken) {
        Response<IspTxnDto> response = new Response<>();
        try {
            Response<IspTxn> rsp = getIspTxn(accessToken);
            IspTxn ispTxn = rsp.getData();
            if (ispTxn != null) { //Txn found
                IspTxnDto ispTxnDTO = IspTxnMapper.ispTxnToIspTxnDtoForRecurring(ispTxn);
                response.setRspCode(rsp.getRspCode());
                response.setRspMsg(rsp.getRspMsg());
                response.setRspAlias(rsp.getRspAlias());
                response.setData(ispTxnDTO);
            } else { //Txn not found
                response.setRspCode(ErrorCode.ISP_TXN_NOT_FOUND.getCode());
                response.setRspMsg(ErrorCode.ISP_TXN_NOT_FOUND.getMessage());
                response.setRspAlias(ErrorCode.ISP_TXN_NOT_FOUND.getAlias());
            }
        } catch (Exception ex) {
            log.error("Exception getIspTxnDto: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<String> cancelIspTxn(String accessToken) {
        Response<String> response = new Response<>();
        try {
            log.info("Begin cancelIspTxn: accessToken={}", accessToken);
            String url = ispApiUrl + "/recurring/cancel?accessToken=" + accessToken;
            String rsp = restPost(url, null);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End cancelIspTxn: accessToken={} - rspData={}", accessToken, rsp);
        } catch (Exception ex) {
            log.error("Exception cancelIspTxn: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<VerifyRsp> verifyApi(CardVerify cardVerify, String accessToken, String cardNumber) {
        Response<VerifyRsp> response = new Response<>();
        try {
            log.info("Begin verifyApi: accessToken={} - cardNumber={}", accessToken, cardNumber);

            String url = ispApiUrl + "/recurring/verify";
            String rsp = restPost(url, Utils.toJson(cardVerify));

            RspObj verifyApiRsp = Utils.fromJson(rsp, RspObj.class);
            response.setRspCode(verifyApiRsp.getCode());
            response.setRspMsg(verifyApiRsp.getMessage());
            response.setRspAlias(verifyApiRsp.getAlias());
            if (VerifyErrorCode.SUCCESS.getCode().equals(verifyApiRsp.getCode()) && !StringUtils.isEmpty(verifyApiRsp.getBankResponData())) { //Verify success
                BankRsp bankRsp = Utils.fromJson(verifyApiRsp.getBankResponData(), BankRsp.class);
                String formHtml = "<iframe name='ddc-iframe' height='1' width='1' style='display: none;'></iframe>" +
                        "<form id='ddc-form' target='ddc-iframe' method='POST' action='" + bankRsp.getFormAction() + "'>" +
                        "<input type='hidden' name='JWT' value='" + bankRsp.getAccessToken() + "' />" +
                        "</form>";
                VerifyRsp verifyRsp = new VerifyRsp();
                verifyRsp.setData(formHtml);
                verifyRsp.setNotifyUrl(bankRsp.getNotifyUrl());
                response.setData(verifyRsp);
                log.info("End verifyApi: success=true - accessToken={} - cardNumber={} - rspData={}",
                        accessToken, cardNumber, rsp);
            } else { //Verify fail
                String tranId = verifyApiRsp.getData();
                if (!StringUtils.isEmpty(tranId)) { //Cache tranId if TXN was created
                    String tranIdKey = String.format(TRAN_ID_KEY, accessToken);
                    redisTemplate.opsForValue().setIfAbsent(tranIdKey, tranId, 15, TimeUnit.MINUTES);
                }
                log.error("End verifyApi: success=false, accessToken={} - cardNumber={} - rspData={}",
                        accessToken, cardNumber, rsp);
            }
        } catch (Exception ex) {
            log.error("Exception verifyApi: accessToken={} - cardNumber={}",
                    accessToken, cardNumber, ex);
            response.setRspCode(VerifyErrorCode.EXCEPTION.getCode());
            response.setRspMsg(VerifyErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(VerifyErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<VerifyRsp> verifyCard(String accessToken, CardRequest cardRequest, String ipAddr, String userAgent) {
        Response<VerifyRsp> response = new Response<>();
        String cardNumber = cardRequest.getCardNumber();
        if (!log.isDebugEnabled()) {
            cardNumber = Utils.maskCardNumber(cardNumber);
        }
        try {
            log.info("Begin verifyCard: accessToken={} - cardNumber={} - ipAddr={} - userAgent={}",
                    accessToken, cardNumber, ipAddr, userAgent);
            Response<IspTxn> rspTxn = getIspTxn(accessToken);
            IspTxn ispTxn = rspTxn.getData();
            if (ispTxn != null) { //IspTxn exist
                CardVerify cardVerify = new CardVerify();
                String tranIdKey = String.format(TRAN_ID_KEY, accessToken);
                String tranIdCached = (String) redisTemplate.opsForValue().get(tranIdKey); // get TranId cached from Redis
                if (!StringUtils.isEmpty(tranIdCached)) {
                    cardVerify.setTranId(Long.parseLong(tranIdCached));
                }
                CardConfig cardConfig = CardConfig.getCardConfigByCardNumber(cardRequest.getCardNumber());
                cardVerify.setBankCode(cardConfig.getScheme());
                cardVerify.setToken(accessToken);
                cardVerify.setCardType(cardConfig.getType());
                cardVerify.setCardNumber(cardRequest.getCardNumber());
                cardVerify.setCardDate(cardRequest.getCardDate());
                cardVerify.setIpAddr(ipAddr);
                cardVerify.setCvv(cardRequest.getCvcCvv());
                cardVerify.setCommand(ispTxn.getCommand());

                CustomerInfo customerInfo = new CustomerInfo();

                String fullName = Utils.unAccent(cardRequest.getCardHolder());
                String firstName = fullName.split(" ")[0];
                String lastName = fullName.substring(firstName.length()).trim();

                cardVerify.setCardHolder(fullName);

                customerInfo.setFirstName(firstName);
                customerInfo.setLastName(lastName);
                customerInfo.setFullName(fullName);
                customerInfo.setEmail(cardRequest.getEmail());
                customerInfo.setAddress1(Utils.unAccent(cardRequest.getAddress()));
                customerInfo.setCity(Utils.unAccent(cardRequest.getCity()));
                customerInfo.setCountry(cardRequest.getCountry());

                cardVerify.setAddData(Utils.toJson(customerInfo));
                cardVerify.setUserAgent(userAgent);
                cardVerify.setField1(ispTxn.getRecurringId());

                response = verifyApi(cardVerify, accessToken, cardNumber);
            } else { //IspTxn not exist
                response.setRspCode(VerifyErrorCode.ISP_TXN_NOT_FOUND.getCode());
                response.setRspMsg(VerifyErrorCode.ISP_TXN_NOT_FOUND.getMessage());
                response.setRspAlias(VerifyErrorCode.ISP_TXN_NOT_FOUND.getAlias());
                log.error("Error verifyCard: Isp Txn not found, accessToken={}", accessToken);
            }
            log.info("End verifyCard: accessToken={} - cardNumber={} - rspCode={} - rspMsg={}",
                    accessToken, cardNumber, response.getRspCode(), response.getRspMsg());
        } catch (Exception ex) {
            log.error("Exception verifyCard: accessToken={} - cardNumber={}", accessToken, cardNumber, ex);
            response.setRspCode(VerifyErrorCode.EXCEPTION.getCode());
            response.setRspMsg(VerifyErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(VerifyErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }
}
