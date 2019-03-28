package springData.service;

import dataBase.entity.Office;
import org.junit.Test;
import springData.dto.OfficeRequest;
import springData.util.DtoModelsUtil;

import static org.junit.Assert.*;

public class OfficeCreatorTest {

    private OfficeCreator officeCreator = new OfficeCreatorImpl();

    @Test
    public void testCreateOffice(){
        OfficeRequest officeRequest = DtoModelsUtil.officeRequest();
        Office actual = officeCreator.createOffice(officeRequest);
        Office expected = DtoModelsUtil.office();
        assertEquals(expected, actual);
    }
}
