package com.scalable.ecb.exchange.services;

import com.scalable.ecb.exchange.dto.ConversionDetailsDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("dummyCurrencyExchangeServiceImpl")
public class DummyCurrencyExchangeServiceImpl implements CurrencyExchangeService {

    @Override
    public ConversionDetailsDTO getAllCurrencyExchangeRates() {
        System.out.println("Placeholder: Yet to be implemented");
        return null;
    }

    @Override
    public ConversionDetailsDTO convertCurrency(BigDecimal baseAmount, String baseCurrency, String targetCurrency) {
        System.out.println("Placeholder: Yet to be implemented");
        return null;
    }
}
