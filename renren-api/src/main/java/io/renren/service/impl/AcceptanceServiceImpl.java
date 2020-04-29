package io.renren.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.dao.AcceptanceDao;
import io.renren.dto.AcceptanceBoxListDto;
import io.renren.dto.AcceptanceDto;
import io.renren.dto.AcceptanceSubmitDto;
import io.renren.entity.AcceptanceEntity;
import io.renren.entity.DefectNameRecordEntity;
import io.renren.entity.MalfunctionEntity;
import io.renren.entity.MeterDefectRecordEntity;
import io.renren.service.AcceptanceService;
import io.renren.service.DefectNameRecordService;
import io.renren.service.MalfunctionService;
import io.renren.service.MeterDefectRecordService;
import io.renren.util.FileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.renren.controller.ApiMyWebSocket.sendInfo;


/**
 * @author JunCheng He
 */
@Service("acceptanceService")
public class AcceptanceServiceImpl extends ServiceImpl<AcceptanceDao, AcceptanceEntity> implements AcceptanceService {
    @Value("${apiupload.path}")
    private String path;
    /**
     * 存在缺陷
     */
    private static final Integer ACCEPTANCE_STATUS_DEFECT = 1;
    /**
     * 完成验收
     */
    private static final Integer ACCEPTANCE_STATUS_CARRYOUT = 2;

    @Autowired
    private MalfunctionService malfunctionService;

    @Autowired
    private DefectNameRecordService defectNameRecordService;

    @Autowired
    private MeterDefectRecordService meterDefectRecordService;

    /**
     * 根据资产号更新验收状态
     *
     * @return
     */
    @Override
    public Boolean updateByAssetNumber(Long malfunctionId, MultipartFile[] file) {
        if (malfunctionId == null) {
            return true;
        }
        MalfunctionEntity malfunctionEntity = new MalfunctionEntity();
        malfunctionEntity.setId(malfunctionId);

        StringBuilder fileNameSet = new StringBuilder();
        if (file != null || file.length != 0) {
            for (MultipartFile multipartFile : file) {
                fileNameSet.append("image/验收/").append(FileUpload.upload(multipartFile, path + "验收/", "" + System.currentTimeMillis())).append(",");
            }
            malfunctionEntity.setAcceptImage(fileNameSet.toString());
        }
        malfunctionEntity.setProcessingStatus(1);
        malfunctionEntity.setCreateTime(new Date());
        malfunctionService.updateById(malfunctionEntity);

        return true;
    }


    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByAssetNumber(AcceptanceSubmitDto acceptanceSubmitDto) {
        MalfunctionEntity malfunctionEntity = JSON.parseObject(acceptanceSubmitDto.getParams(), MalfunctionEntity.class);
        if (ACCEPTANCE_STATUS_DEFECT.equals(acceptanceSubmitDto.getType())) {
            String faultDescription = "";
            if (malfunctionEntity.getDefectArray().length != 0) {
                Integer count = 1;
                for (Long aLong : malfunctionEntity.getDefectArray()) {
                    if (aLong < 16L) {
                        continue;
                    }
                    DefectNameRecordEntity defectNameRecordEntity = defectNameRecordService.getOne(new QueryWrapper<DefectNameRecordEntity>().eq("defect_id", aLong));
                    faultDescription += ("    \r\n  ") + (count) + ("、") + (defectNameRecordEntity.getName());
                    count++;
                }
            }
            malfunctionEntity.setFaultDescription(faultDescription + "\r\n");
            malfunctionEntity.setType(1);
            malfunctionEntity.setDeleteStatus(0);
            malfunctionEntity.setProcessingStatus(0);
            Date createDate = new Date();
            malfunctionEntity.setCreateTime(createDate);

            StringBuilder fileNameSet = new StringBuilder();
            if (acceptanceSubmitDto.getFile() != null || acceptanceSubmitDto.getFile().length != 0) {
                for (MultipartFile multipartFile : acceptanceSubmitDto.getFile()) {
                    fileNameSet.append("image/验收/").append(FileUpload.upload(multipartFile, path + "验收/", "" + System.currentTimeMillis())).append(",");
                }
                malfunctionEntity.setImage(fileNameSet.toString());
            }

            if (malfunctionEntity.getId() != null) {
                malfunctionService.removeById(malfunctionEntity.getId());
            }

            //缺陷表添加
            malfunctionService.save(malfunctionEntity);

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

            this.update(new UpdateWrapper<AcceptanceEntity>()
                    .eq("asset_number", malfunctionEntity.getAssetsCode())
                    .set("step", ACCEPTANCE_STATUS_DEFECT)
                    .set("malfunction_id", malfunctionEntity.getId())
                    .set("user_id", malfunctionEntity.getUserId())
                    .set("defect_description", faultDescription));

            sendInfo(JSON.toJSONString(R.ok().put("data", malfunctionService.selectByMalfunctionId(malfunctionEntity.getId()))));

        } else if (ACCEPTANCE_STATUS_CARRYOUT.equals(acceptanceSubmitDto.getType())) {
            this.update(new UpdateWrapper<AcceptanceEntity>()
                    .eq("asset_number", malfunctionEntity.getAssetsCode())
                    .set("step", ACCEPTANCE_STATUS_CARRYOUT)
                    .set("user_id", malfunctionEntity.getUserId())
                    .set("completion_date", new Date()));
        }
        return true;
    }*/


    /**
     * 根据资产号查询验收信息
     *
     * @param meterBoxAssetNumber
     * @return
     */
    @Override
    public AcceptanceDto selectByAssetNumber(String meterBoxAssetNumber, Long userId) {
        if (StringUtils.isBlank(meterBoxAssetNumber)) {
            throw new RRException("表箱资产号不能为空！");
        }
        return this.baseMapper.selectByAssetNumber(meterBoxAssetNumber, userId);
    }

    /**
     * 用户id查询验收列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<AcceptanceBoxListDto> selectByUserId(Long userId, String acceptanceType, String step) {
        if (userId == null) {
            throw new RRException("用户id不能为空！");
        }
        return this.baseMapper.selectByUserId(userId, acceptanceType, step);
    }
}
