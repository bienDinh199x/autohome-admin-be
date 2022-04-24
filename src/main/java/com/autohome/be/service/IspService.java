package com.autohome.be.service;

import com.autohome.be.common.Utils;
import com.autohome.be.dto.mapper.IspTxnMapper;
import com.autohome.be.dto.request.CardRequest;
import com.autohome.be.dto.response.*;
import com.autohome.be.enums.CardConfig;
import com.autohome.be.enums.ErrorCode;
import com.autohome.be.enums.VerifyErrorCode;
import com.autohome.be.model.CardVerify;
import com.autohome.be.model.CustomerInfo;
import com.autohome.be.model.IspConfig;
import com.autohome.be.model.IspTxn;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Service
public class IspService extends BaseService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    static final String TRAN_ID_KEY = "TRAN_ID_%s";

    protected Response<IspTxn> getIspTxn(String accessToken) {
        Response<IspTxn> response = new Response<>();
        try {
            log.info("Begin getIspTxn: accessToken={}", accessToken);
            String url = ispApiUrl + "/txn/get-isp-txn?accessToken=" + accessToken;
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
                IspTxnDto ispTxnDTO = IspTxnMapper.ispTxnToIspTxnDtoForIsp(ispTxn);
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
            String url = ispApiUrl + "/txn/cancel?accessToken=" + accessToken;
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

    public Response<Object> updateAcceptedTerms(String accessToken) {
        Response<Object> response = new Response<>();
        try {
            log.info("Begin updateAcceptedTerms: accessToken={}", accessToken);
            String url = ispApiUrl + "/txn/update-accepted-terms?accessToken=" + accessToken;
            String rsp = restPost(url, null);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End updateAcceptedTerms: accessToken={} - rspData={}", accessToken, rsp);
        } catch (Exception ex) {
            log.error("Exception updateAcceptedTerms: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<Object> updateRecurringInfo(String accessToken, IspTxn ispTxn) {
        Response<Object> response = new Response<>();
        try {
            log.info("Begin updateRecurringInfo: accessToken={} - issuerCode={} - recurringNumberOfIsp={} - recurringFrequency={} - scheme={}",
                    accessToken, ispTxn.getIssuerCode(), ispTxn.getRecurringNumberOfIsp(), ispTxn.getRecurringFrequency(), ispTxn.getScheme());
            String url = ispApiUrl + "/txn/update-recurring-info";
            String rsp = restPost(url, Utils.toJson(ispTxn));
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End updateRecurringInfo: accessToken={} - rspData={}", accessToken, rsp);
        } catch (Exception ex) {
            log.error("Exception updateRecurringInfo: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    protected boolean verifyCardScheme(String scheme, String cardNumber) {
        boolean isMatch = false;
        try {
            isMatch = Pattern.matches(CardConfig.getCardConfigByScheme(scheme).getRegex(), cardNumber);
        } catch (Exception ex) {
            log.error("Exception verifyCardScheme: exception=", ex);
        }
        return isMatch;
    }

    protected Response<IspConfig> verifyBin(String binCode, IspTxn ispTxn) {
        Response<IspConfig> response = new Response<>();
        try {
            String url = UriComponentsBuilder.fromHttpUrl(ispApiUrl + "/txn/bin-checker")
                    .queryParam("tmnCode", ispTxn.getTmnCode())
                    .queryParam("binCode", binCode)
                    .queryParam("issuerCode", ispTxn.getIssuerCode())
                    .queryParam("scheme", ispTxn.getScheme())
                    .queryParam("recurringFrequency", ispTxn.getRecurringFrequency())
                    .queryParam("recurringNumberOfIsp", ispTxn.getRecurringNumberOfIsp())
                    .queryParam("amount", ispTxn.getAmount())
                    .build().toUriString();
            String rsp = restGet(url);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
        } catch (Exception ex) {
            log.error("Exception verifyBin: binCode={}", binCode, ex);
            response.setRspCode(VerifyErrorCode.EXCEPTION.getCode());
            response.setRspMsg(VerifyErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(VerifyErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<VerifyRsp> verifyApi(CardVerify cardVerify, String accessToken, String cardNumber) {
        Response<VerifyRsp> response = new Response<>();
        try {
            log.info("Begin verifyApi: accessToken={} - cardNumber={}", accessToken, cardNumber);

            String url = ispApiUrl + "/txn/verify";
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
                boolean isMatchScheme = verifyCardScheme(ispTxn.getScheme(), cardRequest.getCardNumber());
                if (isMatchScheme) { //Card number match scheme
                    //binCode length may be change in the future
                    String binCode = cardRequest.getCardNumber().substring(0, 10);
                    Response<IspConfig> verifyBinRsp = verifyBin(binCode, ispTxn);
                    if (VerifyErrorCode.SUCCESS.getCode().equals(verifyBinRsp.getRspCode())) { //Bin valid

                        CardVerify cardVerify = new CardVerify();
                        String tranIdKey = String.format(TRAN_ID_KEY, accessToken);
                        String tranIdCached = (String) redisTemplate.opsForValue().get(tranIdKey); // get TranId cached from Redis
                        if (!StringUtils.isEmpty(tranIdCached)) {
                            cardVerify.setTranId(Long.parseLong(tranIdCached));
                        }
                        cardVerify.setBankCode(ispTxn.getScheme());
                        cardVerify.setToken(accessToken);
                        cardVerify.setCardType(ispTxn.getCardType());
                        cardVerify.setCardNumber(cardRequest.getCardNumber());
                        cardVerify.setCardDate(cardRequest.getCardDate());
                        cardVerify.setIpAddr(ipAddr);
                        cardVerify.setCvv(cardRequest.getCvcCvv());

                        CustomerInfo customerInfo = new CustomerInfo();

                        String fullName;
                        String firstName;
                        String lastName;
                        if (StringUtils.isEmpty(ispTxn.getBillToForename()) || StringUtils.isEmpty(ispTxn.getBillToSurname())) { //ispTxn not had
                            fullName = Utils.unAccent(cardRequest.getCardHolder());
                            firstName = fullName.split(" ")[0];
                            lastName = fullName.substring(firstName.length()).trim();
                        } else { //ispTxn had
                            firstName = ispTxn.getBillToSurname();
                            lastName = ispTxn.getBillToForename();
                            fullName = firstName + " " + lastName;
                        }

                        if (StringUtils.isEmpty(ispTxn.getIdentityCode())) { //ispTxn not had
                            customerInfo.setIdentityCode(Utils.unAccent(cardRequest.getIdentityCode()));
                        } else { //ispTxn had
                            customerInfo.setIdentityCode(ispTxn.getIdentityCode());
                        }

                        if (StringUtils.isEmpty(ispTxn.getBillToMobile())) { //ispTxn not had
                            customerInfo.setMobile(cardRequest.getMobile());
                        } else { //ispTxn had
                            customerInfo.setMobile(ispTxn.getBillToMobile());
                        }

                        if (StringUtils.isEmpty(ispTxn.getBillToEmail())) { //ispTxn not had
                            customerInfo.setEmail(cardRequest.getEmail());
                        } else { //ispTxn had
                            customerInfo.setEmail(ispTxn.getBillToEmail());
                        }

                        if (StringUtils.isEmpty(ispTxn.getBillToAddress())) { //ispTxn not had
                            customerInfo.setAddress1(Utils.unAccent(cardRequest.getAddress()));
                        } else { //ispTxn had
                            customerInfo.setAddress1(ispTxn.getBillToAddress());
                        }

                        if (StringUtils.isEmpty(ispTxn.getBillToCity())) { //ispTxn not had
                            customerInfo.setCity(Utils.unAccent(cardRequest.getCity()));
                        } else { //ispTxn had
                            customerInfo.setCity(ispTxn.getBillToCity());
                        }

                        if (StringUtils.isEmpty(ispTxn.getBillToCountry())) { //ispTxn not had
                            customerInfo.setCountry(cardRequest.getCountry());
                        } else { //ispTxn had
                            customerInfo.setCountry(ispTxn.getBillToCountry());
                        }

                        cardVerify.setCardHolder(fullName);

                        customerInfo.setFirstName(firstName);
                        customerInfo.setLastName(lastName);
                        customerInfo.setFullName(fullName);

                        cardVerify.setAddData(Utils.toJson(customerInfo));
                        cardVerify.setUserAgent(userAgent);

                        response = verifyApi(cardVerify, accessToken, cardNumber);
                    } else { //Bin not valid
                        response.setRspCode(verifyBinRsp.getRspCode());
                        response.setRspMsg(verifyBinRsp.getRspMsg());
                        response.setRspAlias(verifyBinRsp.getRspAlias());
                        log.error("Error verifyCard: verifyBin {}, accessToken={} - binCode={} - tmnCode={}",
                                verifyBinRsp.getRspMsg(), accessToken, binCode, ispTxn.getTmnCode());
                    }
                } else { //Card number not match scheme
                    response.setRspCode(VerifyErrorCode.NOT_MATCH_SCHEME.getCode());
                    response.setRspMsg(VerifyErrorCode.NOT_MATCH_SCHEME.getMessage());
                    response.setRspAlias(VerifyErrorCode.NOT_MATCH_SCHEME.getAlias());
                    log.error("Error verifyCard: card number not match any schemes, accessToken={} - cardNumber={} - scheme={}",
                            accessToken, cardNumber, ispTxn.getScheme());
                }
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
