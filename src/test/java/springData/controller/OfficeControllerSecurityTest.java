package springData.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataBase.entity.Office;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springData.config.MainMvcConfig;
import springData.dto.ErrorMessage;
import springData.util.DtoModelsUtil;

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
public class OfficeControllerSecurityTest {

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
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
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
    @WithMockUser(roles = "ADMIN")
    public void testGetOfficeExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/office/{id}", "111111")).andDo(print()).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        Office office = mapper.readValue(mvcResult.getResponse().getContentAsString(), Office.class);
        assertNotNull(office);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetOfficeNotExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/office/{id}", "8841")).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertTrue(mvcResult.getResponse().getContentAsString().length() == 0);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddOfficeAuth() throws Exception {
        String json = mapper.writeValueAsString(DtoModelsUtil.officeRequest());
        MvcResult mvcResult = mockMvc.perform(post("/office").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andReturn();

        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateOfficeExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/office/{id}", "111111").param("city", "Gotham")).andDo(print()).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateOfficeNotExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/office/{id}", "8841").param("city", "Noir")).andDo(print()).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        ErrorMessage errorMessage = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
        assertEquals("Could not update Office id= 8841 , it doesn't exist.", errorMessage.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteOfficeExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/office/{id}", "111111")).andDo(print()).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteOfficeNotExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/office/{id}", "8841")).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        ErrorMessage errorMessage = mapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorMessage.class);
        assertEquals("Can not delete Office 8841, because it doesn't exist", errorMessage.getMessage());

    }

    private List<Office> getListOfficeFromResult(MvcResult mvcResult)
            throws IOException, JsonParseException, JsonMappingException, UnsupportedEncodingException {
        return mapper.readValue(mvcResult.getResponse().getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, Office.class));
    }
}