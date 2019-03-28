package springData.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import dataBase.entity.Office;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import springData.config.MainMvcConfig;
import springData.dto.ErrorMessage;

import javax.sql.DataSource;
import javax.ws.rs.core.Response.Status;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainMvcConfig.class)
@WebAppConfiguration
@TestPropertySource("classpath:springData/test.properties")
public class OfficeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();


    @Before
    public void setup() {
        Resource initSchema = new ClassPathResource("springData\\script\\schema.sql");
        Resource data = new ClassPathResource("springData\\script\\data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testFindByTargetBetween() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/office").param("min", "300").param("max", "600")).andDo(print())
                .andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        List<Office> office = getListOfficeFromResult(mvcResult);
        assertEquals(0, office.size());
    }

    @Test
    public void testGetOfficeByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/office/{id}", "111111")).andDo(print()).andReturn();

        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        Office office = mapper.readValue(mvcResult.getResponse().getContentAsString(), Office.class);
        assertNotNull(office);
    }

    @Test
    public void testGetOfficeByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/office/{id}", "8841")).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().length() == 0);
    }

    @Test
    public void testUpdateOfficeByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/office/{id}", "111111").param("city", "Gotham")).andDo(print()).andReturn();
        assertNotEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateOfficeByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/office/{id}", "8841").param("city", "Noir")).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        ErrorMessage errorMessage = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
        assertNotEquals("Could not update Office id=8841, because it doesn't exist.", errorMessage.getMessage());
    }

    @Test
    public void testDeleteOfficeByIdExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/office/{id}", "111111")).andDo(print()).andReturn();
        assertNotEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteOfficeByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/office/{id}", "8841")).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
    }

    private List<Office> getListOfficeFromResult(MvcResult mvcResult)
            throws IOException, JsonParseException, JsonMappingException, UnsupportedEncodingException {
        return mapper.readValue(mvcResult.getResponse().getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, Office.class));
    }
}