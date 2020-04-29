package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.sys.controller.SysLogController;
import io.renren.modules.sys.controller.SysLoginController;
import io.renren.modules.sys.dto.CreateTimeGroupDto;
import io.renren.modules.sys.dto.InspectionDto;
import io.renren.common.exception.RRException;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.InspectionDao;
import io.renren.modules.sys.entity.InspectionEntity;
import io.renren.modules.sys.service.InspectionService;


/**
 * @author JunCheng He
 */
@Service("inspectionService")
public class InspectionServiceImpl extends ServiceImpl<InspectionDao, InspectionEntity> implements InspectionService {
//    @Autowired
//    private InspectionDao inspectionDao;

    @Override
    public List<InspectionEntity> getInspectionList(long userid, int page, int pageSize) {
        List<InspectionEntity> list = null;
        list = this.baseMapper.getInspectionList(userid, page, pageSize);
        if (list == null) {
            throw new RRException("缺陷列表获取异常");
        }
        return list;
    }

    /**
     * 统计本月巡检次数
     *
     * @return
     */
    @Override
    public Integer countByNowMonth() {
        return this.baseMapper.countByNowMonth();
    }

    @Override
    public int getInspectedMeterCountByCondition(String startTime, String endTime, String taiAreaName, String powerSupply) {
        int count = 0;
        count = this.baseMapper.getInspectedMeterCountByCondition(startTime, endTime, taiAreaName, powerSupply);
        return count;
    }

    /**
     * 根据最近几天时间统计巡检次数
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<CreateTimeGroupDto> groupByCreateTime(String start, String end) {
        return this.baseMapper.groupByCreateTime(start, end);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<InspectionEntity> page = this.page(
                new Query<InspectionEntity>().getPage(params),
                new QueryWrapper<InspectionEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 分页查询巡检记录
     *
     * @param params
     * @return
     */
    @Override
    public Page<InspectionDto> pageList(Map<String, Object> params) {

        String createTime = (String) params.get("timeRange");
        String start = "";
        String end = "";
        String boxId = null;

        if (createTime != null && "".equals(createTime)) {
            start = createTime.substring(0, 19);
            end = createTime.substring(22, 41);
        }

        //if (createTime != null && !"".equals(createTime)) {
        //    start = createTime.substring(0, 19);
        //    if(createTime.substring(33).equals("00:00:00")){
        //        StrBuilder sb = new StrBuilder();
        //        sb.append(createTime.substring(22,33));
        //        sb.append("23:59:59");
        //        end = sb.toString();
        //    } else {
        //        end = createTime.substring(22, 41);
        //    }
        //}
        boxId = (String) params.get("boxId");
        Page<InspectionDto> page = new Page<>(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        page.setRecords(this.baseMapper.selectByCreateTimeAndOwnerName(page, start, end, boxId));
        return page;
    }

}
