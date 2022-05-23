package de.c24.finacc.klt.rest;

import de.c24.finacc.dto.ConversionDetailsDTO;
import de.c24.finacc.klt.services.CurrencyConversionService;
import de.c24.finacc.klt.services.CurrencyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@RestController
@RequestMapping(value = "conversion")
class CurrencyConversionController {

    @Autowired
    private CurrencyDetailsService currencyDetailsService;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping(path = "/currency/symbols", produces = "application/json")
    public ResponseEntity<ConversionDetailsDTO> findEmployee() throws HttpStatusCodeException {
        ConversionDetailsDTO conversionDetails = currencyDetailsService.getAvailableCurrencies();
        if (conversionDetails != null) {
            return new ResponseEntity<ConversionDetailsDTO>(conversionDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<ConversionDetailsDTO>((ConversionDetailsDTO) null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(path = "/currency/{baseCurrency}", produces = "application/json")
    public ResponseEntity<ConversionDetailsDTO> findEmployee(@PathVariable String baseCurrency) throws HttpStatusCodeException {
        ConversionDetailsDTO conversionDetails = currencyConversionService.convertCurrency(baseCurrency);
        if (conversionDetails != null) {
            return new ResponseEntity<ConversionDetailsDTO>(conversionDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<ConversionDetailsDTO>((ConversionDetailsDTO) null, HttpStatus.NOT_FOUND);
        }
    }
}