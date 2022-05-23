package de.c24.finacc.klt.services;

import de.c24.finacc.dto.ConversionDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:properties/configurations.properties")
public class CurrencyConversionServiceImpl implements CurrencyDetailsService, CurrencyConversionService {
    private ConversionDetailsDTO conversionDetails;
    @Value("${currency_conversion_uri}")
    private String currencyConversionURI;
    @Value("${available_currencies_suffix}")
    private String availableCurrenciesSuffix;
    @Value("${conversion_latest_suffix}")
    private String currencyConversionSuffix;
    @Value("${api_access_key}")
    private String currencyConversionAccessKey;

    private Map<String, String> currencyTypesMap;
    private String finalConversionURL;

    @Override
    public ConversionDetailsDTO getAvailableCurrencies() {
        StringBuilder finalConversionURLBuilder = new StringBuilder().append(currencyConversionURI).append(availableCurrenciesSuffix).append("?access_key=").append(currencyConversionAccessKey);
        conversionDetails = new RestTemplate().getForObject(finalConversionURLBuilder.toString(), ConversionDetailsDTO.class);
        if (conversionDetails != null && conversionDetails.getSuccess()) {
            currencyTypesMap = conversionDetails.getSymbols();
            String availableCurrencies = currencyTypesMap.keySet().toString().replaceAll("\\[|\\]", "");
            finalConversionURLBuilder = finalConversionURLBuilder.append("&symbols=" + availableCurrencies);
            conversionDetails.setCurrencies(currencyTypesMap.entrySet().stream().map(entry -> String.join(" - ", entry.getKey(), entry.getValue())).collect(Collectors.toSet()));
        }
        finalConversionURL = finalConversionURLBuilder.toString().replaceAll(availableCurrenciesSuffix, currencyConversionSuffix);
        return conversionDetails;
    }

    @Override
    public ConversionDetailsDTO convertCurrency(String baseCurrency) {
        if(finalConversionURL == null || currencyTypesMap == null) {
            this.getAvailableCurrencies();
        }
        StringBuilder finalConversionURLBuilder = new StringBuilder().append(finalConversionURL).append("&base=").append(baseCurrency);
        return new RestTemplate().getForObject(finalConversionURLBuilder.toString(), ConversionDetailsDTO.class);
    }
}
