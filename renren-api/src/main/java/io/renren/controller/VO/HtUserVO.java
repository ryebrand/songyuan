package io.renren.controller.VO;

import io.renren.entity.HtUserEntity;
import lombok.Data;

@Data
public class HtUserVO extends HtUserEntity {

    /**
     * checkUser 检查人员
     */
    private String checkUserName;
    /**
     * 检查人员电话
     */
    private String phoneNumber;
}
