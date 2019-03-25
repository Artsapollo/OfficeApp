package springData.controller;

import dataBase.entity.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springData.exception.UpdateException;
import springData.service.OfficesService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/office")
public class OfficeController {
    private static final Logger LOG = LogManager.getLogger(OfficeController.class);

    @Autowired
    private OfficesService officesService;

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

    @PutMapping("/{id}")
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
    public void deleteOfficeById(@PathVariable("id") Integer id) {
        LOG.info("Deleting office id={}", id);
        officesService.deleteOffice(BigDecimal.valueOf(id));
        LOG.info("Office deleted");
    }
}