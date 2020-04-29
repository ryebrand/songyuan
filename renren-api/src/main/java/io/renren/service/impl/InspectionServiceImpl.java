package io.renren.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.controller.VO.HtUserVO;
import io.renren.dao.InspectionDao;
import io.renren.dao.MalfunctionDao;
import io.renren.dto.InspectionPageListDto;
import io.renren.entity.DefectNameRecordEntity;
import io.renren.entity.InspectionEntity;
import io.renren.entity.MalfunctionEntity;
import io.renren.entity.MeterDefectRecordEntity;
import io.renren.service.*;
import io.renren.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.renren.controller.ApiMyWebSocket.sendInfo;


/**
 * @author JunCheng He
 */
@Service("inspectionService")
public class InspectionServiceImpl extends ServiceImpl<InspectionDao, InspectionEntity> implements InspectionService {

    @Autowired
    private DefectNameRecordService defectNameRecordService;

    @Value("${apiupload.path}")
    private String path;

    @Autowired
    private MeterDefectRecordService meterDefectRecordService;

    @Autowired
    private MalfunctionService malfunctionService;

    @Autowired
    private MeterBoxService meterBoxService;


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
     * 分页查询巡检记录
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param userId     用户id
     * @param meterBoxId 表箱id
     * @return
     */
    @Override
    public PageInfo<InspectionPageListDto> queryPageList(Integer pageNum, Integer pageSize, Long userId, Long meterBoxId) {
        if (userId == null || meterBoxId == null) {
            throw new RRException("用户id或表箱id不能为空！");
        }
        PageInfo<InspectionPageListDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> this.baseMapper.queryPageList(userId, meterBoxId));
        return page;
    }

    /**
     * 添加巡检记录
     *
     * @param malfunctionEntity 缺陷表对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(MalfunctionEntity malfunctionEntity, MultipartFile[] file) {
        String faultDescription = "";
        if (malfunctionEntity.getDefectArray().length != 0) {
            Integer count = 1;
            for (Long aLong : malfunctionEntity.getDefectArray()) {
                //if (aLong < 16L) {
                //    continue;
                //}
                DefectNameRecordEntity defectNameRecordEntity = defectNameRecordService.getOne(new QueryWrapper<DefectNameRecordEntity>().eq("defect_id", aLong));
                faultDescription += ("    \r\n  ") + (count) + ("、") + (defectNameRecordEntity.getName());
                count++;
            }
        }
        malfunctionEntity.setFaultDescription(faultDescription == null ? "无" : faultDescription + "\r\n");
        malfunctionEntity.setType(0);
        malfunctionEntity.setDeleteStatus(0);
        malfunctionEntity.setProcessingStatus(0);
        Date date = new Date();
        Date createDate = date;
        malfunctionEntity.setCreateTime(createDate);
        StringBuilder fileNameSet = new StringBuilder();
        if (file.length > 0 && file != null) {
            for (MultipartFile multipartFile : file) {
                fileNameSet.append("image/巡检图片/").append(FileUpload.upload(multipartFile, path + "巡检图片/", "" + System.currentTimeMillis())).append(",");
            }
            malfunctionEntity.setImage(fileNameSet.toString());
        }
        malfunctionEntity.setProcessingStatus(0);
        //缺陷表添加
        malfunctionService.save(malfunctionEntity);

        InspectionEntity inspectionEntity = new InspectionEntity();
        inspectionEntity.setHtUserId(malfunctionEntity.getHtUserId());
        inspectionEntity.setCreateTime(createDate);
        inspectionEntity.setDeleteStatus(0);
        inspectionEntity.setMalfunctionId(malfunctionEntity.getId());
        inspectionEntity.setUserId(malfunctionEntity.getUserId());
        //巡检表添加
        this.save(inspectionEntity);

        List<MeterDefectRecordEntity> meterDefectRecordEntities = new ArrayList<>();
        int len = malfunctionEntity.getDefectArray().length;
        for (int j = 0; j < len; j++) {
            MeterDefectRecordEntity meterDefectRecordEntity = new MeterDefectRecordEntity();
            meterDefectRecordEntity.setHtUserId(malfunctionEntity.getHtUserId());
            meterDefectRecordEntity.setCreatetime(createDate);
            meterDefectRecordEntity.setDefectId(malfunctionEntity.getDefectArray()[j]);
            meterDefectRecordEntities.add(meterDefectRecordEntity);
        }
        //统计记录表
        meterDefectRecordService.saveBatch(meterDefectRecordEntities);




        //更新电箱表上次检查日期
        HtUserVO htUserVO = meterBoxService.getMeterBoxAssetNumberById(malfunctionEntity.getHtUserId());
        //Date转为LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
        //利用LocalDate + 检查周期
        LocalDate dateAfter = localDate.plusMonths(htUserVO.getCheckCycle());
        //将下次检查日期从LocalDate转为Date
        Date timeAfter = Date.from(dateAfter.atStartOfDay(ZoneId.systemDefault()).toInstant());
        htUserVO.setCheckDateBefore(date);
        htUserVO.setCheckDateAfter(timeAfter);
        meterBoxService.updateByHtUserId(htUserVO);

        sendInfo(JSON.toJSONString(R.ok().put("data", malfunctionService.selectByMalfunctionId(malfunctionEntity.getId()))));

        return true;
    }
}
