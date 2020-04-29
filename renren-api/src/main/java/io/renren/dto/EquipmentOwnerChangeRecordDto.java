package io.renren.dto;

import io.renren.entity.EquipmentOwnerChangeRecordEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 根据表箱id查询设备主人变更记录DTO
 *
 * @author JunCheng He
 * @title: EquipmentOwnerChangeRecordDto
 * @projectName project
 * @description: TODO
 * @date 2019/7/27  17:16
 */
@Data
public class EquipmentOwnerChangeRecordDto extends EquipmentOwnerChangeRecordEntity implements Serializable {
    private static final long serialVersionUID = -2416840205608357541L;
    /**
     * 名字
     */
    private String username;
    /**
     * 电话号码
     */
    private String mobile;
}
