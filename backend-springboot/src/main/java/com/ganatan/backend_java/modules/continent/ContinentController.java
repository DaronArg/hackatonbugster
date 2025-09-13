package com.ganatan.backend_java.modules.continent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ContinentController {

    private static final Logger logger = LoggerFactory.getLogger(ContinentController.class);
    private final ContinentService service;

    public ContinentController(ContinentService service) {
        logger.info("ContinentController Constructor initialized");
        this.service = service;
    }

    @GetMapping("/continents")
    public List<Continent> getItems() {
        logger.info("GET /continents");
        return service.getItems();
    }
}
