package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import io.renren.dto.InspectionPageListDto;
import io.renren.entity.InspectionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 巡检表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
@Mapper
public interface InspectionDao extends BaseMapper<InspectionEntity> {
    /**
     * 根据用户id和表箱id查询巡检列表
     *
     * @param userId     用户id
     * @param meterBoxId 表箱id
     * @return
     */
    Page<InspectionPageListDto> queryPageList(@Param("userId") Long userId, @Param("meterBoxId") Long meterBoxId);

    /**
     * 根据用户编码，指定从pageSize*(page-1)获取pageSize数量的缺陷列表数据
     *
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    List<InspectionEntity> getInspectionList(@Param("userid") long userid, @Param("page") int page, @Param("pageSize") int pageSize);


}
