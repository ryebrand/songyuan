package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.controller.VO.HtUserVO;
import io.renren.controller.VO.HtUserVTO;
import io.renren.dto.MeterBoxInfoDto;
import io.renren.dto.MeterBoxSearchDto;
import io.renren.dto.RecentInspeBoxDto;
import io.renren.entity.MeterBoxEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface MeterBoxDao extends BaseMapper<MeterBoxEntity> {

    /**
     * 根据电表箱资产号查询电表箱详情
     *
     * @param meterBoxId 电表箱资产号
     * @return
     */
    HtUserVO selectByMeterBoxAssetNumber(@Param("meterBoxId") String meterBoxId);


    /**
     * 根据用户id查询最近巡检过的表箱
     *
     * @param userId
     * @return
     */
    List<HtUserVTO> selectByUserId(@Param("userId") Long userId);

    /**
     * 通过电表箱id获取电表箱资产编号
     *
     * @param id
     * @return
     */
    HtUserVO getMeterBoxAssetNumberById(String id);


    /**
     * 模糊搜索
     *
     * @param assetNumberOrAddress
     * @return
     */
    List<HtUserVO> searchByAssetNumberOrAdderss(@Param("assetNumberOrAddress") String assetNumberOrAddress,@Param("userId") long userId);

    boolean updteByUserId(HtUserVO htUserVO);
}
