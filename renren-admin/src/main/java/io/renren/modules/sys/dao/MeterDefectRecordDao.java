package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.MeterDefectRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 缺陷记录
 * 
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface MeterDefectRecordDao extends BaseMapper<MeterDefectRecordEntity> {
    /**
     * 统计每种缺陷的数量
     * @return
     */
	public List<Map<String,Integer>> getMeterDefectRecordById();
}
