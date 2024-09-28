package site.icefox.javaeelearn.Learn2.target50.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import site.icefox.javaeelearn.Learn2.target50.service.UserService;

@Controller
public class UserController {
    //使用@Resource注解注入UserService
    @Resource(name="userService")
    private UserService userService;
    public void save(){
        this.userService.save();
        System.out.println("执行UserController.save()");
    }
}

