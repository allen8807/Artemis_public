package cc.ubw.artemis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created with IntelliJ IDEA.
 * User: Allen
 * Date: 2019/12/23 0023
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Controller
public class DefaultController {
    @GetMapping("/zhaohuaxishi")
    public String getZhaohuaxishi() {
        return "zhaohuaxishi";
    }
}
