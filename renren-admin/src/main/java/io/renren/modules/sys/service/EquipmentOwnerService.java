package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.EquipmentOwnerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
public interface EquipmentOwnerService extends IService<EquipmentOwnerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据id获取设备主人信息
     *
     * @param id
     * @return
     */
    EquipmentOwnerEntity getOwnerById(long id);

    /**
     * 根据id获取设备主人姓名
     *
     * @param id
     * @return
     */
    EquipmentOwnerEntity getOwnerByMeterBoxId(long id);

    /**
     * 根据手机号码查询责任人ID
     *
     * @param phoneNumber
     * @return
     */
    Integer selectByPhoneNumber(String phoneNumber);

}

