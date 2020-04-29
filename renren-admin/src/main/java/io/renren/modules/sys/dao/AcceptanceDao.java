package io.renren.modules.sys.dao;

import io.renren.modules.sys.dto.AcceptanceInfoDto;
import io.renren.modules.sys.entity.AcceptanceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface AcceptanceDao extends BaseMapper<AcceptanceEntity> {

    /**
     * 获取limt page,pageSize数量的验收数据
     *
     * @param page
     * @param pageSize
     * @return
     */
    List<AcceptanceEntity> getAcceptanceInfo(@Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 根据设备类型type,验收类型acceptanceType，区域regionId和验收状态step统计
     *
     * @param type
     * @param acceptanceType
     * @param regionId
     * @param step
     * @return
     */
    int getCountByCondition(@Param("type") int type, @Param("acceptanceType") int acceptanceType, @Param("regionId") long regionId, @Param("step") int step);

    /**
     * 根据验收id查询验收详细信息
     *
     * @param id
     * @return
     */
    AcceptanceInfoDto selectByAcceptanceId(@Param("id") Long id);
}
