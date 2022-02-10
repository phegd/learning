package com.workmotion.employees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workmotion.employees.entities.EmployeeDetails;
import com.workmotion.employees.entities.EmployeeStates;
import org.junit.Before;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmployeeServicesApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class EmployeeControllerTest {
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

    @Before
    public void initializeEmployee() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/employees/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(this.buildEmployeeDetails("Prasad Hegde",36,"+919986514344","prasad.tumbemane@gmail.com")))).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.put("/employees/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(this.buildEmployeeDetails("Roger Federer",40,"+41185287555","roger.federer@gmail.com")))).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.put("/employees/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(this.buildEmployeeDetails("Novak Djokovic",34,"+38132587658","novak.djokovic@gmail.com")))).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.put("/employees/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.mapToJson(this.buildEmployeeDetails("Rafal Nadal",40,"+34285785250","rafal.nadal@gmail.com")))).andReturn();
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testConfirmCreateEmployeeSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10001")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertNotNull(empDetails);
        assertEquals(10001, empDetails.getEmpId().intValue());
        assertEquals("Prasad Hegde", empDetails.getEmpFullName());
        assertEquals(36, empDetails.getEmpAge().intValue());
        assertEquals("+919986514344", empDetails.getEmpPhone());
        assertEquals("prasad.tumbemane@gmail.com", empDetails.getEmpEmailId());
    }

    @Test
    public void testUpdateInCheckSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10001/IN_CHECK")
                                .contentType(MediaType.APPLICATION_JSON_VALUE).content("")).andReturn();
        Integer employeeId = this.mapFromJson(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(10001, employeeId.intValue());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10001")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.IN_CHECK, empDetails.getEmpStatus().getEmpStatus());
        assertEquals(EmployeeStates.SubStates.SECURITY_CHECK_STARTED, empDetails.getEmpStatus().getEmpSecurityStatus());
        assertEquals(EmployeeStates.SubStates.WORK_PERMIT_CHECK_STARTED, empDetails.getEmpStatus().getEmpWorkPermitStatus());
    }

    @Test
    public void testUpdateSecurityStateSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10002/IN_CHECK/SECURITY_CHECK_FINISHED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();
        Integer employeeId = this.mapFromJson(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(10002, employeeId.intValue());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10002")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.IN_CHECK, empDetails.getEmpStatus().getEmpStatus());
        assertEquals(EmployeeStates.SubStates.SECURITY_CHECK_FINISHED, empDetails.getEmpStatus().getEmpSecurityStatus());
        assertEquals(EmployeeStates.SubStates.WORK_PERMIT_CHECK_STARTED, empDetails.getEmpStatus().getEmpWorkPermitStatus());
    }

    @Test
    public void testUpdateWorkPermitStateSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10003/IN_CHECK/WORK_PERMIT_CHECK_FINISHED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();
        Integer employeeId = this.mapFromJson(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(10003, employeeId.intValue());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10003")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.IN_CHECK, empDetails.getEmpStatus().getEmpStatus());
        assertEquals(EmployeeStates.SubStates.SECURITY_CHECK_STARTED, empDetails.getEmpStatus().getEmpSecurityStatus());
        assertEquals(EmployeeStates.SubStates.WORK_PERMIT_CHECK_FINISHED, empDetails.getEmpStatus().getEmpWorkPermitStatus());
    }

    @Test
    public void testAutoTransitionApprovalSuccess() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10004/IN_CHECK/SECURITY_CHECK_FINISHED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10004/IN_CHECK/WORK_PERMIT_CHECK_FINISHED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10004")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.APPROVED, empDetails.getEmpStatus().getEmpStatus());
        assertEquals(EmployeeStates.SubStates.SECURITY_CHECK_FINISHED, empDetails.getEmpStatus().getEmpSecurityStatus());
        assertEquals(EmployeeStates.SubStates.WORK_PERMIT_CHECK_FINISHED, empDetails.getEmpStatus().getEmpWorkPermitStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10004/ACTIVE")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();
        Integer employeeId = this.mapFromJson(mvcResult.getResponse().getContentAsString(), Integer.class);

        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(10004, employeeId.intValue());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10004")).andReturn();
        empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.ACTIVE, empDetails.getEmpStatus().getEmpStatus());
        assertEquals(EmployeeStates.SubStates.SECURITY_CHECK_FINISHED, empDetails.getEmpStatus().getEmpSecurityStatus());
        assertEquals(EmployeeStates.SubStates.WORK_PERMIT_CHECK_FINISHED, empDetails.getEmpStatus().getEmpWorkPermitStatus());
    }

    @Test
    public void testUpdateManualAdditionFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10001/ADDED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10001")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.ADDED, empDetails.getEmpStatus().getEmpStatus());
    }

    @Test
    public void testUpdateManualApprovalFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10001/APPROVED")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10001")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertNotNull(empDetails.getEmpStatus().getEmpStatus());
    }

    @Test
    public void testUpdateEarlyActivationFailure() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/employees/update/10001/ACTIVE")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.mapToJson(""))).andReturn();

        Integer employeeId = this.mapFromJson(mvcResult.getResponse().getContentAsString(), Integer.class);
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(-1, employeeId.intValue());

        mvcResult = mvc.perform(MockMvcRequestBuilders.get("/employees/search/10001")).andReturn();
        EmployeeDetails empDetails = this.mapFromJson(mvcResult.getResponse().getContentAsString(), EmployeeDetails.class);
        assertEquals(EmployeeStates.ADDED, empDetails.getEmpStatus().getEmpStatus());
    }

    private EmployeeDetails buildEmployeeDetails(String empFullName, Integer empAge, String empPhone, String empEmailId) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmpFullName(empFullName);
        employeeDetails.setEmpAge(empAge.byteValue());
        employeeDetails.setEmpPhone(empPhone);
        employeeDetails.setEmpEmailId(empEmailId);
        return employeeDetails;
    }
}
