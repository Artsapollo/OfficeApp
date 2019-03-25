package springData.repository;

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
import springData.config.DataConfig;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
@TestPropertySource("classpath:springData/test.properties")
public class OfficesRepositoryIntegrationH2Test {

    private static final Office INSERT_OFFICE = new Office(BigDecimal.valueOf(3333), "StarCity","Star",BigDecimal.valueOf(105));
    private static final Office UPDATE_OFFICE = new Office(BigDecimal.valueOf(1111),"Gotham","Gotham",BigDecimal.valueOf(501));
    private static final Office DELETE_OFFICE = new Office(BigDecimal.valueOf(3333),null,null,null);
    private static final BigDecimal NOT_EXIST_OFFICE_ID = BigDecimal.valueOf(-1);


    @Autowired
    private OfficesRepository officesRepository;

    @Autowired
    private DataSource dataSource;

    @Before
    public void initDb() {
        Resource initSchema = new ClassPathResource("springData\\script\\schema.sql");
        Resource data = new ClassPathResource("springData\\script\\data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    public void testFindByTargetBetween() {
        Set<Office> office = officesRepository.findByTargetBetween(BigDecimal.valueOf(300), BigDecimal.valueOf(600));
        assertNotNull(office.size() == 1);
    }

    @Test
    public void testGetAllOffices(){
        List<Office> office = officesRepository.findAll();
        System.out.println(office);
        assertTrue(office.size() >= 2);
    }

    @Test
    public void testFindOfficeById() {
        Optional<Office> office = officesRepository.findById(BigDecimal.valueOf(1111));
        assertNotNull(office.isPresent());
    }

    @Test
    public void testFindOfficeByIdNotExist() {
        Optional<Office> office = officesRepository.findById(NOT_EXIST_OFFICE_ID);
        assertFalse(office.isPresent());
    }

    @Test
    public void testInsertOffice() {
        officesRepository.save(INSERT_OFFICE);
    }

    @Test
    public void testUpdateOffice() {
        UPDATE_OFFICE.setCity("Akropol");
        UPDATE_OFFICE.setSales(BigDecimal.valueOf(123994));
        officesRepository.save(UPDATE_OFFICE);
        System.out.println(UPDATE_OFFICE);
    }

    @Test
    public void testDeleteOffice() {
        officesRepository.delete(INSERT_OFFICE);
    }

}