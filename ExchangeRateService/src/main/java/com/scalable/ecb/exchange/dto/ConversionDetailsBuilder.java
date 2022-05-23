package com.scalable.ecb.exchange.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public class ConversionDetailsBuilder {
    private ConversionDetailsDTO conversionDetailsDTO;

    public ConversionDetailsBuilder() {
        this.conversionDetailsDTO = new ConversionDetailsDTO();
    }

    public ConversionDetailsBuilder(ConversionDetailsDTO conversionDetailsDTO) {
        this.conversionDetailsDTO = conversionDetailsDTO;
    }

    public ConversionDetailsBuilder withSuccess(Boolean success) {
        conversionDetailsDTO.setSuccess(success);
        return this;
    }

    public ConversionDetailsBuilder withReferenceDate(String referenceDate) {
        conversionDetailsDTO.setReferenceDate(referenceDate);
        return this;
    }

    public ConversionDetailsBuilder withProvider(String provider) {
        conversionDetailsDTO.setProvider(provider);
        return this;
    }

    public ConversionDetailsBuilder withBaseCurrency(String baseCurrency) {
        conversionDetailsDTO.setBaseCurrency(baseCurrency);
        return this;
    }

    public ConversionDetailsBuilder withBaseAmount(BigDecimal baseAmount) {
        conversionDetailsDTO.setBaseAmount(baseAmount);
        return this;
    }

    public ConversionDetailsBuilder withTargetCurrency(String targetCurrency) {
        conversionDetailsDTO.setTargetCurrency(targetCurrency);
        return this;
    }

    public ConversionDetailsBuilder withTargetAmount(BigDecimal targetAmount) {
        conversionDetailsDTO.setTargetAmount(targetAmount);
        return this;
    }

    public ConversionDetailsBuilder withCurrencySet(Set<String> currencySet) {
        conversionDetailsDTO.setCurrencySet(currencySet);
        return this;
    }

    public ConversionDetailsBuilder withConversionHistoryMap(Map<String, Integer> conversionHistoryMap) {
        conversionDetailsDTO.setConversionHistoryMap(conversionHistoryMap);
        return this;
    }

    public ConversionDetailsBuilder withErrorMessage(String errorMessage) {
        conversionDetailsDTO.setErrorMessage(errorMessage);
        return this;
    }

    public ConversionDetailsDTO build() {
        return conversionDetailsDTO;
    }
}