package kwcoder.club.system.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhinushannan
 * @create: 2021/11/15  17:02
 * @description:
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test")
    public String test() {
        return "success";
    }

}
