package io.renren.modules.sys.service.impl;

import io.renren.common.exception.RRException;
import io.renren.modules.sys.dto.AcceptanceInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.AcceptanceDao;
import io.renren.modules.sys.entity.AcceptanceEntity;
import io.renren.modules.sys.service.AcceptanceService;


/**
 * @author JunCheng He
 */
@Service("acceptanceService")
public class AcceptanceServiceImpl extends ServiceImpl<AcceptanceDao, AcceptanceEntity> implements AcceptanceService {

    @Autowired
    private AcceptanceDao acceptanceDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AcceptanceEntity> page = this.page(
                new Query<AcceptanceEntity>().getPage(params),
                new QueryWrapper<AcceptanceEntity>().orderByDesc("build_date")
        );

        return new PageUtils(page);
    }

    @Override
    public List<AcceptanceEntity> getAcceptanceInfo(int page, int pageSize) {
        List<AcceptanceEntity> acceptanceEntity = null;
        acceptanceEntity = acceptanceDao.getAcceptanceInfo(page, pageSize);
        if (acceptanceEntity == null) {
            throw new RRException("未找到对应数据");
        }
        return acceptanceEntity;
    }

    @Override
    public int getCountByCondition(int type, int acceptanceType, long regionId, int step) {
        int count = 0;
        count = acceptanceDao.getCountByCondition(type, acceptanceType, regionId, step);
        return count;
    }

    /**
     * 根据验收id查询验收详细信息
     *
     * @param id
     * @return
     */
    @Override
    public AcceptanceInfoDto selectByAcceptanceId(Long id) {
        return this.baseMapper.selectByAcceptanceId(id);
    }

}
