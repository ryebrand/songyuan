package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.TransformerEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 13:44:48
 */
public interface TransformerService extends IService<TransformerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);

    @Override
    TransformerEntity getById(Serializable id);

    boolean removeByHtUserId(String userId);

    boolean insertList(List<TransformerEntity> list);
}

