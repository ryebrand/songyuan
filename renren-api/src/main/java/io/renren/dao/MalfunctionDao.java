package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.dto.FaultReminderDto;
import io.renren.dto.MalfunctionDto;
import io.renren.entity.MalfunctionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:44
 */
@Mapper
public interface MalfunctionDao extends BaseMapper<MalfunctionEntity> {
    /**
     * 根据故障表id获取缺陷等级
     *
     * @param id
     * @return
     */
    MalfunctionEntity getDefectLevelById(long id);

    /**
     * 获取所有缺陷
     *
     * @param defectLevel      缺陷级别
     * @param processingStatus 缺陷处理状态
     * @param userId           用户id
     * @return
     */
    List<MalfunctionDto> pageList(@Param("defectLevel") String defectLevel, @Param("processingStatus") String processingStatus, @Param("userId") Long userId);


    /**
     * 故障提醒
     *
     * @param id
     * @return
     */
    FaultReminderDto selectByMalfunctionId(@Param("id") Long id);

    MalfunctionDto getDetailById(@Param("id") Long id);


    MalfunctionDto getDetailByhtUserId(@Param(("htUserId")) String htUserId);

    String getUserNameById(String id);
}
