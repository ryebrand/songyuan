package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.dto.MeterBoxDetailsDto;
import io.renren.modules.sys.dto.MeterBoxDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.HtUserEntity;
import io.renren.modules.sys.entity.MeterBoxEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface MeterBoxDao extends BaseMapper<MeterBoxEntity> {

    /**
     * 根据资产号 地址 台区名称查询 电表箱列表
     *
     * @param page
     * @param assetNumber
     * @param meterBoxStatus
     * @param address
     * @param taiAreaName
     * @return
     */
    List<MeterBoxDto> listByAssetNumberAndAddressAndTaiAreaName(Page page,
                                                                @Param("assetNumber") String assetNumber,
                                                                @Param("address") String address,
                                                                @Param("meterBoxStatus") String meterBoxStatus,
                                                                @Param("taiAreaName") String taiAreaName);

    /**
     * 根据资产号查询id
     *
     * @param meterBoxAssetNumber
     * @return
     */
    Long selectIdByMeterBoxAssetNumber(@Param("meterBoxAssetNumber") String meterBoxAssetNumber);

    /**
     * 根据电表箱设备编号获取电表箱信息
     *
     * @param assetNumber
     * @return
     */
    public MeterBoxEntity getMeterBoxByAssetNumber(String assetNumber);

    /**
     * 通过电表箱id获取电表箱资产编号
     *
     * @param id
     * @return
     */
    MeterBoxEntity getMeterBoxAssetNumberById(String id);

    /**
     * 按供电所和台区进行电表箱统计
     *
     * @return
     */
    List<Map<String, Object>> getTotalByDiv();

    /**
     * 按供电所和台区进行处理状态统计
     *
     * @return
     */
    List<Map<String, Object>> getProcessingStatusByDiv();

    /**
     * 根据电表箱资产号查询电表箱详情
     *
     * @param assetNumber
     * @return
     */
    MeterBoxDetailsDto selectByAssetNumber(@Param("assetNumber") String assetNumber);

    /**
     * 根据高压用户编号查询
     * @param id
     * @return
     */
    HtUserVO getByUserId(String id);
}
