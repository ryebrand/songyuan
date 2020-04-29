package io.renren.controller.VO;

import lombok.Data;

import java.util.Date;

@Data
public class HtUserVTO extends HtUserVO {

    /**
     * 故障id
     */
    private long malfunctionId;
    /**
     * 用户id
     */
    //private long userId;
    /**
     * 巡检时间
     */
    private Date creatTime;
    /**
     * 删除状态
     */
    private int deleteStatus;

}
