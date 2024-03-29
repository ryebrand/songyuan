/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.UserEntity;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private HttpServletRequest request;

    //public static UserEntity loginUser;


//    @PostMapping("login")
//    @ApiOperation("登录")
//    public R login(@RequestBody LoginForm form){
//        //表单校验
//        ValidatorUtils.validateEntity(form);
//
//        //用户登录
//        Map<String, Object> map = userService.login(form);
//
//        return R.ok(map);
//    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId) {
        tokenService.expireToken(userId);
        return R.ok();
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody UserEntity userEntity) {
        //表单校验
        ValidatorUtils.validateEntity(userEntity);

        //用户登录

        UserEntity loginUser = userService.login(userEntity);


        return R.ok().put("user", loginUser);
    }

}
