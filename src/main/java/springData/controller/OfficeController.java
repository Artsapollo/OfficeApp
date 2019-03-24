package springData.controller;

import dataBase.entity.Office;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springData.exception.DeleteException;
import springData.service.OfficesService;

@RestController
@RequestMapping("/office")
public class OfficeController {
    private static final Logger LOG = LogManager.getLogger(OfficeController.class);

    @Autowired
    private OfficesService officesService;

    @GetMapping("/{id}")
    public @ResponseBody
    Office getOfficeById(@PathVariable("id") int id) {
        LOG.info("findOfficeById start, id={}", id);
        Office result = officesService.findOfficeById(java.math.BigDecimal.valueOf(id));
        LOG.info("findOfficeById end");
        throw new DeleteException("ERROR");
//        return result;
    }
}
