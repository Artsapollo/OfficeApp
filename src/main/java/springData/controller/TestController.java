package springData.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/temp")
public @ResponseBody class TestController {
    @GetMapping
    public String index(){
        return "XXXXXXXXXXXX";
    }
}