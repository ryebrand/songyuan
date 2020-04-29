package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.dto.CreateTimeGroupDto;
import io.renren.modules.sys.dto.InspectionDto;
import io.renren.modules.sys.entity.InspectionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface InspectionDao extends BaseMapper<InspectionEntity> {

    /**
     * 根据巡检时间 责任人查询巡检记录
     *
     * @param start
     * @param end
     * @param bixId
     * @param name
     * @return
     */
    List<InspectionDto> selectByCreateTimeAndOwnerName(Page page, @Param("start") String start, @Param("end") String end, @Param("bixId") String bixId);

    /**
     * 根据用户编码，指定从pageSize*(page-1)获取pageSize数量的缺陷列表数据
     *
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    List<InspectionEntity> getInspectionList(@Param("userid") long userid, @Param("page") int page, @Param("pageSize") int pageSize);

    /**
     * 根据台区和供电局信息获取一段时间内巡检过的电表箱数量
     *
     * @param startTime
     * @param endTime
     * @param taiAreaName
     * @param powerSupply
     * @return
     */
    int getInspectedMeterCountByCondition(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("taiAreaName") String taiAreaName, @Param("powerSupply") String powerSupply);

    /**
     * 统计本月巡检次数
     *
     * @return
     */
    Integer countByNowMonth();

    /**
     * 根据最近几天时间统计巡检次数
     *
     * @param start
     * @param end
     * @return
     */
    List<CreateTimeGroupDto> groupByCreateTime(@Param("start") String start, @Param("end") String end);

}
