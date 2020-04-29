package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.exception.RRException;
import io.renren.dao.MalfunctionDao;
import io.renren.dto.FaultReminderDto;
import io.renren.dto.MalfunctionDto;
import io.renren.entity.MalfunctionEntity;
import io.renren.service.MalfunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author JunCheng He
 */
@Service("malfunctionService")
public class MalfunctionServiceImpl extends ServiceImpl<MalfunctionDao, MalfunctionEntity> implements MalfunctionService {

    @Override
    public MalfunctionEntity getDefectLevelById(long id) {

        MalfunctionEntity malfunctionEntity = null;
        malfunctionEntity = this.baseMapper.getDefectLevelById(id);
        if (malfunctionEntity == null) {
            throw new RRException("没有找到故障id[" + id + "]对应的故障等级");
        }
        return malfunctionEntity;
    }

    /**
     * 获取所有缺陷
     *
     * @param pageNum
     * @param pageSize
     * @param processingStatus
     * @param defectLevel
     * @param userId
     * @return
     */
    @Override
    public PageInfo<MalfunctionDto> pageList(Integer pageNum, Integer pageSize, String processingStatus, String defectLevel, Long userId) {
        PageInfo<MalfunctionDto> page = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> this.baseMapper.pageList(defectLevel, processingStatus, userId));
        return page;
    }

    /**
     * 故障提醒
     *
     * @param id
     * @return
     */
    @Override
    public FaultReminderDto selectByMalfunctionId(Long id) {

        return this.baseMapper.selectByMalfunctionId(id);
    }

    @Override
    public MalfunctionDto getDetailById(Long id) {
        if(id == null)
            return null;
        MalfunctionDto detailById = this.baseMapper.getDetailById(id);
        //根据表箱id查询该表箱的负责人
        detailById.setUsername(this.baseMapper.getUserNameById(detailById.getHtUserId()));
        return detailById;
    }

    @Override
    public MalfunctionDto detailsByUserId(String id) {
        if(id == null)
            return null;
        MalfunctionDto detailByhtUserId = this.baseMapper.getDetailByhtUserId(id);
        //根据表箱id得到表箱的负责人
        detailByhtUserId.setUsername(this.baseMapper.getUserNameById(id));
        return detailByhtUserId;
    }
}
