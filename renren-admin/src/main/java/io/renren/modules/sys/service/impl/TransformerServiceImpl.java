package io.renren.modules.sys.service.impl;

import io.renren.common.exception.RRException;
import jdk.nashorn.internal.ir.LoopNode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.TransformerDao;
import io.renren.modules.sys.entity.TransformerEntity;
import io.renren.modules.sys.service.TransformerService;
import org.springframework.transaction.annotation.Transactional;


@Service("transformerService")
public class TransformerServiceImpl extends ServiceImpl<TransformerDao, TransformerEntity> implements TransformerService {

    @Autowired
    private TransformerDao transformerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<TransformerEntity> page = null;
        String userId = (String) params.get("htUserId");

        page = this.page(
                new Query<TransformerEntity>().getPage(params),
                new QueryWrapper<TransformerEntity>()
                        .eq(StringUtils.isNotBlank(userId),"ht_user_id",userId)
                        .eq("del_status",0)
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (idList == null || idList.isEmpty()) {
            return true;
        }
        for (Serializable serializable : idList) {
            if(!transformerDao.removeById(serializable)){
                throw new RRException("操作失败！");
            }
        }
        return true;
    }

    @Override
    public TransformerEntity getById(Serializable id) {

        TransformerEntity transformerEntity = this.baseMapper.getById(id);
        return transformerEntity;
    }

    @Override
    public boolean removeByHtUserId(String userId) {
        if(userId == null)
            return true;
        return this.baseMapper.removeByUserId(userId);
    }

    @Override
    public boolean insertList(List<TransformerEntity> list) {
        if(list == null)
            return true;
        return this.baseMapper.insetList(list);
    }
}
