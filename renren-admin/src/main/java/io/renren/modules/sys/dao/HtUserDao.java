
package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.entity.HtUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 14:11:13
 */
@Mapper
public interface HtUserDao extends BaseMapper<HtUserEntity> {

    boolean removeById(String id);


    List<HtUserEntity> listByAssetcodeAndUser(Page page, String htUserId, String address, String name);

    HtUserEntity getById(Serializable id);

    boolean updateHtuserById(HtUserEntity htUser);

    boolean removeByHtuserId(String userId);

    //boolean saveHtUser(HtUserVO htUserVO);
}
