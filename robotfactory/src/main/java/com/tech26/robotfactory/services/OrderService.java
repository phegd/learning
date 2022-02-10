package com.tech26.robotfactory.services;

import com.tech26.robotfactory.entities.OrderDetails;
import com.tech26.robotfactory.entities.RobotPart;
import com.tech26.robotfactory.entities.RobotPartTypes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {
    private Map<String, RobotPart> robotPartsStockMap = new HashMap<String, RobotPart>() {{
        put("A", new RobotPart("A", new BigDecimal(10.28), "Humanoid Face", 9));
        put("B", new RobotPart("B", new BigDecimal(24.07), "LCD Face", 7));
        put("C", new RobotPart("C", new BigDecimal(13.30), "Steampunk Face", 0));
        put("D", new RobotPart("D", new BigDecimal(28.94), "Arms with Hands", 1));
        put("E", new RobotPart("E", new BigDecimal(12.39), "Arms with Grippers", 3));
        put("F", new RobotPart("F", new BigDecimal(30.77), "Mobility with Wheels", 2));
        put("G", new RobotPart("G", new BigDecimal(55.13), "Mobility with Legs", 15));
        put("H", new RobotPart("H", new BigDecimal(50.00), "Mobility with Tracks", 7));
        put("I", new RobotPart("I", new BigDecimal(90.12), "Material Bioplastic", 92));
        put("J", new RobotPart("J", new BigDecimal(82.31), "Material Metallic", 15));
    }};
    private Map<String, OrderDetails> placedOrdersMap = new HashMap<String, OrderDetails>();

    public Boolean isValidOrder(Set<String> uniqueRobotParts) {
        try {
            Set<String> orderCategories = new HashSet<>();
            if (uniqueRobotParts == null || uniqueRobotParts.size() != 4) {
                return Boolean.FALSE;
            }
            uniqueRobotParts.stream().forEach(code -> {
                if (robotPartsStockMap.get(code).getAvailableStock() != 0) {
                    if (!orderCategories.contains(RobotPartTypes.valueOf(code).getCategory())) {
                        orderCategories.add(RobotPartTypes.valueOf(code).getCategory());
                    }
                }
            });
            return (orderCategories.size() == 4);
        } catch (Exception ex) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    public OrderDetails placeOrder(Set<String> robotParts) {
        try {
            OrderDetails currentOrder = new OrderDetails();
            currentOrder.setTotalAmount(BigDecimal.ZERO);
            StringBuilder orderIdBuilder = new StringBuilder();
            robotParts.stream().forEach(code -> {
                this.addToCart(code, orderIdBuilder, currentOrder);
            });
            orderIdBuilder.append(placedOrdersMap.size() + 1);
            currentOrder.setOrderId(orderIdBuilder.toString());
            currentOrder.setRobotParts(robotParts);
            placedOrdersMap.put(currentOrder.getOrderId(), currentOrder);
            return currentOrder;
        } catch (Exception ex) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public OrderDetails findOrder(String orderId) {
        return this.placedOrdersMap.get(orderId);
    }

    private void addToCart(String code, StringBuilder orderIdBuilder, OrderDetails currentOrder) {
        orderIdBuilder.append(code);
        currentOrder.setTotalAmount(currentOrder.getTotalAmount().add(robotPartsStockMap.get(code).getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN)));
        Integer stock = robotPartsStockMap.get(code).getAvailableStock();
        robotPartsStockMap.get(code).setAvailableStock(--stock);
    }
}
