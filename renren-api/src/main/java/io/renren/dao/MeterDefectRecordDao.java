package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.MeterDefectRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 缺陷记录
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 13:48:14
 */
@Mapper
public interface MeterDefectRecordDao extends BaseMapper<MeterDefectRecordEntity> {
	
}
