package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.AcceptanceInfoDto;
import io.renren.modules.sys.entity.AcceptanceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface AcceptanceService extends IService<AcceptanceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取limt page,pageSize数量的验收数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<AcceptanceEntity> getAcceptanceInfo(int page, int pageSize);

    /**
     * 根据设备类型type,验收类型acceptanceType，区域regionId和验收状态step统计
     *
     * @param type
     * @param acceptanceType
     * @param regionId
     * @param step
     * @return
     */
    int getCountByCondition(int type, int acceptanceType, long regionId, int step);

    /**
     * 根据验收id查询验收详细信息
     *
     * @param id
     * @return
     */
    AcceptanceInfoDto selectByAcceptanceId(Long id);
}

