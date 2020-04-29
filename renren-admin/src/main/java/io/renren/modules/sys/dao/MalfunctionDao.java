package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.dto.DefectLevelGroupDto;
import io.renren.modules.sys.dto.FaultReminderDto;
import io.renren.modules.sys.dto.MalfunctionInfoDto;
import io.renren.modules.sys.dto.MalfunctionStatics;
import io.renren.modules.sys.entity.MalfunctionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:09
 */
@Mapper
public interface MalfunctionDao extends BaseMapper<MalfunctionEntity> {
    /**
     * 根据id获取故障信息
     *
     * @param id
     * @return
     */
    MalfunctionEntity getMalfunctionById(long id);

    /**
     * 根据故障表id获取缺陷等级
     *
     * @param id
     * @return
     */
    MalfunctionEntity getDefectLevelById(long id);

    /**
     * 根据故障类型type、缺陷级别defectLevel和处理状态processingStatus，进行统计
     *
     * @param type
     * @param defectLevel
     * @param processingStatus
     * @return
     */
    int getCountByCondition(@Param("type") int type, @Param("defectLevel") int defectLevel, @Param("processingStatus") int processingStatus);

    /**
     * 分页查找缺陷记录
     *
     * @param page
     * @param start  开始时间
     * @param end    结束时间
     * @param types   故障类型
     * @param levels 缺陷级别
     * @param statuses 缺陷处理状态
     * @return
     */
    List<MalfunctionInfoDto> pageList(Page page, @Param("start") String start, @Param("end") String end, @Param("types") String[] types, @Param("levels") String[] levels, @Param("statuses") String[] statuses);

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    MalfunctionInfoDto selectByMalfunctionId(@Param("id") Long id);

    /**
     * 根据不同处理状态统计数目
     *
     * @param processingStatus
     * @return
     */
    List<MalfunctionStatics> countByProcessingStatus(@Param("processingStatus") Integer processingStatus);


    /**
     * 获取最新6条 故障记录
     */
    List<FaultReminderDto> selectTopSix();

    /**
     * 获取指定缺陷等级的指定时间段内的数据
     *
     * @param defectLevel
     * @param startTime
     * @param endTime
     * @return
     */
    int getDefectLevelByTime(@Param("defectLevel") int defectLevel, @Param("taiAreaName") String taiAreaName, @Param("powerSupply") String powerSupply, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取指定处理状态的指定时间段内的数据
     *
     * @param processingStatus
     * @param startTime
     * @param endTime
     * @return
     */
    int getProcessingStatus(@Param("processingStatus") int processingStatus, @Param("taiAreaName") String taiAreaName, @Param("powerSupply") String powerSupply, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据缺陷级别统计
     *
     * @return
     */
    List<DefectLevelGroupDto> groupByDefectLevel();

    int getCountByUser(long userId);
}
