package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MeterEntity;
import io.renren.entity.TransformerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电能表表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface MeterDao extends BaseMapper<MeterEntity> {

    List<TransformerEntity> selectByUserId(String meterBoxId);

}
