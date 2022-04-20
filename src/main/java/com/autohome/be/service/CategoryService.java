package com.autohome.be.service;

import com.autohome.be.common.Utils;
import com.autohome.be.dto.response.Response;
import com.autohome.be.enums.ErrorCode;
import com.autohome.be.enums.LangConfig;
import com.autohome.be.model.Issuer;
import com.autohome.be.model.LangPhrase;
import com.autohome.be.model.RecurringInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService extends BaseService {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    public List<LangPhrase> getPhrases(String module) {
        List<LangPhrase> langPhrases = null;
        try {
            log.info("Begin getPhrases: module={}", module);
            String url = ispApiUrl + "/category/get-phrases?module=" + module;
            String rsp = restGet(url);
            Response<List<LangPhrase>> response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            langPhrases = response.getData();
            log.info("End getPhrases: module={} - rspData={}", module, rsp);
        } catch (Exception ex) {
            log.error("Exception getPhrases: module={}", module, ex);
        }
        return langPhrases;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getTranslations(String lang) {
        Map<String, String> translations = null;
        try {
            String key = "ISP_PHRASES";
            lang = lang.split("-")[0]; // prevent case vi-VN, en-US,...
            int langId = LangConfig.getLangConfigByType(lang).getId();
            translations = (Map<String, String>) redisTemplate.opsForHash().get(key, langId);
            if (translations == null) {
                String status = (String) redisTemplate.opsForHash().get(key, "ISP_PHRASES_STATUS");
                if (!"UPDATED".equals(status)) {
                    List<LangPhrase> langPhrases = getPhrases("ISP");
                    if (langPhrases != null) {
                        Map<Integer, Map<String, String>> translationsByLang = langPhrases.stream()
                                .collect(Collectors.groupingBy(LangPhrase::getLangId, Collectors.toMap(LangPhrase::getTitle, LangPhrase::getText)));
                        for (Map.Entry<Integer, Map<String, String>> entry : translationsByLang.entrySet()) {
                            redisTemplate.opsForHash().put(key, entry.getKey(), entry.getValue());
                        }
                        redisTemplate.opsForHash().put(key, "ISP_PHRASES_STATUS", "UPDATED");
                        redisTemplate.expire(key, 24, TimeUnit.HOURS);

                        translations = translationsByLang.get(langId);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Exception getTranslations: lang={}", lang, ex);
        }

        if (translations == null) {
            translations = new HashMap<>();
        }
        return translations;
    }

    public String getErrorText(String label, String lang) {
        try {
            String key = "ISP_ERRMSG_PHRASES";
            lang = lang.split("-")[0]; // prevent case vi-VN, en-US,...
            String hashKey = String.format("%s_%s", LangConfig.getLangConfigByType(lang).getId(), label);
            String text = (String) redisTemplate.opsForHash().get(key, hashKey);
            if (text == null) {
                String status = (String) redisTemplate.opsForHash().get(key, "ISP_ERRMSG_PHRASES_STATUS");
                if (!"UPDATED".equals(status)) {
                    List<LangPhrase> langPhrases = getPhrases("ERRMSG");
                    if (langPhrases != null) {
                        for (LangPhrase lp : langPhrases) {
                            String hashKeyLp = String.format("%s_%s", lp.getLangId(), lp.getTitle());
                            if (hashKeyLp.equals(hashKey)) {
                                text = lp.getText();
                            }
                            redisTemplate.opsForHash().put(key, hashKeyLp, lp.getText());
                        }
                        redisTemplate.opsForHash().put(key, "ISP_ERRMSG_PHRASES_STATUS", "UPDATED");
                        redisTemplate.expire(key, 24, TimeUnit.HOURS);
                    }
                }
            }
            return text;
        } catch (Exception ex) {
            log.error("Exception getErrorText: label={}, lang={} - exception", label, lang, ex);
        }
        return "";
    }

    public Response<List<Issuer>> getIssuers(String accessToken) {
        Response<List<Issuer>> response = new Response<>();
        try {
            log.info("Begin getIssuers: accessToken={}", accessToken);
            String url = ispApiUrl + "/category/get-issuers?accessToken=" + accessToken;
            String rsp = restGet(url);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End getIssuers: accessToken={} - rspData={}", accessToken, rsp);
        } catch (Exception ex) {
            log.error("Exception getIssuers: accessToken={}", accessToken, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<List<String>> getSchemes(String accessToken, String issuerCode) {
        Response<List<String>> response = new Response<>();
        try {
            log.info("Begin getSchemes: accessToken={} - issuerCode={}", accessToken, issuerCode);
            String url = ispApiUrl + "/category/get-schemes?issuerCode=" + issuerCode + "&accessToken=" + accessToken;
            String rsp = restGet(url);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            log.info("End getSchemes: accessToken={} -issuerCode={} - rspData={}", accessToken, issuerCode, rsp);
        } catch (Exception ex) {
            log.error("Exception getSchemes: issuerCode={}", issuerCode, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }

    public Response<List<RecurringInfo>> getRecurringInfo(String accessToken, String issuerCode, String scheme) {
        Response<List<RecurringInfo>> response = new Response<>();
        try {
            log.info("Begin getRecurringInfo: accessToken={} - issuerCode={} - scheme={}", accessToken, issuerCode, scheme);
            String url = ispApiUrl + "/category/get-recurring-info?issuerCode=" + issuerCode + "&scheme=" + scheme + "&accessToken=" + accessToken;
            String rsp = restGet(url);
            response = Utils.fromJson(rsp, new TypeReference<>() {
            });
            if (ErrorCode.SUCCESS.getCode().equals(response.getRspCode())
                    && response.getData() != null
                    && !response.getData().isEmpty()) {
                response.getData().forEach(recurringInfo -> {
                    recurringInfo.setAmount(recurringInfo.getAmount() / 100);
                    recurringInfo.setRecurringAmount(recurringInfo.getRecurringAmount() / 100);
                    recurringInfo.setTotalIspAmount(recurringInfo.getTotalIspAmount() / 100);
                });
            }
            log.info("End getRecurringInfo: accessToken={} - issuerCode={} - scheme={} - rspData={}", accessToken, issuerCode, scheme, rsp);
        } catch (Exception ex) {
            log.error("Exception getRecurringInfo: accessToken={} - issuerCode={} - scheme={}", accessToken, issuerCode, scheme, ex);
            response.setRspCode(ErrorCode.EXCEPTION.getCode());
            response.setRspMsg(ErrorCode.EXCEPTION.getMessage());
            response.setRspAlias(ErrorCode.EXCEPTION.getAlias());
        }
        return response;
    }
}
