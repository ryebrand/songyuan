package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.DefectNameRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 故障类型表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface DefectNameRecordService extends IService<DefectNameRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取缺陷描述
     * @return
     */
    public List<Map<String,Object>> getRecords();
}

