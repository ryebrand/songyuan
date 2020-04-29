package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.DefectDao;
import io.renren.modules.sys.entity.DefectEntity;
import io.renren.modules.sys.service.DefectService;


@Service("defectService")
public class DefectServiceImpl extends ServiceImpl<DefectDao, DefectEntity> implements DefectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DefectEntity> page = this.page(
                new Query<DefectEntity>().getPage(params),
                new QueryWrapper<DefectEntity>()
        );

        return new PageUtils(page);
    }

}
