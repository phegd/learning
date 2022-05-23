package com.scalable.ecb.exchange.rest;

import com.scalable.ecb.exchange.dto.ConversionDetailsDTO;
import com.scalable.ecb.exchange.services.CurrencyExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "ecb/exchange")
class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger("CurrencyExchangeService");

    @GetMapping(path = "/supported-currencies", produces = "application/json")
    public ResponseEntity<ConversionDetailsDTO> getAllAvailableCurrenciesForExchange() {
        try {
            ConversionDetailsDTO conversionDetails = currencyExchangeService.getAllCurrencyExchangeRates();
            if (conversionDetails != null) {
                return new ResponseEntity<>(conversionDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR in getAllAvailableCurrenciesForExchange(): ", ex);
            return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/reference/{baseCurrency}/{targetCurrency}", produces = "application/json")
    public ResponseEntity<ConversionDetailsDTO> findReferenceExchangeRate(@PathVariable String baseCurrency, @PathVariable String targetCurrency) {
        try {
            ConversionDetailsDTO conversionDetails = currencyExchangeService.convertCurrency(BigDecimal.ONE, baseCurrency, targetCurrency);
            if (conversionDetails != null) {
                return new ResponseEntity<>(conversionDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR in findReferenceExchangeRate(): ", ex);
            return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{baseCurrency}/{exchangeAmount}/{targetCurrency}", produces = "application/json")
    public ResponseEntity<ConversionDetailsDTO> convertOnExchangeRate(@PathVariable String baseCurrency, @PathVariable Long exchangeAmount, @PathVariable String targetCurrency) {
        try{
            ConversionDetailsDTO conversionDetails = currencyExchangeService.convertCurrency(BigDecimal.valueOf(exchangeAmount), baseCurrency, targetCurrency);
            if (conversionDetails != null) {
                return new ResponseEntity<>(conversionDetails, HttpStatus.OK);
            } else {
                return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            LOGGER.error("ERROR in convertOnExchangeRate(): ", ex);
            return new ResponseEntity<>((ConversionDetailsDTO) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}