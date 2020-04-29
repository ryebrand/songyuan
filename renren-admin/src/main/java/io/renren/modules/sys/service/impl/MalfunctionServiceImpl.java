package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.modules.sys.dto.DefectLevelGroupDto;
import io.renren.modules.sys.dto.FaultReminderDto;
import io.renren.modules.sys.dto.MalfunctionInfoDto;
import io.renren.modules.sys.dto.MalfunctionStatics;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MalfunctionDao;
import io.renren.modules.sys.entity.MalfunctionEntity;
import io.renren.modules.sys.service.MalfunctionService;


/**
 * @author JunCheng He
 */
@Service("malfunctionService")
public class MalfunctionServiceImpl extends ServiceImpl<MalfunctionDao, MalfunctionEntity> implements MalfunctionService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MalfunctionEntity> page = this.page(
                new Query<MalfunctionEntity>().getPage(params),
                new QueryWrapper<MalfunctionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public MalfunctionEntity getMalfunctionById(long id) {
        MalfunctionEntity malfunctionEntity = null;
        malfunctionEntity = this.baseMapper.getMalfunctionById(id);
        if (malfunctionEntity == null) {
            throw new RRException("未找到对应的数据");
        }
        return malfunctionEntity;
    }

    @Override
    public MalfunctionEntity getDefectLevelById(long id) {

        MalfunctionEntity malfunctionEntity = null;
        malfunctionEntity = this.baseMapper.getDefectLevelById(id);
        if (malfunctionEntity == null) {
            throw new RRException("没有找到故障id[" + id + "]对应的故障等级");
        }
        return malfunctionEntity;
    }

    @Override
    public int getCountByCondition(int type, int defectLevel, int processingStatus) {
        int count = 0;
        count = this.baseMapper.getCountByCondition(type, defectLevel, processingStatus);
        return count;
    }

    /**
     * 分页查找缺陷记录
     *
     * @param params
     * @return
     */
    @Override
    public Page<MalfunctionInfoDto> pageList(Map<String, Object> params) {
        String createTime = (String) params.get("createTime");
        String start = "";
        String end = "";

        String type = (String) params.get("type");
        String level = (String) params.get("level");
        String status = (String) params.get("status");

        String[] types = null;
        String[] levels = null;
        String[] statuses = null;
        if (StringUtils.isNotBlank(type)) {
            types = type.split(",");
        }
        if (StringUtils.isNotBlank(level)) {
            levels = level.split(",");
        }
        if (StringUtils.isNotBlank(status)) {
            statuses = status.split(",");
        }

        if (createTime != null && "".equals(createTime)) {
            start = createTime.substring(0, 19);
            end = createTime.substring(22, 41);
        }


        //if (createTime != null && !"".equals(createTime)) {
        //    start = createTime.substring(0, 19);
        //    //如果结束时间为2020-04-23 00:00:00 优化成 2020-04-23 23:59:59
        //    if(createTime.substring(33).equals("00:00:00")){
        //        StrBuilder sb = new StrBuilder();
        //        sb.append(createTime.substring(22,33));
        //        sb.append("23:59:59");
        //        end = sb.toString();
        //    } else {
        //        end = createTime.substring(22, 41);
        //    }
        //}

        Page<MalfunctionInfoDto> page = new Page<>(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        page.setRecords(this.baseMapper.pageList(page, start, end, types, levels, statuses));
        return page;
    }

    /**
     * 查询详情
     *
     * @param id
     * @return
     */
    @Override
    public MalfunctionInfoDto selectByMalfunctionId(Long id) {
        return this.baseMapper.selectByMalfunctionId(id);
    }

    /**
     * 根据id更新缺陷处理状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean updateByMalfuctionId(Long id, Integer status) {
        this.update(new UpdateWrapper<MalfunctionEntity>().eq("id", id).set("processing_status", status));
        return true;
    }

    /**
     * 根据不同处理状态统计数目
     *
     * @param processingStatus
     * @return
     */
    @Override
    public List<MalfunctionStatics> countByProcessingStatus(Integer processingStatus) {
        return this.baseMapper.countByProcessingStatus(processingStatus);
    }

    /**
     * 获取最新6条 故障记录
     */
    @Override
    public List<FaultReminderDto> selectTopSix() {
        return this.baseMapper.selectTopSix();
    }

    @Override
    public int getDefectLevelByTime(@Param("defectLevel") int defectLevel, @Param("taiAreaName") String taiAreaName, @Param("powerSupply") String powerSupply, @Param("startTime") String startTime, @Param("endTime") String endTime) {
        int count = 0;
        count = this.baseMapper.getDefectLevelByTime(defectLevel, taiAreaName, powerSupply, startTime, endTime);
        return count;
    }

    @Override
    public int getProcessingStatus(@Param("processingStatus") int processingStatus, @Param("taiAreaName") String taiAreaName, @Param("powerSupply") String powerSupply, @Param("startTime") String startTime, @Param("endTime") String endTime) {
        int count = 0;
        count = this.baseMapper.getProcessingStatus(processingStatus, taiAreaName, powerSupply, startTime, endTime);
        return count;
    }

    /**
     * 根据缺陷级别统计
     *
     * @return
     */
    @Override
    public List<DefectLevelGroupDto> groupByDefectLevel() {
        return this.baseMapper.groupByDefectLevel();
    }

    @Override
    public int count(Wrapper<MalfunctionEntity> queryWrapper) {
        return super.count(queryWrapper);
    }


    @Override
    public int getCount(long userId) {
        return this.baseMapper.getCountByUser(userId);
    }
}
