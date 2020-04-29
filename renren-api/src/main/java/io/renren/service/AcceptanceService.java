package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.dto.AcceptanceBoxListDto;
import io.renren.dto.AcceptanceDto;
import io.renren.dto.AcceptanceSubmitDto;
import io.renren.entity.AcceptanceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 验收表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface AcceptanceService extends IService<AcceptanceEntity> {

    /**
     * 根据资产号更新验收状态
     *
     * @return
     */
    //Boolean updateByAssetNumber(AcceptanceSubmitDto acceptanceSubmitDto);
    Boolean updateByAssetNumber(Long malfunctionId, MultipartFile[] file);


    /**
     * 根据表箱资产号查询验收表箱
     *
     * @param meterBoxAssetNumber
     * @param userId
     * @return
     */
    AcceptanceDto selectByAssetNumber(String meterBoxAssetNumber, Long userId);


    /**
     * 用户id查询验收列表
     *
     * @param userId         用户id
     * @param acceptanceType 验收类型
     * @param step           验收状态
     * @return
     */
    List<AcceptanceBoxListDto> selectByUserId(Long userId, String acceptanceType, String step);

}

