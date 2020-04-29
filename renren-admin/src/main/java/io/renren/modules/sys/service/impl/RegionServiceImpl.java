package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;
import io.renren.common.exception.RRException;
import io.renren.modules.sys.dto.RegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.RegionDao;
import io.renren.modules.sys.entity.RegionEntity;
import io.renren.modules.sys.service.RegionService;


/**
 * @author JunCheng He
 */
@Service("regionService")
public class RegionServiceImpl extends ServiceImpl<RegionDao, RegionEntity> implements RegionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RegionEntity> page = this.page(
                new Query<RegionEntity>().getPage(params),
                new QueryWrapper<RegionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public RegionEntity getRegionById(long id) {
        RegionEntity regionEntity = null;
        regionEntity = this.baseMapper.getRegionById(id);
        if (regionEntity == null) {
            throw new RRException("未找到对应的数据");
        }
        return regionEntity;
    }

    /**
     * 分页查询区域
     *
     * @param params
     * @return
     */
    @Override
    public Page<RegionDto> pageList(Map<String, Object> params) {
        Page<RegionDto> page = new Page<>(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        page.setRecords(this.baseMapper.pageList(page, (String) params.get("name"), (String) params.get("username"), (String) params.get("tbUserName")));
        return page;
    }

    /**
     * 根据id更新管理员
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    public Boolean updateUserIdById(Long id, Long userId) {
        this.update(new UpdateWrapper<RegionEntity>().eq("id", id).set("user_id", userId));
        return true;
    }

}
