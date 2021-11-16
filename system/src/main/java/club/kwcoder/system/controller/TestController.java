package club.kwcoder.system.controller;


import club.kwcoder.server.dataobject.TestDO;
import club.kwcoder.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zhinushannan
 * @create: 2021/11/15  17:02
 * @description:
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/test")
    public List<TestDO> test() {
        return testService.list();
    }

}
