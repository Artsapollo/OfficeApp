package springData.service;

import dataBase.entity.Office;
import springData.dto.OfficeRequest;

public interface OfficeCreator {
        Office createOffice(OfficeRequest officeRequest);
}
