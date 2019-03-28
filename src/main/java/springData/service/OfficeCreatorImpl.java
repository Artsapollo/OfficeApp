package springData.service;

import dataBase.entity.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import springData.dto.OfficeDetails;
import springData.dto.OfficeRequest;

@Service
public class OfficeCreatorImpl implements OfficeCreator {
    private static final Logger LOG = LogManager.getLogger(OfficeCreatorImpl.class);

    @Override
    public Office createOffice(OfficeRequest officeRequest) {
        LOG.info("createOffice, officeRequest={}", officeRequest);
        Office result = new Office();
        result.setOffice(officeRequest.getOffice());
        result.setCity(officeRequest.getCity());
        result.setRegion(officeRequest.getRegion());

        OfficeDetails officeDetails = officeRequest.getOfficeDetails();
        result.setSales(officeDetails.getSales());
        result.setTarget(officeDetails.getTarget());
        LOG.info("createOffice, office, that was created after={}", result);

        return result;
    }
}