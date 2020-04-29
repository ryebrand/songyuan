package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 15:38:51
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
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
    UserEntity getUserIdByUserName(@Param("username") String username);

    void removeById(Serializable serializable);

    List<UserEntity> getList();

}
