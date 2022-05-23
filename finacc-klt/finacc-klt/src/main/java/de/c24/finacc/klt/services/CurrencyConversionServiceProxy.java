package de.c24.finacc.klt.services;

import de.c24.finacc.dto.ConversionDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CurrencyConversionServiceProxy implements CurrencyConversionService {
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Override
    public ConversionDetailsDTO convertCurrency(String baseCurrency) {
        return currencyConversionService.convertCurrency(baseCurrency);
    }
}
