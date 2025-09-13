package com.ganatan.backend_java.modules.person;

import com.ganatan.backend_java.shared.controllers.GenericController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController extends GenericController<Person, Long> {
    public PersonController(PersonService service) {
        super(service);
    }
}
