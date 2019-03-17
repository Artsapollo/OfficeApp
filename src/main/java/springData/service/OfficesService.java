package springData.service;

import dataBase.entity.Office;

import java.util.Set;

public interface OfficesService {

    Set<Office> getAllOffices();

    Set<Office> findByTargetBetween(int minTarget, int maxTarget);

    Office getOfficeByCity(String city);

    void insertOffice(Office office);

    void updateOffice(Office office);

    void deleteOffice(Office office);
}