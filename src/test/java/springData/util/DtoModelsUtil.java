package springData.util;

import dataBase.entity.Office;
import springData.dto.OfficeDetails;
import springData.dto.OfficeRequest;

import java.math.BigDecimal;

public class DtoModelsUtil {

    private static final BigDecimal OFFICE = BigDecimal.valueOf(105);
    private static final String CITY = "Kyiv";
    private static final String REGION = "Street";
    private static final BigDecimal TARGET = BigDecimal.valueOf(450);
    private static final BigDecimal SALES = BigDecimal.valueOf(490);

    public static Office office(){
        Office office = new Office();
        office.setOffice(OFFICE);
        office.setCity(CITY);
        office.setRegion(REGION);
        office.setTarget(TARGET);
        office.setSales(SALES);
        return office;
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