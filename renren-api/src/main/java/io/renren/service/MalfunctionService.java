package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import io.renren.dto.FaultReminderDto;
import io.renren.dto.MalfunctionDto;
import io.renren.entity.MalfunctionEntity;


/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:44
 */
public interface MalfunctionService extends IService<MalfunctionEntity> {
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
     * @param pageNum
     * @param pageSize
     * @param processingStatus
     * @param defectLevel
     * @param userId
     * @return
     */
    PageInfo<MalfunctionDto> pageList(Integer pageNum, Integer pageSize, String processingStatus, String defectLevel, Long userId);

    /**
     * 故障提醒
     *
     * @param id
     * @return
     */
    FaultReminderDto selectByMalfunctionId(Long id);

    /**
     * 故障id查询详情
     * @param id
     * @return
     */
    MalfunctionDto getDetailById(Long id);

    /**
     * 根据表箱id查详情
     * @param id
     * @return
     */
    MalfunctionDto detailsByUserId(String id);
}

