package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.DefectLevelGroupDto;
import io.renren.modules.sys.dto.FaultReminderDto;
import io.renren.modules.sys.dto.MalfunctionInfoDto;
import io.renren.modules.sys.dto.MalfunctionStatics;
import io.renren.modules.sys.entity.MalfunctionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:09
 */
public interface MalfunctionService extends IService<MalfunctionEntity> {

    PageUtils queryPage(Map<String, Object> params);

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
     * @param params
     * @return
     */
    Page<MalfunctionInfoDto> pageList(Map<String, Object> params);


    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    MalfunctionInfoDto selectByMalfunctionId(Long id);

    /**
     * 根据id更新缺陷处理状态
     *
     * @param id
     * @param status
     * @return
     */
    Boolean updateByMalfuctionId(Long id, Integer status);


    /**
     * 根据不同处理状态统计数目
     *
     * @param processingStatus
     * @return
     */
    List<MalfunctionStatics> countByProcessingStatus(Integer processingStatus);
    /**
     * 获取最新6条 故障记录
     */
    List<FaultReminderDto> selectTopSix();


    /**
     * 获取指定缺陷等级的指定时间段内的数据
     * @param defectLevel
     * @param startTime
     * @param endTime
     * @return
     */
    int getDefectLevelByTime(@Param("defectLevel") int defectLevel,@Param("taiAreaName") String taiAreaName,@Param("powerSupply") String powerSupply,@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 获取指定处理状态的指定时间段内的数据
     * @param processingStatus
     * @param startTime
     * @param endTime
     * @return
     */
    int getProcessingStatus( @Param("processingStatus") int processingStatus,@Param("taiAreaName") String taiAreaName,@Param("powerSupply") String powerSupply,@Param("startTime") String startTime,@Param("endTime") String endTime);
    /**
     * 根据缺陷级别统计
     *
     * @return
     */
    List<DefectLevelGroupDto> groupByDefectLevel();


    int getCount(long userId);

}

