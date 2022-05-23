package de.c24.finacc.klt.services;

import de.c24.finacc.dto.ConversionDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CurrencyDetailsServiceProxy implements CurrencyDetailsService {
    @Autowired
    private CurrencyDetailsService currencyDetailsService;

    @Override
    public ConversionDetailsDTO getAvailableCurrencies() {
        return currencyDetailsService.getAvailableCurrencies();
    }
}
