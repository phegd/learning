package de.c24.finacc.klt.services;

import de.c24.finacc.dto.ConversionDetailsDTO;

public interface CurrencyConversionService {
    ConversionDetailsDTO convertCurrency(String baseCurrency);
}
