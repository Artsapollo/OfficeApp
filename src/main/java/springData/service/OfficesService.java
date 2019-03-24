package springData.service;

import dataBase.entity.Office;

import java.math.BigDecimal;
import java.util.Set;

public interface OfficesService {

    Set<Office> getAllOffices();

    Set<Office> findByTargetBetween(int minTarget, int maxTarget);

    Office findOfficeById(BigDecimal office);

    void insertOffice(Office office);

    void updateOffice(Office office);

    void deleteOffice(BigDecimal office);
}