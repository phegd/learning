package de.c24.finacc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

public class ConversionDetailsDTO {
    @JsonProperty(value = "timestamp")
    private Long timestamp;
    @JsonProperty(value = "success", required = true)
    private Boolean success;
    @JsonProperty(value = "base")
    private String base;
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "symbols")
    private Map<String, String> symbols;
    @JsonProperty(value = "currencies")
    private Set<String> currencies;
    @JsonProperty(value = "rates")
    private Map<String, Double> rates;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }

    public Set<String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Set<String> currencies) {
        this.currencies = currencies;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
