/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import io.renren.dao.UserDao;
import io.renren.entity.TokenEntity;
import io.renren.entity.UserEntity;
import io.renren.form.LoginForm;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity queryByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }


    @Override
    public Map<String, Object> login(LoginForm form) {
        UserEntity user = queryByUsername(form.getUsername());
        Assert.isNull(user, "用户名或密码错误");

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("用户名或密码错误");
        }

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

        return map;
    }



    @Override
    public UserEntity login(UserEntity userEntity) {
        if (StringUtils.isEmpty(this.baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", userEntity.getUsername())))){
            throw new RRException("用户不存在，请重新填写");
        }
//        userEntity.setPassword(DigestUtils.sha256Hex(userEntity.getPassword()));
        UserEntity user=this.baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", userEntity.getUsername()).eq("password", userEntity.getPassword()));
        if(StringUtils.isEmpty(user)){
            throw new RRException("密码不正确，请重新填写");
        }else{
            return user;
        }
    }

    @Override
    public UserEntity getUserIdByUserName(String username) {
        UserEntity userEntity=null;
        userEntity=userDao.getUserIdByUserName(username);
        if(userEntity==null){
            throw new RRException("用户不存在！");
        }
        return userEntity;
    }

    //@Override
    //public boolean save(UserEntity entity) {
    //
    //    if(entity == null)
    //        return true;
    //    return this.baseMapper.save(entity);
    //}
}
