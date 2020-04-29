package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.HtUserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

/**
 * 
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2020-04-18 14:11:13
 */
public interface HtUserService extends IService<HtUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Page<HtUserEntity> pageList(Map<String, Object> params);

    @Override
    HtUserEntity getById(Serializable id);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);

    boolean updateByIdAndOwnerId(String id, Long ownerId);

    boolean batchImport(String fileName, MultipartFile file) throws IOException, ParseException;

    @Override
    boolean save(HtUserEntity entity);

    @Override
    boolean updateById(HtUserEntity entity);
}

