package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.common.exception.RRExceptionHandler;
import io.renren.modules.sys.controller.SysLoginController;
import io.renren.modules.sys.controller.SysUserController;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.dao.EquipmentOwnerDao;
import io.renren.modules.sys.dto.MeterBoxDto;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.TransformerService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.HtUserDao;
import io.renren.modules.sys.service.HtUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service("htUserService")
public class HtUserServiceImpl extends ServiceImpl<HtUserDao, HtUserEntity> implements HtUserService {

    @Autowired
    private HtUserDao htUserDao;
    @Autowired
    private TransformerService transformerService;
    @Autowired
    private EquipmentOwnerDao equipmentOwnerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String assetsCode = (String) params.get("assetsCode");
        String checkUser = (String) params.get("checkUser");
        IPage<HtUserEntity> page = this.page(
                new Query<HtUserEntity>().getPage(params),
                new QueryWrapper<HtUserEntity>().eq("del_status", 0)
                .eq(StringUtils.isNotBlank(assetsCode),"assets_code",assetsCode)
                .eq(StringUtils.isNotBlank(checkUser),"check_user",checkUser)
        );

        return new PageUtils(page);
    }

    /**
     * 分页查找
     *
     * @param params
     * @return
     */
    @Override
    public Page<HtUserEntity> pageList(Map<String, Object> params) {
        Page<HtUserEntity> page = new Page<>(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        page.setRecords(this.baseMapper.listByAssetcodeAndUser(page, (String) params.get("htUserId"),(String) params.get("address"),(String) params.get("checkUserName")));
        return page;
    }

    @Override
    public HtUserEntity getById(Serializable id) {
        return this.baseMapper.getById(id);
    }

    @Override
    public boolean save(HtUserEntity htUser) {
        if(htUser == null) {
            return true;
        }
        //格式化日期，设置下次检查时间
        Date timeBefore = htUser.getCheckDateBefore();

        //Date转为LocalDate
        LocalDate localDate = timeBefore.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
        //利用LocalDate + 检查周期
        LocalDate dateAfter = localDate.plusMonths(htUser.getCheckCycle());
        //将下次检查日期从LocalDate转为Date
        Date timeAfter = Date.from(dateAfter.atStartOfDay(ZoneId.systemDefault()).toInstant());
        htUser.setCheckDateBefore(timeBefore);
        htUser.setCheckDateAfter(timeAfter);
        return super.save(htUser);
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(HtUserEntity htUser) {
        if (htUser == null) {
            return true;
        }
        //格式化日期，设置下次检查时间
        Date timeBefore = htUser.getCheckDateBefore();

        //Date转为LocalDate
        LocalDate localDate = timeBefore.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
        //利用LocalDate + 检查周期
        LocalDate dateAfter = localDate.plusMonths(htUser.getCheckCycle());
        //将下次检查日期从LocalDate转为Date
        Date timeAfter = Date.from(dateAfter.atStartOfDay(ZoneId.systemDefault()).toInstant());
        htUser.setCheckDateBefore(timeBefore);
        htUser.setCheckDateAfter(timeAfter);

        return this.baseMapper.updateHtuserById(htUser);
    }

    /**
     * 删除
     * @param idList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        if (idList == null || idList.isEmpty()) {
            return true;
        }

        for (Serializable serializable : idList) {
            //逻辑删除，更新删除状态字段
            boolean b = htUserDao.removeById(serializable.toString());
            if(!b)
                throw new RRException("操作失败");
        }
        return true;
    }

    /**
     * 更改责任人
     * @param id
     * @param ownerId
     * @return
     */
    @Override
    public boolean updateByIdAndOwnerId(String id, Long ownerId) {
        this.update(new UpdateWrapper<HtUserEntity>().eq("user_id", id).set("check_user", ownerId));
        return true;
    }

    /**
     * 导入文件
     * @param fileName
     * @param file
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchImport(String fileName, MultipartFile file) throws IOException {
        boolean notNull = false;
        List<MeterBoxEntity> meterBoxEntities = new ArrayList<>();
        List<MeterEntity> meterEntities = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new RRException("导入文件格式不正确！");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet == null) {
            throw new RRException("表格为空");
            //return true;
        }

        notNull = true;
        HtUserEntity htUserEntity;
        TransformerEntity transformerEntity;



        //获得当前sheet的开始行
        int firstRowNum  = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if (lastRowNum == -1 || lastRowNum == 0) {
            throw new RRException("表格为空");
        }

        String htUserId = null;
        String htUserIdTemp = null;
        HtUserEntity htUserTemp = null;
        List<TransformerEntity> list = new ArrayList<>();
        //获取系统中所有存在的责任人
        int count = equipmentOwnerDao.getCount();
        List<UserEntity> userEntities = equipmentOwnerDao.list();
        Map<String,Long> userMap = new HashMap<>(count);

        for (UserEntity userEntity : userEntities) {
            userMap.put(userEntity.getUsername(),userEntity.getUserId());
        }

        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int r = firstRowNum+1; r <= lastRowNum; r++) {

            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

            Cell cell = null;

            htUserEntity = new HtUserEntity();
            transformerEntity = new TransformerEntity();

            //  第一列 高压用户编号
            if ((cell = row.getCell(0)) == null) {
                throw new RRException("导入失败(第" + (r + 1) + "行,用户编号为空或长度不为10)");
            }
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (cell.getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,用户编号请设为文本格式)");
            }
            htUserId = cell.getStringCellValue().trim();
            if (StringUtils.isBlank(htUserId) || htUserId.length() != 10) {
                throw new RRException("导入失败(第" + (r + 1) + "行,用户编号为空或长度不为10)");
            }


            /**
             * 电表相关信息
             */
            String transformCode = null;
            String transformCategory = null;
            String voltageVar = null;
            String currentVal = null;
            //第十一列 互感器条码
            if((cell = row.getCell(10)) != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,互感器条码请设为文本格式)");
                }
                transformCode = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(transformCode)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,互感器条码为空)");
                }
            }

            //第十二列 互感器类别
            if((cell = row.getCell(11)) != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,互感器类别请设为文本格式)");
                }
                transformCategory = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(transformCategory)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,互感器类别为空)");
                }
            }

            boolean transformValue = false;
            //第十三列 电压互感器变比
            if((cell = row.getCell(12)) != null) {

                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,电压互感器变比请设为文本格式)");
                }
                voltageVar = cell.getStringCellValue().trim();
                if (StringUtils.isNotBlank(voltageVar)) {
                    transformValue = true;
                }
            }

            //第十四列 电流互感器变比
            if((cell = row.getCell(13)) != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,电流互感器变比请设为文本格式)");
                }
                currentVal = cell.getStringCellValue().trim();

                if (transformValue && StringUtils.isNotBlank(currentVal)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,互感器不能同时有电流和电压变比值)");
                }
                if (StringUtils.isBlank(currentVal) && !transformValue) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,不能同时有电流和电压变比值)");
                }
            }

            transformerEntity.setTransformCode(transformCode);
            transformerEntity.setTransformCategory(transformCategory);
            transformerEntity.setVoltageVar(voltageVar);
            transformerEntity.setCurrentVal(currentVal);
            transformerEntity.setHtUserId(htUserId);


            /**
             * 当前行与换上一行是否相同
             * 如果相同 则表箱信息不管 往list中放入电表信息
             * 如果不同 放入上一个表箱和电表的所有信息 同时清空list
             */
            if (htUserIdTemp == null || !htUserId.equals(htUserIdTemp)) {
                //1.判断表箱htUserIdTemp和电表list是否为空 如果非空 加入这些信息 如果非空且存在该表箱 直接删除
                if (htUserIdTemp != null) {
                    HtUserEntity byId = this.getById(htUserIdTemp);
                    if (byId != null) {
                        this.baseMapper.removeByHtuserId(htUserIdTemp);
                        transformerService.removeByHtUserId(htUserIdTemp);
                    }
                    //插入表箱
                    this.save(htUserTemp);
                    //插入电表
                    if (list != null && list.size() > 0) {
                        transformerService.insertList(list);
                        list.clear();
                    }
                }
                //2.拿到所有表箱信息 同时往list中加入电表
                // 第二列 高压用户名
                if ((cell = row.getCell(1)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用户名为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用户名请设为文本格式)");
                }
                String userName = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(userName)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用户名为空)");
                }

                // 第三列 用电类别
                if ((cell = row.getCell(2)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电类别为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电类别请设为文本格式)");
                }
                String elecCategory = row.getCell(2).getStringCellValue().trim();
                if (StringUtils.isBlank(elecCategory)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电类别为空)");
                }

                // 第四列  用户分类
                if ((cell = row.getCell(3)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电类别为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行, 用户分类请设为文本格式)");
                }
                String userCategory = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(userCategory)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用户分类为空)");
                }

                // 第五列  负荷性质
                if((cell = row.getCell(4)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,负荷性质为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行, 负荷性质请设为文本格式)");
                }
                String loadProperty = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(loadProperty)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,负荷性质为空)");
                }

                // 第六列  用电地址信息
                if((cell = row.getCell(5)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电地址信息为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行, 用电地址信息请设为文本格式)");
                }
                String elecAddress = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(elecAddress)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,用电地址信息为空)");
                }

                //  第七列 检察人员
                if((cell = row.getCell(6)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检察人员为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检察人员请设为文本格式)");
                }
                String name = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(name)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检察人员为空)");
                }

                //根据检察人员姓名查询检查人员id
                Long checkUser = userMap.get(name);
                if (checkUser == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,系统不存在该检察人员，请先添加该检查人员)");
                }

                //第八列 检查周期
                if((cell = row.getCell(7)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检查周期只能为3、6、9、12)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检查周期请设为整数)");
                }
                int checkCycle = 0;
                try {
                    checkCycle = Integer.parseInt(cell.getStringCellValue().toString().trim());
                } catch (Exception e) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,检查周期请设为整数)");
                }
                if(checkCycle != 3 && checkCycle != 6 && checkCycle != 12 && checkCycle != 36){
                    throw new RRException("导入失败(第" + (r + 1) + "行,检查周期只能为3、6、12、36)");
                }

                //第九列 上次检查日期
                if((cell = row.getCell(8)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,上次检查日期为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,上次检查日期请输入正确格式)");
                }
                String checkDateBefore = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(checkDateBefore)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,上次检查日期为空)");
                }
                Date timeBefore = null;
            /*//格式化日期


            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

                timeBefore = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());

                //String value = sdf.format(date);
                //
                //System.out.println(value);
            }*/

                try {
                    timeBefore = new SimpleDateFormat("yyyy/MM/dd").parse(checkDateBefore);
                } catch (ParseException e) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,日期格式非法)");
                }

                //第十列  电能表条码
                if((cell = row.getCell(9)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,电能表条码为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,电能表条码请设为文本格式)");
                }
                String elecCode = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(elecCode)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,电能表条码为空)");
                }

                //第十五列 采集点编号
                if((cell = row.getCell(14)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,采集点编号为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,采集点编号请设为文本格式)");
                }
                String collectCode = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(collectCode)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,采集点编号为空)");
                }

                //第十六列 终端资产编号
                if((cell = row.getCell(15)) == null) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,终端资产编号为空)");
                }
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (cell.getCellType() != 1) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,终端资产编号请设为文本格式)");
                }
                String assetsCode = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(assetsCode)) {
                    throw new RRException("导入失败(第" + (r + 1) + "行,终端资产编号为空)");
                }

                htUserEntity.setUserId(htUserId);
                htUserEntity.setUserName(userName);
                htUserEntity.setElecCategory(elecCategory);
                htUserEntity.setUserCategory(userCategory);
                htUserEntity.setLoadProperty(loadProperty);
                htUserEntity.setElecAddress(elecAddress);
                htUserEntity.setCheckCycle(checkCycle);
                //Date转为LocalDate
                LocalDate localDate = timeBefore.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
                //利用LocalDate + 检查周期
                LocalDate dateAfter = localDate.plusMonths(checkCycle);
                //将下次检查日期从LocalDate转为Date
                Date timeAfter = Date.from(dateAfter.atStartOfDay(ZoneId.systemDefault()).toInstant());
                htUserEntity.setCheckDateBefore(timeBefore);
                htUserEntity.setCheckDateAfter(timeAfter);
                htUserEntity.setElecCode(elecCode);
                htUserEntity.setCollectCode(collectCode);
                htUserEntity.setAssetsCode(assetsCode);
                htUserEntity.setCheckUser(Integer.parseInt(String.valueOf(checkUser)));

                //往list中加入这个电表
                list.add(transformerEntity);

                //3.赋值htUserIdTemp为当前表箱
                htUserIdTemp = htUserId;
                htUserTemp = htUserEntity;
            } else {
                //往list中加入这个电表
                list.add(transformerEntity);
            }


        }

        //循环完之后，加入剩下数据
        if (htUserIdTemp != null) {
            HtUserEntity byId = this.getById(htUserId);
            if (byId != null) {
                this.baseMapper.removeByHtuserId(byId.getUserId());
                transformerService.removeByHtUserId(byId.getUserId());
            }
            //插入表箱
            this.save(htUserTemp);
            //插入电表
            if (list != null && list.size() > 0) {
                transformerService.insertList(list);
            }
        }


            /*int ownerCount = equipmentOwnerService.count(new QueryWrapper<EquipmentOwnerEntity>()
                    .eq("phone_number", ownerPhone)
            );

            if (ownerCount > 0) {
                equipmentOwnerService.update(new UpdateWrapper<EquipmentOwnerEntity>()
                        .eq("phone_number", ownerPhone)
                        .set("name", ownerName)
                        .set("studio_camera", ownerWorkPhone)
                );
                long ownerId = equipmentOwnerService.selectByPhoneNumber(ownerPhone);
                meterBoxEntity.setEquipmentOwnerId(ownerId);
            } else {
                EquipmentOwnerEntity equipmentOwnerEntity = new EquipmentOwnerEntity();
                equipmentOwnerEntity.setCreateDate(new Date());
                equipmentOwnerEntity.setDeleteStatus(0);
                equipmentOwnerEntity.setName(ownerName);
                equipmentOwnerEntity.setPhoneNumber(ownerPhone);
                equipmentOwnerEntity.setStudioCamera(ownerWorkPhone);
                equipmentOwnerService.save(equipmentOwnerEntity);
                meterBoxEntity.setEquipmentOwnerId(equipmentOwnerEntity.getId());
            }


            int count = this.count(new QueryWrapper<MeterBoxEntity>().eq("meter_box_asset_number", id));
            if (count > 0) {
                //存在
                long meterBoxId = this.baseMapper.selectIdByMeterBoxAssetNumber(id);
                meterEntity.setMeterBoxId(meterBoxId);
            } else {
                //不存在
                this.save(meterBoxEntity);
                AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
                acceptanceEntity.setDeleteStatus(0);
                acceptanceEntity.setStep(0);
                acceptanceEntity.setRegionId(1L);
                acceptanceEntity.setBuildDate(new Date());
                acceptanceEntity.setAcceptanceType(0);
                acceptanceEntity.setAssetNumber(meterBoxEntity.getMeterBoxAssetNumber());
                acceptanceEntity.setType(0);
                acceptanceService.save(acceptanceEntity);

                meterEntity.setMeterBoxId(meterBoxEntity.getId());
            }

            meterEntity.setAccountName(username);
            meterEntity.setAccountNumber(usernumber);
            meterEntity.setEnergyMeterAssetNumber(eId);
            meterEntity.setUserPhone(userTel);
            meterEntity.setBoxRelationship(watchBoxRelationship);
            meterEntity.setElectricEnergyMeterSeal(wattHourMeterSeal);
            meterEntity.setDeleteStatus(0);


            meterEntities.add(meterEntity);
        }
        //去重
        List<MeterEntity> meterEntities1 = meterEntities.stream().distinct().collect(Collectors.toList());
        meterEntities1.forEach(item -> {
                    int count = meterService.count(new QueryWrapper<MeterEntity>().eq("energy_meter_asset_number", item.getEnergyMeterAssetNumber()));
                    if (count > 0) {
                        meterService.update(new UpdateWrapper<MeterEntity>()
                                .eq("meter_box_id", item.getMeterBoxId())
                                .set("energy_meter_asset_number", item.getEnergyMeterAssetNumber())
                                .set("account_name", item.getAccountName())
                                .set("account_number", item.getAccountNumber())
                                .set("user_phone", item.getUserPhone())
                                .set("box_relationship", item.getBoxRelationship())
                                .set("isolation_switch_asset_number", item.getIsolationSwitchAssetNumber())
                                .set("circuit_breaker_asset_number", item.getCircuitBreakerAssetNumber())
                                .set("transformer_asset_number", item.getTransformerAssetNumber())
                                .set("electric_energy_meter_seal", item.getElectricEnergyMeterSeal())
                        );
                    } else {
                        meterService.save(item);
                    }
                }
        );*/
        return notNull;
    }


}
