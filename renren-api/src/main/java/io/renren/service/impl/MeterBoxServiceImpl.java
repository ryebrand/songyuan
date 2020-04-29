package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.renren.common.exception.RRException;
import io.renren.controller.ApiLoginController;
import io.renren.controller.VO.HtUserVO;
import io.renren.dao.MeterBoxDao;
import io.renren.dto.MeterBoxInfoDto;
import io.renren.dto.MeterBoxSearchDto;
import io.renren.dto.RecentInspeBoxDto;
import io.renren.entity.MeterBoxEntity;
import io.renren.entity.UserEntity;
import io.renren.service.MeterBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author JunCheng He
 */
@Service("meterBoxService")
public class MeterBoxServiceImpl extends ServiceImpl<MeterBoxDao, MeterBoxEntity> implements MeterBoxService {


    @Override
    public HtUserVO getMeterBoxAssetNumberById(String id) {
        HtUserVO assetNumberById = this.baseMapper.getMeterBoxAssetNumberById(id);
        if (assetNumberById == null) {
            throw new RRException("找不到电表箱id为[" + id + "]对应的电表箱设备编号");
        }
        return assetNumberById;
    }

    /**
     * 更新高压用户信息
     * @param htUserVO
     * @return
     */
    @Override
    public boolean updateByHtUserId(HtUserVO htUserVO) {
        if (htUserVO == null) {
            return true;
        }
        return this.baseMapper.updteByUserId(htUserVO);
    }

    /**
     * 根据电表箱资产号查询电表箱详情
     *
     * @param meterBoxId 电表箱资产号
     * @return
     */
    @Override
    public HtUserVO selectByMeterBoxAssetNumber(String meterBoxId) {
        if (meterBoxId == null) {
            throw new RRException("表箱资产号不能为空！");
        }
        return this.baseMapper.selectByMeterBoxAssetNumber(meterBoxId);
    }

    /**
     * 分页查询
     *
     * @param userId
     * @return
     */
    @Override
    public PageInfo<HtUserVO> selectByUserId(Integer pageNum, Integer pageSize, Long userId) {
        if (userId == null) {
            throw new RRException("用户id不能为空！");
        }
        PageInfo<HtUserVO> page = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> this.baseMapper.selectByUserId(userId));
        return page;
    }

    /**
     * 根据资产号或地址模糊查询电表箱
     *
     * @param pageNum
     * @param pageSize
     * @param assetOrAddress
     * @return
     */
    @Override
    public PageInfo<HtUserVO> selectByAssetNumOrAddress(Integer pageNum, Integer pageSize, String assetOrAddress,long loginUser) {
        //UserEntity loginUser = ApiLoginController.loginUser;
        //admin id为1
        PageInfo<HtUserVO> page = PageHelper.startPage(pageNum, pageSize)
                    .doSelectPageInfo(() -> this.baseMapper.searchByAssetNumberOrAdderss(assetOrAddress,loginUser));

        return page;
    }

}
