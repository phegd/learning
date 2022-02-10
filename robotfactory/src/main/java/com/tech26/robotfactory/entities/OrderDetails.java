package com.tech26.robotfactory.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Set;

public class OrderDetails {
    @JsonProperty(value = "order_id")
    private String orderId;
    @JsonProperty(value = "total")
    private BigDecimal totalAmount;
    @JsonIgnore
    private Set<String> robotParts;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<String> getRobotParts() {
        return robotParts;
    }

    public void setRobotParts(Set<String> robotParts) {
        this.robotParts = robotParts;
    }
}
