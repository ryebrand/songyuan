package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.DefectEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 故障问题表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:45
 */
@Mapper
public interface DefectDao extends BaseMapper<DefectEntity> {
	
}
