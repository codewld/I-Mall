package pers.codewld.imall.common.controller;

import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 错误 前端控制器
 * </p>
 *
 * @author codewld
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/err")
public class ErrorController {

    @RequestMapping
    public void err(@RequestAttribute("exception") Exception e) throws Exception {
        throw e;
    }

}
