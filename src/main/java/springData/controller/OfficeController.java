package springData.controller;

import dataBase.entity.Office;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springData.dto.OfficeRequest;
import springData.exception.UpdateException;
import springData.service.OfficeCreator;
import springData.service.OfficesService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/office")
public class OfficeController {
    private static final Logger LOG = LogManager.getLogger(OfficeController.class);

    @Autowired
    private OfficesService officesService;

    @Autowired
    private OfficeCreator officeCreator;

    @GetMapping
    public @ResponseBody
    Set<Office> findOfficeByTargetBetween(@RequestParam(value = "min", required = false) Integer minTarget,
                                          @RequestParam(value = "max", required = false) Integer maxTarget) {
        LOG.info("findByTargetBetween start, min={}, max={}", minTarget, maxTarget);
        if (Objects.isNull(maxTarget) || Objects.isNull(minTarget)) {
            LOG.debug("findOfficeByTargetBetween use getAllOffices");
            return officesService.getAllOffices();
        }
        Set<Office> result = officesService.findByTargetBetween(minTarget, maxTarget);
        LOG.info("findOfficeByTargetBetween done.");
        return result;
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Office getOfficeById(@PathVariable("id") Integer id) {
        LOG.info("findOfficeById start, id={}", id);
        Office result = officesService.findOfficeById(BigDecimal.valueOf(id));
        LOG.info("findOfficeById end");
        return result;
    }

    @PostMapping
    @ApiOperation(authorizations = { @Authorization(value = "basicAuthorization") }, value = "addOffice")
    public void addOffice (@Valid @RequestBody OfficeRequest officeRequest){
        LOG.info("addOffice start, officeRequest={}", officeRequest);
        Office office = officeCreator.createOffice(officeRequest);
        officesService.insertOffice(office);
        LOG.info("addOffice finished");
    }



    @PutMapping("/{id}")
    @ApiOperation(authorizations = { @Authorization(value = "basicAuthorization") }, value = "updateOffice")
    public void updateOfficeById(@PathVariable("id") Integer id, @RequestParam("city") String city) {
        LOG.info("Updating Office id={}, city={}", id, city);
        Office office = officesService.findOfficeById(BigDecimal.valueOf(id));
        if (Objects.isNull(office)) {
            LOG.warn("Updating failed, Office is null");
            throw new UpdateException("Could not update Office id= " + id + " , it doesn't exist.");
        } else {
            office.setCity(city);
            officesService.updateOffice(office);
        }
        LOG.info("Office updated");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(authorizations = { @Authorization(value = "basicAuthorization") }, value = "deleteOffice")
    public void deleteOfficeById(@PathVariable("id") Integer id) {
        LOG.info("Deleting office id={}", id);
        officesService.deleteOffice(BigDecimal.valueOf(id));
        LOG.info("Office deleted");
    }
}