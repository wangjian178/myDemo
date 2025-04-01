package com.wj.demo.core.test.service.impl;

import com.wj.demo.core.test.service.PersonService;
import org.springframework.stereotype.Service;

/**
 * @author wj
 * @version 1.0
 * @Desc
 * @date 2024/4/18 16:46
 */
@Service(value = "PersonService_default")
public class DefaultServiceImpl implements PersonService {

    @Override
    public String getName() {
        return "default";
    }
}
