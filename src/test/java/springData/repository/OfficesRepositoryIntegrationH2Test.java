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
import springData.service.OfficesService;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
@TestPropertySource("classpath:springData/test.properties")
public class OfficesRepositoryIntegrationH2Test {

    private static final Office NOT_EXIST_OFFICE_INSERT = new Office(BigDecimal.valueOf(333333), null,null,null);
    private static final Office ALREADY_EXIST_OFFICE_UPDATE = new Office(BigDecimal.valueOf(111111),"Gotham",null,null);
    private static final Office ALREADY_EXIST_OFFICE_DELETE = new Office(BigDecimal.valueOf(333333),null,null,null);
    private static final BigDecimal NOT_EXIST_OFFICE_ID = BigDecimal.valueOf(-1);


    @Autowired
    private OfficesRepository officesRepository;

    @Autowired
    private OfficesService officesService;

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
    public void testGetAllOffices(){
        List<Office> office = officesRepository.findAll();
        System.out.println(office);
        assertTrue(office.size() >= 2);
    }

    @Test
    public void testFindOfficeById() {
        Optional<Office> office = officesRepository.findById(BigDecimal.valueOf(1111));
        assertTrue(office.isPresent());
    }

    @Test
    public void testInsertOffice() {
        officesRepository.saveAndFlush(NOT_EXIST_OFFICE_INSERT);
    }

    @Test
    public void testUpdateOffice() {
        ALREADY_EXIST_OFFICE_UPDATE.setSales(BigDecimal.valueOf(-333));
        ALREADY_EXIST_OFFICE_UPDATE.setTarget(BigDecimal.valueOf(-111));
        officesRepository.save(ALREADY_EXIST_OFFICE_UPDATE);
        System.out.println(ALREADY_EXIST_OFFICE_UPDATE);
    }

    @Test
    public void testDeleteOffice() {
        officesRepository.delete(ALREADY_EXIST_OFFICE_DELETE);
    }

}