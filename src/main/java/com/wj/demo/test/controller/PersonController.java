package com.wj.demo.test.controller;

import com.wj.demo.baseContext.HandlerAdapter;
import com.wj.demo.test.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 16:47
 */
@RestController
@RequestMapping("person")
public class PersonController {

    @GetMapping("get")
    public String get(){
        PersonService personService = HandlerAdapter.finderHandler(PersonService.class);
        return personService.getName();
    }
}
