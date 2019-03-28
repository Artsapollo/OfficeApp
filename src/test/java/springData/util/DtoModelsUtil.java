package springData.util;

import dataBase.entity.Office;
import springData.dto.OfficeDetails;
import springData.dto.OfficeRequest;

import java.math.BigDecimal;

public class DtoModelsUtil {

    private static final BigDecimal OFFICE = BigDecimal.valueOf(404);
    private static final String CITY = "Kyiv";
    private static final String REGION = "Kyivsky";
    private static final BigDecimal TARGET = BigDecimal.valueOf(45000);
    private static final BigDecimal SALES = BigDecimal.valueOf(50000);

    public static Office office(){
        Office expected = new Office();
        expected.setOffice(OFFICE);
        expected.setCity(CITY);
        expected.setRegion(REGION);
        expected.setTarget(TARGET);
        expected.setSales(SALES);
        return expected;
    }

    public static OfficeRequest officeRequest(){
        OfficeRequest officeRequest = new OfficeRequest();
        officeRequest.setOffice(OFFICE);
        officeRequest.setCity(CITY);
        officeRequest.setRegion(REGION);
        officeRequest.setOfficeDetails(officeDetails());
        return officeRequest;
    }

    public static OfficeDetails officeDetails(){
        OfficeDetails officeDetails = new OfficeDetails();
        officeDetails.setTarget(TARGET);
        officeDetails.setSales(SALES);
        return officeDetails;
    }
}