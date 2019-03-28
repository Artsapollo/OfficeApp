package springData.dto;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springData.util.DtoModelsUtil;


import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OfficeRequestTest {

    private Validator validator;

    @Before
    public void setUp() {
        LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        validator = localValidatorFactory;
    }

    @Test
    public void testOfficeRequestValid() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testOfficeRequestOfficeSmallNumber() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOffice(BigDecimal.valueOf(3));
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("office").getDefaultMessage(), "1");
    }

    @Test
    public void testOfficeRequestOfficeIsNull() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOffice(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("office").getDefaultMessage(), "1");
    }

    @Test
    public void testOfficeRequestCityWrongFormat() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setCity("Coppenshnizzelgarden");
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("city").getDefaultMessage(), "2");
    }

    @Test
    public void testOfficeRequestCityIsNull() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setCity(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("city").getDefaultMessage(), "2");
    }

    @Test
    public void testOfficeRequestRegionIsNumber() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setRegion("9032492");
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("region").getDefaultMessage(), "3");
    }

    @Test
    public void testOfficeRequestRegionIsNull() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setRegion(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("region").getDefaultMessage(), "3");
    }

    @Test
    public void testOfficeRequestOfficeDetailsIsNull() {
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        officeRequest.setOfficeDetails(null);
        Errors errors = new BeanPropertyBindingResult(officeRequest, "officeRequest");
        validator.validate(officeRequest, errors);
        assertEquals(errors.getFieldError("officeDetails").getDefaultMessage(), "4");
    }
}
