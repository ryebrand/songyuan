package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.AcceptanceBoxListDto;
import io.renren.dto.AcceptanceDto;
import io.renren.entity.AcceptanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface AcceptanceDao extends BaseMapper<AcceptanceEntity> {

    /**
     * 根据资产号查询验收信息
     *
     * @param meterBoxAssetNumber
     * @param userId
     * @return
     */
    AcceptanceDto selectByAssetNumber(@Param("meterBoxAssetNumber") String meterBoxAssetNumber, @Param("userId") Long userId);


    /**
     * 用户id查询验收列表
     *
     * @param userId
     * @return
     */
    List<AcceptanceBoxListDto> selectByUserId(@Param("userId") Long userId,@Param("acceptanceType") String acceptanceType, @Param("step") String step);
}
