package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {


    @PostMapping("/login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public R info () {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
