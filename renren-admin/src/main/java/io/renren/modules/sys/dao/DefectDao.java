package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.DefectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 故障问题表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface DefectDao extends BaseMapper<DefectEntity> {
	
}
