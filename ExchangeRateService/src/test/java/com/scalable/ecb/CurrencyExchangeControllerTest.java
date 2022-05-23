package com.scalable.ecb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.ecb.exchange.ECBExchangeApplication;
import com.scalable.ecb.exchange.dto.ConversionDetailsDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ECBExchangeApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class CurrencyExchangeControllerTest {
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
    public void testSupportedCurrenciesSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/supported-currencies")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getCurrencySet());
        assertEquals(31, conversionDetailsDTO.getCurrencySet().size());
    }

    @Test
    public void testSupportedCurrenciesFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/reference/supported-currencies")).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testReferenceRatesForEuroSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/reference/EUR/INR")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getTargetAmount());
        assertTrue(conversionDetailsDTO.getTargetAmount().compareTo(BigDecimal.ZERO) > -1);
        //assertEquals(new BigDecimal(82.1617).setScale(4, RoundingMode.HALF_UP), conversionDetailsDTO.getTargetAmount());
    }

    @Test
    public void testReferenceRatesToEuroSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/reference/PLN/EUR")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getTargetAmount());
        assertTrue(conversionDetailsDTO.getTargetAmount().compareTo(BigDecimal.ZERO) > -1);
        //assertEquals(new BigDecimal(0.2157).setScale(4, RoundingMode.HALF_UP), conversionDetailsDTO.getTargetAmount());
    }

    @Test
    public void testReferenceRatesFromEuroToUnsupportedFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/reference/EUR/XYZ")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.FALSE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertEquals("Unsupported currency for conversion.", conversionDetailsDTO.getErrorMessage());
    }

    @Test
    public void testConversionFromEuroToRupeeSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/EUR/100/INR")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getTargetAmount());
        assertTrue(conversionDetailsDTO.getTargetAmount().compareTo(BigDecimal.ZERO) > -1);
        //assertEquals(new BigDecimal(8216.1700).setScale(4, RoundingMode.HALF_UP), conversionDetailsDTO.getTargetAmount());
    }

    @Test
    public void testConversionFromUSDollarToEuroSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/USD/10/EUR")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getTargetAmount());
        assertTrue(conversionDetailsDTO.getTargetAmount().compareTo(BigDecimal.ZERO) > -1);
        //assertEquals(new BigDecimal(9.4540).setScale(4, RoundingMode.HALF_UP), conversionDetailsDTO.getTargetAmount());
    }

    @Test
    public void testConversionFromBritishPoundToRupeeSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/GBP/5/INR")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.TRUE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertNotNull(conversionDetailsDTO.getTargetAmount());
        assertTrue(conversionDetailsDTO.getTargetAmount().compareTo(BigDecimal.ZERO) > -1);
        //assertEquals(new BigDecimal(484.3300).setScale(4, RoundingMode.HALF_UP), conversionDetailsDTO.getTargetAmount());
    }

    @Test
    public void testConversionFromUnsupportedtOBritishPoundFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/ecb/exchange/XYZ/10/GBP")).andReturn();
        ConversionDetailsDTO conversionDetailsDTO = this.mapFromJson(mvcResult.getResponse().getContentAsString(), ConversionDetailsDTO.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(conversionDetailsDTO);
        assertEquals(Boolean.FALSE, conversionDetailsDTO.getSuccess());
        assertEquals("ECB", conversionDetailsDTO.getProvider());
        assertEquals("Unsupported currency for conversion.", conversionDetailsDTO.getErrorMessage());
    }
}
