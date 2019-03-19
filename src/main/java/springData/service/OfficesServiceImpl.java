package springData.service;


import dataBase.entity.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import springData.exception.DeleteException;
import springData.repository.OfficesRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class OfficesServiceImpl implements OfficesService {

    private static final Logger LOG = LogManager.getLogger(OfficesServiceImpl.class);

    @Autowired
    private OfficesRepository officesRepository;

    @Override
    public Set<Office> getAllOffices() {
        LOG.debug("getAllOffices");
        HashSet<Office> result = new HashSet<>(officesRepository.findAll());
        LOG.debug("getAllOffices return {} records", result.size());
        return result;
    }

    @Override
    public Office getOfficeById(BigDecimal id) {
        LOG.debug("getOfficeByCity, city={}", id);
        Office result = officesRepository.findById(id).orElse(null);
        LOG.debug("findOfficeByCity, result={}", result);
        return result;
    }

    @Override
    public void insertOffice(Office office) {
        LOG.debug("insertOffice, office={}", office);
        officesRepository.save(office);
        LOG.debug("insertOffice completed");
    }

    @Override
    public void updateOffice(Office office) {
        LOG.debug("updateOffice, office={}", office);
        officesRepository.save(office);
        LOG.debug("updateOffice completed");
    }

    @Override
    public void deleteOffice(Office office) {
        LOG.debug("deleteOffice, office={}", office);
        try {
            officesRepository.delete(office);
        } catch (EmptyResultDataAccessException e) {
            LOG.warn("Can not deleteOffice office{}, because it doesn't exist", office);
            throw new DeleteException("Can not delete Office " + office + ", because it doesn't exist");
        }
        LOG.debug("deleteOffice completed");
    }

    @Override
    public Set<Office> findByTargetBetween(int minTarget, int maxTarget) {
        LOG.debug("findByTargetBetween, min={}, max={}", minTarget, maxTarget);
        HashSet<Office> result = new HashSet<>(
                officesRepository.findByTargetBetween(BigDecimal.valueOf(minTarget), BigDecimal.valueOf(maxTarget)));
        LOG.debug("findByTargetBetween, result={}", result);
        return result;
    }

}