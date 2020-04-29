package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.DefectNameRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 故障类型表
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface DefectNameRecordDao extends BaseMapper<DefectNameRecordEntity> {
    /**
     * 获取缺陷描述
     * @return
     */
	public List<Map<String,Object>> getRecords();
}
