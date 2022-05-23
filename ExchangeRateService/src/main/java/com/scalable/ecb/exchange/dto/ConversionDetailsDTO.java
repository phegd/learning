package com.scalable.ecb.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class ConversionDetailsDTO {
    @JsonProperty(value = "success", required = true)
    private Boolean success;
    @JsonProperty(value = "referenceDate")
    private String referenceDate;
    @JsonProperty(value = "provider")
    private String provider;
    @JsonProperty(value = "baseCurrency")
    private String baseCurrency;
    @JsonProperty(value = "baseAmount")
    private BigDecimal baseAmount;
    @JsonProperty(value = "targetCurrency")
    private String targetCurrency;
    @JsonProperty(value = "targetAmount")
    private BigDecimal targetAmount;
    @JsonProperty(value = "supportedCurrencies")
    private Set<String> currencySet;
    @JsonProperty(value = "conversionHistory")
    private Map<String, Integer> conversionHistoryMap;
    @JsonProperty(value = "errorMessage")
    private String errorMessage;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public BigDecimal getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Set<String> getCurrencySet() {
        return currencySet;
    }

    public void setCurrencySet(Set<String> currencySet) {
        this.currencySet = currencySet;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Integer> getConversionHistoryMap() {
        return conversionHistoryMap;
    }

    public void setConversionHistoryMap(Map<String, Integer> conversionHistoryMap) {
        this.conversionHistoryMap = conversionHistoryMap;
    }
}
