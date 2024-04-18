package com.wj.demo.test.service.impl;

import com.wj.demo.test.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 16:46
 */
@Service(value = "PersonService-student")
public class StudentServiceImpl implements PersonService {
    @Override
    public String getName() {
        return "student";
    }
}
