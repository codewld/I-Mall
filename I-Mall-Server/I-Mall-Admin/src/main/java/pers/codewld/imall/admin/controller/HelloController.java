package pers.codewld.imall.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.codewld.imall.log.annotation.DisableLogDBStorage;

/**
 * <p>
 * 测试 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-02-04
 */
@DisableLogDBStorage
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
