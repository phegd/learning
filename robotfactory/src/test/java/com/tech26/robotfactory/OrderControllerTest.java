package com.tech26.robotfactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech26.robotfactory.entities.OrderDetails;
import com.tech26.robotfactory.entities.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RobotfactoryApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class OrderControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    public void testCreateOrderSuccessful() throws Exception {
        String uri = "/orders";
        OrderRequest orderRequest = new OrderRequest();
        ObjectMapper mapper = new ObjectMapper();
        orderRequest.setComponents(Arrays.asList(new String[]{"I","A","D","F"}));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(orderRequest))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        OrderDetails orderDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), OrderDetails.class);
        assertEquals("ADFI1", orderDetails.getOrderId());
        assertEquals(new BigDecimal(160.11).setScale(2, BigDecimal.ROUND_HALF_EVEN), orderDetails.getTotalAmount());
    }

    @Test
    public void testInvalidOrder1() throws Exception {
        String uri = "/orders";
        OrderRequest orderRequest = new OrderRequest();
        ObjectMapper mapper = new ObjectMapper();
        orderRequest.setComponents(Arrays.asList(new String[]{"I","C","D","F"}));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(orderRequest))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void testInvalidOrder2() throws Exception {
        String uri = "/orders";
        OrderRequest orderRequest = new OrderRequest();
        ObjectMapper mapper = new ObjectMapper();
        orderRequest.setComponents(Arrays.asList(new String[]{"G","A","D","F"}));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(orderRequest))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void testInvalidOrder3() throws Exception {
        String uri = "/orders";
        OrderRequest orderRequest = new OrderRequest();
        ObjectMapper mapper = new ObjectMapper();
        orderRequest.setComponents(Arrays.asList(new String[]{"G","J","D","F"}));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(orderRequest))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
}
