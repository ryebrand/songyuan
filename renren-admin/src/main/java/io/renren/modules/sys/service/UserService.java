package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.UserEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 15:38:51
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);


    @Override
    List<UserEntity> list(Wrapper<UserEntity> queryWrapper);

    /**
     * 根据id获取责任人信息
     * @param id
     * @return
     */
    public UserEntity getUserById(long id);
    /**
     * 通过用户名获取用户id
     * @param username
     * @return
     */
    UserEntity getUserIdByUserName(String username);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);
}

