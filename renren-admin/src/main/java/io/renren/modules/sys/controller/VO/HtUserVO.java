package io.renren.modules.sys.controller.VO;

import io.renren.modules.sys.entity.HtUserEntity;
import lombok.Data;

@Data
public class HtUserVO extends HtUserEntity {

    /**
     * checkUser 检查人员
     */
    private String checkUserName;
}
