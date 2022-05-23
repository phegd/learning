package com.scalable.ecb.exchange.services;

import com.scalable.ecb.exchange.dto.ConversionDetailsDTO;

import java.math.BigDecimal;

public interface CurrencyExchangeService {
    ConversionDetailsDTO getAllCurrencyExchangeRates() throws Exception;
    ConversionDetailsDTO convertCurrency(BigDecimal baseAmount, String baseCurrency, String targetCurrency);
}
