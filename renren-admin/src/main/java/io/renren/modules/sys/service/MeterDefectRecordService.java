package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.MeterDefectRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 缺陷记录
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface MeterDefectRecordService extends IService<MeterDefectRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
    /**
     * 统计每种缺陷的数量
     * @return
     */
    public List<Map<String,Integer>> getMeterDefectRecordById();
}

