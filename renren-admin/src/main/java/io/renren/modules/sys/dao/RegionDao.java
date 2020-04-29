package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.dto.RegionDto;
import io.renren.modules.sys.entity.RegionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 15:38:51
 */
@Mapper
public interface RegionDao extends BaseMapper<RegionEntity> {
    /**
     * 根据id获取区域信息
     *
     * @param id
     * @return
     */
    RegionEntity getRegionById(long id);


    /**
     * 责任人列表
     *
     * @param page
     * @param name
     * @param username
     * @param tbUserName
     * @return
     */
    List<RegionDto> pageList(Page page, @Param("name") String name, @Param("username") String username, @Param("tbUserName") String tbUserName);
}
