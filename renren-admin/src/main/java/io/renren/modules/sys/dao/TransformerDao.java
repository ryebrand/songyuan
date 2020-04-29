package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.TransformerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 13:44:48
 */
@Mapper
public interface TransformerDao extends BaseMapper<TransformerEntity> {

    boolean removeById(Serializable id);

    TransformerEntity getById(Serializable id);

    boolean removeByUserId(String userId);

    boolean insetList(List<TransformerEntity> list);

}
