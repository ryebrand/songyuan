package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.EquipmentOwnerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备责任人表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@Mapper
public interface EquipmentOwnerDao extends BaseMapper<EquipmentOwnerEntity> {
    /**
     * 根据id获取设备主人信息
     *
     * @param id
     * @return
     */
    public EquipmentOwnerEntity getOwnerById(long id);

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
    Integer selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    Long getOwnerByName(String name);

    int getCount();

    List<UserEntity> list();
}
