package io.renren.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import io.renren.controller.VO.HtUserVO;
import io.renren.dto.MeterBoxInfoDto;
import io.renren.dto.MeterBoxSearchDto;
import io.renren.dto.RecentInspeBoxDto;
import io.renren.entity.MeterBoxEntity;


/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface MeterBoxService extends IService<MeterBoxEntity> {

    /**
     * 根据电表箱资产号查询电表箱详情
     *
     * @param meterBoxId 电表箱资产号
     * @return
     */
    HtUserVO selectByMeterBoxAssetNumber(String meterBoxId);

    /**
     * 分页查询
     *
     * @param userId
     * @return
     */
    PageInfo<HtUserVO> selectByUserId(Integer pageNum, Integer pageSize, Long userId);

    /**
     * 根据资产号或地址模糊查询电表箱
     *
     * @param pageNum
     * @param pageSize
     * @param assetOrAddress
     * @return
     */
    PageInfo<HtUserVO> selectByAssetNumOrAddress(Integer pageNum, Integer pageSize, String assetOrAddress,long loginUser);

    /**
     * 通过电表箱id获取电表箱资产编号
     *
     * @param id
     * @return
     */
    HtUserVO getMeterBoxAssetNumberById(String id);

    boolean updateByHtUserId(HtUserVO htUserVO);
}

