package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dto.RegionDto;
import io.renren.modules.sys.entity.RegionEntity;

import java.util.Map;

/**
 * 区域表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 15:38:51
 */
public interface RegionService extends IService<RegionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据id获取区域信息
     *
     * @param id
     * @return
     */
    RegionEntity getRegionById(long id);

    /**
     * 分页查询区域
     *
     * @param params
     * @return
     */
    Page<RegionDto> pageList(Map<String, Object> params);


    /**
     * 根据id更新管理员
     *
     * @param id
     * @param userId
     * @return
     */
    Boolean updateUserIdById(Long id, Long userId);
}

