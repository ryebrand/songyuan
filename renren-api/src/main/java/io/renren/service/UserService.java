/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.UserEntity;
import io.renren.form.LoginForm;

import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	/**
	 * 根据用户名获取用户对象
	 * @param username 用户名
	 * @return 用户对象
	 */
	UserEntity queryByUsername(String username);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return 返回登录信息
	 */
	Map<String, Object> login(LoginForm form);

	/**
	 * 用户登录
	 * @param userEntity
	 * @return
	 */
	UserEntity login(UserEntity userEntity);

	//
	//@Override
	//boolean save(UserEntity entity);

	/**
	 * 通过用户名获取用户id
	 * @param username
	 * @return
	 */
	UserEntity getUserIdByUserName(String username);
}
