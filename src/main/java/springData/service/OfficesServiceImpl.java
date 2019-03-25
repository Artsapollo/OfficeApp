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
    public Set<Office> findByTargetBetween(int minTarget, int maxTarget) {
        LOG.debug("findByTargetBetween, min={}, max={}", minTarget, maxTarget);
        HashSet<Office> result = new HashSet<>(
                officesRepository.findByTargetBetween(BigDecimal.valueOf(minTarget), BigDecimal.valueOf(maxTarget)));
        LOG.debug("findByTargetBetween, result={}", result);
        return result;
    }

    @Override
    public Set<Office> getAllOffices() {
        LOG.debug("getAllOffices");
        HashSet<Office> result = new HashSet<>(officesRepository.findAll());
        LOG.debug("getAllOffices return {} records", result.size());
        return result;
    }

    @Override
    public Office findOfficeById(BigDecimal id) {
        LOG.debug("findOfficeByCity, city={}", id);
        Office result = officesRepository.findById(id).orElse(null);
        LOG.debug("findOfficeByCity, result={}", result);
        return result;
    }

    @Override
    public void insertOffice(Office office) {
        LOG.debug("insert Office, office={}", office);
        officesRepository.save(office);
        LOG.debug("insert Office completed");
    }

    @Override
    public void updateOffice(Office office) {
        LOG.debug("update Office, office={}", office);
        officesRepository.save(office);
        LOG.debug("update Office completed");
    }

    @Override
    public void deleteOffice(BigDecimal office) {
        LOG.debug("delete Office, office={}", office);
        try {
            officesRepository.deleteById(office);
        } catch (EmptyResultDataAccessException e) {
            LOG.warn("Can't delete Office office{}, because it doesn't exist", office);
            throw new DeleteException("Can not delete Office " + office + ", because it doesn't exist");
        }
        LOG.debug("delete Office completed");
    }
}