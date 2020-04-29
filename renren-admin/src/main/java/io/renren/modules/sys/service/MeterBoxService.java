package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.dto.MeterBoxDetailsDto;
import io.renren.modules.sys.dto.MeterBoxDto;
import io.renren.modules.sys.entity.HtUserEntity;
import io.renren.modules.sys.entity.MeterBoxEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface MeterBoxService extends IService<MeterBoxEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 分页查找
     *
     * @param params
     * @return
     */
    Page<MeterBoxDto> pageList(Map<String, Object> params);


    /**
     * Excel 导入表箱数据
     *
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    /**
     * 根据电表箱设备编号获取电表箱信息
     *
     * @param assetNumber
     * @return
     */
    MeterBoxEntity getMeterBoxByAssetNumber(String assetNumber);

    /**
     * 通过电表箱id获取电表箱资产编号
     *
     * @param id
     * @return
     */
    MeterBoxEntity getMeterBoxAssetNumberById(String id);


    /**
     * 根据id更新责任人
     *
     * @param id
     * @param ownerId
     * @return
     */
    Boolean updateByIdAndOwnerId(String id, Long ownerId);

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
    MeterBoxDetailsDto selectByAssetNumber(String assetNumber);

    HtUserVO getByUserId(String id);
}

