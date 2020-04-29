package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.DefectNameRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 故障类型表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface DefectNameRecordDao extends BaseMapper<DefectNameRecordEntity> {
	
}
