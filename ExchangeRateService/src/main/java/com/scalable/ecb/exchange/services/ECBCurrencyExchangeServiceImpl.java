package com.scalable.ecb.exchange.services;

import com.scalable.ecb.exchange.dto.ConversionDetailsBuilder;
import com.scalable.ecb.exchange.dto.ConversionDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Primary
@Service("ecbCurrencyExchangeServiceImpl")
@PropertySource("classpath:properties/configurations.properties")
public class ECBCurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private String exchangeReferenceDate;
    private static final String EUR_CURRENCY = "EUR";
    private static final String ECB_INFO_PROVIDER = "ECB";
    private Map<String, BigDecimal> exchangeRatesMap;
    private Map<String, Integer> conversionHistoryMap;

    public Map<String, Integer> getConversionHistoryMap() {
        return conversionHistoryMap;
    }

    @Value("${ecb_exchange_rates_url}")
    private String currencyExchangeURL;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger("CurrencyExchangeService");

    @Override
    public ConversionDetailsDTO getAllCurrencyExchangeRates() throws Exception {
        URL ecbExchangeRatesURL = new URL(currencyExchangeURL);
        try {
            // Instantiate the Factory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(ecbExchangeRatesURL.openStream());
            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("Cube");
            if (nodeList.getLength() > 0) {
                exchangeRatesMap = new ConcurrentHashMap<>();
                for (int counter = 0; counter < nodeList.getLength(); counter++) {
                    Node currencyNode = nodeList.item(counter);
                    if (currencyNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                        Element currencyElement = (Element) currencyNode;
                        String currencyCode = currencyElement.getAttribute("currency");
                        if(currencyCode != null && !currencyCode.isEmpty()) {
                            BigDecimal exchangeRateValue = new BigDecimal(currencyElement.getAttribute("rate"));
                            exchangeRatesMap.put(currencyCode, exchangeRateValue);
                        } else {
                            exchangeReferenceDate = currencyElement.getAttribute("time");
                        }
                    }
                }
            }
            LOGGER.debug("Exchange Rates Information ", exchangeRatesMap);
        } catch (Exception ex) {
            LOGGER.error("ERROR in getAllCurrencyExchangeRates(): ", ex);
        }
        return new ConversionDetailsBuilder()
                .withSuccess(Boolean.TRUE)
                .withProvider(ECB_INFO_PROVIDER)
                .withCurrencySet(exchangeRatesMap.keySet())
                .withConversionHistoryMap(this.conversionHistoryMap).build();
    }

    @Override
    public ConversionDetailsDTO convertCurrency(BigDecimal baseAmount, String baseCurrency, String targetCurrency) {
        BigDecimal convertedAmount;
        ConversionDetailsBuilder conversionBuilder = new ConversionDetailsBuilder();
        try {
            this.updateConversionHistoryMap(baseCurrency, targetCurrency);
            this.getAllCurrencyExchangeRates();
            if (EUR_CURRENCY.equals(baseCurrency)) {
                convertedAmount = baseAmount.multiply(exchangeRatesMap.get(targetCurrency));
            } else if (EUR_CURRENCY.equals(targetCurrency)) {
                convertedAmount = baseAmount.multiply(BigDecimal.ONE.divide(exchangeRatesMap.get(baseCurrency), 4, RoundingMode.HALF_UP));
            } else {
                BigDecimal baseXchangeRateWithEuro = exchangeRatesMap.get(baseCurrency);
                BigDecimal targetXchangeRateWithEuro = exchangeRatesMap.get(targetCurrency);
                convertedAmount = baseAmount.multiply(targetXchangeRateWithEuro.divide(baseXchangeRateWithEuro, 4, RoundingMode.HALF_UP));
            }
            return conversionBuilder.withSuccess(Boolean.TRUE)
                    .withProvider(ECB_INFO_PROVIDER)
                    .withReferenceDate(exchangeReferenceDate)
                    .withBaseAmount(baseAmount)
                    .withBaseCurrency(baseCurrency)
                    .withTargetCurrency(targetCurrency)
                    .withTargetAmount(convertedAmount).build();
        } catch (Exception ex) {
            LOGGER.error("ERROR in getAllCurrencyExchangeRates(): ", ex);
            return conversionBuilder.withSuccess(Boolean.FALSE).withProvider(ECB_INFO_PROVIDER).withErrorMessage("Unsupported currency for conversion.").build();
        }
    }

    private Map<String, Integer> updateConversionHistoryMap(String baseCurrency, String targetCurrency) {
        String mapKey = baseCurrency + "-" + targetCurrency;
        if(conversionHistoryMap  == null) {
            conversionHistoryMap = new ConcurrentHashMap<>();
        }
        Integer conversionCount = conversionHistoryMap.containsKey(mapKey) ? conversionHistoryMap.get(mapKey) : 0;
        conversionHistoryMap.put(mapKey, ++conversionCount);
        return conversionHistoryMap;
    }
}
