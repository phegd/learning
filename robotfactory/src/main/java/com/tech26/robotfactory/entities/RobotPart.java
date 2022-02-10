package com.tech26.robotfactory.entities;

import java.math.BigDecimal;

public class RobotPart {
    private String code;
    private BigDecimal price;
    private String partDescription;
    private Integer availableStock;

    public RobotPart(String code, BigDecimal price, String partDescription, Integer availableStock) {
        this.code = code;
        this.price = price;
        this.partDescription = partDescription;
        this.availableStock = availableStock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }
}
