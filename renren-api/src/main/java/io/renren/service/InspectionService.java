package io.renren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import io.renren.dto.InspectionPageListDto;
import io.renren.dto.RecentInspeBoxDto;
import io.renren.entity.InspectionEntity;
import io.renren.entity.MalfunctionEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 巡检表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-18 15:17:18
 */
public interface InspectionService extends IService<InspectionEntity> {

    /**
     * 分页查询巡检记录
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param userId     用户id
     * @param meterBoxId 表箱id
     * @return
     */
    PageInfo<InspectionPageListDto> queryPageList(Integer pageNum, Integer pageSize, Long userId, Long meterBoxId);

    /**
     * 添加巡检记录
     *
     * @param malfunctionEntity 缺陷表对象
     * @param file              文件数组
     * @return
     */
    Boolean save(MalfunctionEntity malfunctionEntity, MultipartFile[] file);

    /**
     * 根据用户编码，指定从pageSize*(page-1)获取pageSize数量的缺陷列表数据
     *
     * @param userid
     * @param page
     * @param pageSize
     * @return
     */
    List<InspectionEntity> getInspectionList(long userid, int page, int pageSize);

}

