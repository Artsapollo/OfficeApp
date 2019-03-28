package springData.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springData.util.DtoModelsUtil;

import java.math.BigDecimal;

public class OfficeDetailsTest {

    private Validator validator;

    @Before
    public void setUp() {
        LocalValidatorFactoryBean localValidatorFactory = new LocalValidatorFactoryBean();
        localValidatorFactory.setProviderClass(HibernateValidator.class);
        validator = localValidatorFactory;
    }

    @Test
    public void testOfficeDetailsValid() {
        OfficeDetails officeDetails = DtoModelsUtil.officeDetails();
        Errors errors = new BeanPropertyBindingResult(officeDetails, "officeDetails");
        validator.validate(officeDetails, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testOfficeDetailsTargetGreaterThanMaxValue() {
        OfficeDetails officeDetails = DtoModelsUtil.officeDetails();
        officeDetails.setTarget(BigDecimal.valueOf(100000));
        Errors errors = new BeanPropertyBindingResult(officeDetails, "officeDetails");
        validator.validate(officeDetails, errors);
        assertEquals(errors.getFieldError("target").getDefaultMessage(), "4");
    }

    @Test
    public void testOfficeDetailsTargetIsNull() {
        OfficeDetails officeDetails = DtoModelsUtil.officeDetails();
        officeDetails.setTarget(null);
        Errors errors = new BeanPropertyBindingResult(officeDetails, "officeDetails");
        validator.validate(officeDetails, errors);
        assertEquals(errors.getFieldError("target").getDefaultMessage(), "4");
    }

    @Test
    public void testOfficeDetailsSalesValueIsNegative() {
        OfficeDetails officeDetails = DtoModelsUtil.officeDetails();
        officeDetails.setSales(BigDecimal.valueOf(-50000));
        Errors errors = new BeanPropertyBindingResult(officeDetails, "officeDetails");
        validator.validate(officeDetails, errors);
        assertEquals(errors.getFieldError("sales").getDefaultMessage(), "5");
    }

    @Test
    public void testOfficeDetailsSalesIsNull() {
        OfficeDetails officeDetails = DtoModelsUtil.officeDetails();
        officeDetails.setSales(null);
        Errors errors = new BeanPropertyBindingResult(officeDetails, "officeDetails");
        validator.validate(officeDetails, errors);
        assertEquals(errors.getFieldError("sales").getDefaultMessage(), "5");
    }
}