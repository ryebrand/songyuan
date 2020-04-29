package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import io.renren.common.exception.RRException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.UserDao;
import io.renren.modules.sys.entity.UserEntity;
import io.renren.modules.sys.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username =(String) params.get("username");
        String mobile =(String) params.get("mobile");
        String name =(String) params.get("name");

        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
                        .like("del_status",0)
                        .like(!StringUtils.isBlank(username), "username", params.get("username"))
                        .like(!StringUtils.isBlank(mobile), "mobile", params.get("mobile"))
                        .like(!StringUtils.isBlank(name), "name", params.get("name"))
        );

        return new PageUtils(page);
    }

    @Override
    public List<UserEntity> list(Wrapper<UserEntity> queryWrapper) {

        return this.baseMapper.getList();
    }

    @Override
    public UserEntity getUserById(long id) {
        UserEntity userEntity=null;
        userEntity= this.baseMapper.getUserById(id);
        if(userEntity==null){
            throw  new RRException("未找到对应的数据");
        }
        return userEntity;
    }

    @Override
    public UserEntity getUserIdByUserName(String username) {
        UserEntity userEntity=null;
        userEntity=this.baseMapper.getUserIdByUserName(username);
        if(userEntity==null){
            throw new RRException("用户不存在！");
        }
        return userEntity;
    }


    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if(idList == null) {
            return true;
        }
        for (Serializable serializable : idList) {
            this.baseMapper.removeById(serializable);
        }
        return true;
    }
}
