package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.exception.RRException;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.dto.MeterBoxDetailsDto;
import io.renren.modules.sys.dto.MeterBoxDto;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.AcceptanceService;
import io.renren.modules.sys.service.EquipmentOwnerService;
import io.renren.modules.sys.service.MeterService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.MeterBoxDao;
import io.renren.modules.sys.service.MeterBoxService;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author JunCheng He
 */
@Service("meterBoxService")
public class MeterBoxServiceImpl extends ServiceImpl<MeterBoxDao, MeterBoxEntity> implements MeterBoxService {


    @Autowired
    private MeterService meterService;

    @Autowired
    private EquipmentOwnerService equipmentOwnerService;

    @Autowired
    private AcceptanceService acceptanceService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MeterBoxEntity> page = this.page(
                new Query<MeterBoxEntity>().getPage(params),
                new QueryWrapper<MeterBoxEntity>()
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
    public Page<MeterBoxDto> pageList(Map<String, Object> params) {
        Page<MeterBoxDto> page = new Page<>(Integer.parseInt(params.get("page").toString()), Integer.parseInt(params.get("limit").toString()));
        page.setRecords(this.baseMapper.listByAssetNumberAndAddressAndTaiAreaName(page, (String) params.get("meterBoxAssetNumber"), (String) params.get("address"), (String) params.get("meterBoxStatus"), (String) params.get("taiAreaName")));
        return page;
    }

    /**
     * Excel 导入表箱数据
     *
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
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
        if (sheet != null) {
            notNull = true;
        }
        MeterBoxEntity meterBoxEntity;
        MeterEntity meterEntity;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            meterBoxEntity = new MeterBoxEntity();
            meterEntity = new MeterEntity();


            //  第一列 供电所
//            row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(15).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,供电所请设为文本格式)");
//            }
//            String powerunit = row.getCell(15).getStringCellValue().trim();

//            //  第一列 供电所
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(0).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,供电所请设为文本格式)");
            }
            String powerunit = row.getCell(0).getStringCellValue().trim();
//            if (code == null || code.isEmpty()) {
//                new RRException("导入失败(第" + (r + 1) + "行,奖券码未填写)");
//            }
//
            // 第二列 村庄（小区）名称
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(1).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,村庄（小区）名称请设为文本格式)");
            }
            String address = row.getCell(1).getStringCellValue().trim();



            // 第二列 村庄（小区）名称
//            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(5).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,村庄（小区）名称请设为文本格式)");
//            }
//            String address = row.getCell(5).getStringCellValue().trim();


            // 第三列 台区名称
//            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(4).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,台区名称请设为文本格式)");
//            }
//            String platforminfo = row.getCell(4).getStringCellValue().trim();

//            // 第三列 台区名称
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(2).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,台区名称请设为文本格式)");
            }
            String platforminfo = row.getCell(2).getStringCellValue().trim();

//            // 第四列  台区经理姓名
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(3).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行, 台区经理姓名请设为文本格式)");
            }
            String taiAreaManagerName = row.getCell(3).getStringCellValue().trim();

//
//            // 第五列  台区经理手机
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(4).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行, 台区经理手机请设为文本格式)");
            }
            String taiAreaManagerPhone = row.getCell(4).getStringCellValue().trim();

//            // 第六列  台区经理工作电话（座机）
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(5).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行, 台区经理工作电话（座机）请设为文本格式)");
            }
            String taiAreaManagerWorkPhone = row.getCell(5).getStringCellValue().trim();

//


//            //  第七列 台区总表资产号
//            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(6).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,台区总表资产号请设为文本格式)");
//            }
//            String platformStatementAssetNum = row.getCell(6).getStringCellValue().trim();


//            //  第七列 台区总表资产号
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(6).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,台区总表资产号请设为文本格式)");
            }
            String platformStatementAssetNum = row.getCell(6).getStringCellValue().trim();
//
//            //第八列 倍率
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(7).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,倍率请设为文本格式)");
            }
            String multiplyingPower = row.getCell(7).getStringCellValue().trim();


            //第八列 倍率
//            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(7).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,倍率请设为文本格式)");
//            }
//            String multiplyingPower = row.getCell(7).getStringCellValue().trim();
//
////
//            //第九列 总电能表封印
            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(8).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,总电能表封印请设为文本格式)");
            }
            String totalElectricSeal = row.getCell(8).getStringCellValue().trim();

//            //第十列  计量箱地址
            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(9).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱地址请设为文本格式)");
            }
            String gps = row.getCell(9).getStringCellValue().trim();


            //第十列  计量箱地址
//            row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(15).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱地址请设为文本格式)");
//            }
//            String gps = row.getCell(15).getStringCellValue().trim();

//
//            //第十一列 计量箱编号
            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(10).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱编号请设为文本格式)");
            }
            String meterBoxNumber = row.getCell(10).getStringCellValue().trim();


            //第十一列 计量箱编号
//            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(2).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱编号请设为文本格式)");
//            }
//            String meterBoxNumber = row.getCell(2).getStringCellValue().trim();

//
//            //第十二列 计量箱状态
            row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(11).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱状态请设为文本格式)");
            }
            String status = row.getCell(11).getStringCellValue().trim();
//
//            //第十三列 计量箱设备主人姓名
            row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(12).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱设备主人姓名请设为文本格式)");
            }
            String ownerName = row.getCell(12).getStringCellValue().trim().trim();
//
//            //第十四列 计量箱设备主人手机
            row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(13).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱设备主人手机请设为文本格式)");
            }
            String ownerPhone = row.getCell(13).getStringCellValue().trim();

//            //第十五列 计量箱设备主人工作电话（座机）
            row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(14).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱设备主人工作电话（座机）请设为文本格式)");
            }
            String ownerWorkPhone = row.getCell(14).getStringCellValue().trim();



            //第十六列 计量箱资产号
//            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(1).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱资产号请设为文本格式)");
//            }
//            String id = row.getCell(1).getStringCellValue().trim();
//            if (StringUtils.isBlank(id)) {
//                continue;
//            }
//            if (id == null || id.isEmpty()) {
//                new RRException("导入失败(第" + (r + 1) + "行,计量箱资产号未填写)");
//            }


            //第十六列 计量箱资产号
            row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(15).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱资产号请设为文本格式)");
            }
            String id = row.getCell(15).getStringCellValue().trim();
//            if (id == null || id.isEmpty()) {
//                new RRException("导入失败(第" + (r + 1) + "行,计量箱资产号未填写)");
//            }
//


            //第十七列 计量箱规格型号
//            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(3).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱规格型号请设为文本格式)");
//            }
//            String meterBoxModel = row.getCell(3).getStringCellValue().trim();

//            //第十七列 计量箱规格型号
            row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(16).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,计量箱规格型号请设为文本格式)");
            }
            String meterBoxModel = row.getCell(16).getStringCellValue().trim();

//            //第二十四列 集中器资产号
            row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(23).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,集中器资产号请设为文本格式)");
            }
            String concentratorAssetNumber = row.getCell(23).getStringCellValue().trim();


//            //第二十四列 集中器资产号
//            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(9).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,集中器资产号请设为文本格式)");
//            }
//            String concentratorAssetNumber = row.getCell(9).getStringCellValue().trim();

//
//            //第二十五列 集中器封印
            row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(24).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,集中器封印请设为文本格式)");
            }
            String concentratorSeal = row.getCell(24).getStringCellValue().trim();

//
//            //电能表
//
//
//            //第十九列  户名
            row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(17).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,户名请设为文本格式)");
            }
            String username = row.getCell(17).getStringCellValue().trim();

//
            //第十九列  户名
//            row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(11).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,户名请设为文本格式)");
//            }
//            String username = row.getCell(11).getStringCellValue().trim();
//
//
//            //第二十列 户号
//            row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(10).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,户号请设为文本格式)");
//            }
//            String usernumber = row.getCell(10).getStringCellValue().trim();


//            //第二十列 户号
            row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(18).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,户号请设为文本格式)");
            }
            String usernumber = row.getCell(18).getStringCellValue().trim();



            //第二十一列 电能表资产号
//            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(8).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,电能表资产号请设为文本格式)");
//            }
//            String eId = row.getCell(8).getStringCellValue().trim();

//            //第二十一列 电能表资产号
            row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(19).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,电能表资产号请设为文本格式)");
            }
            String eId = row.getCell(19).getStringCellValue().trim();
            if (eId == null || eId.isEmpty()) {
                throw new RRException("导入失败(第" + (r + 1) + "行,电能表资产号未填写)");
            }
//

            //第二十二列 用户电话
//            row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(12).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,用户电话请设为文本格式)");
//            }
//            String userTel = row.getCell(12).getStringCellValue().trim();

//            //第二十二列 用户电话
            row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(20).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,用户电话请设为文本格式)");
            }
            String userTel = row.getCell(20).getStringCellValue().trim();


            //第二十三列 箱表关系
//            row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
//            if (row.getCell(13).getCellType() != 1) {
//                throw new RRException("导入失败(第" + (r + 1) + "行,箱表关系请设为文本格式)");
//            }
//            String watchBoxRelationship = row.getCell(13).getStringCellValue().trim();

//            //第二十三列 箱表关系
            row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(21).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,箱表关系请设为文本格式)");
            }
            String watchBoxRelationship = row.getCell(21).getStringCellValue().trim();
//
//            //第二十四列 电能表封印
            row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);
            if (row.getCell(22).getCellType() != 1) {
                throw new RRException("导入失败(第" + (r + 1) + "行,电能表封印请设为文本格式)");
            }
            String wattHourMeterSeal = row.getCell(22).getStringCellValue().trim();

            meterBoxEntity.setPowerSupply(powerunit);
            meterBoxEntity.setAddress(address);
            meterBoxEntity.setTaiAreaName(platforminfo);
            meterBoxEntity.setTaiAreaTotalAssetNumber(platformStatementAssetNum);
            meterBoxEntity.setMagnification(Integer.valueOf(multiplyingPower));
            meterBoxEntity.setTotalEnergyMeterSeal(totalElectricSeal);
            meterBoxEntity.setMeterBoxAddress(gps);
            meterBoxEntity.setMeterBoxNumber(meterBoxNumber);
            meterBoxEntity.setMeterBoxStatus(status);
            meterBoxEntity.setMeterBoxAssetNumber(id);
            meterBoxEntity.setMeterBoxModel(meterBoxModel);
            meterBoxEntity.setDeleteStatus(0);
            //采集终端资产号
            meterBoxEntity.setAcquisitionTerminalAssetNumber(meterBoxModel);
            meterBoxEntity.setConcentratorSeal(concentratorSeal);
            meterBoxEntity.setConcentratorAssetNumber(concentratorAssetNumber);

            meterBoxEntity.setAddress(address);

            meterBoxEntity.setTaiAreaManagerName(taiAreaManagerName);
            meterBoxEntity.setTaiAreaManagerPhone(taiAreaManagerPhone);
            meterBoxEntity.setTaiAreaManagerWorkPhone(taiAreaManagerWorkPhone);


            int ownerCount = equipmentOwnerService.count(new QueryWrapper<EquipmentOwnerEntity>()
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
        );
        return notNull;
    }

    @Override
    public MeterBoxEntity getMeterBoxByAssetNumber(String assetNumber) {
        MeterBoxEntity meterBoxEntity = null;
        meterBoxEntity = this.baseMapper.getMeterBoxByAssetNumber(assetNumber);
        if (meterBoxEntity == null) {
            throw new RRException("未找到对应的数据");
        }
        return meterBoxEntity;
    }

    @Override
    public MeterBoxEntity getMeterBoxAssetNumberById(String id) {
        MeterBoxEntity meterBoxEntity = null;
        meterBoxEntity = this.baseMapper.getMeterBoxAssetNumberById(id);
        if (meterBoxEntity == null) {
            throw new RRException("找不到电表箱id为[" + id + "]对应的电表箱设备编号");
        }
        return meterBoxEntity;
    }

    /**
     * 根据id更新责任人
     *
     * @param id
     * @param ownerId
     * @return
     */
    @Override
    public Boolean updateByIdAndOwnerId(String id, Long ownerId) {
        this.update(new UpdateWrapper<MeterBoxEntity>().eq("id", id).set("equipment_owner_id", ownerId));
        return true;
    }

    @Override
    public List<Map<String, Object>> getTotalByDiv() {
        List<Map<String, Object>> list = null;
        list = this.baseMapper.getTotalByDiv();
        if (list == null) {
            throw new RRException("未找到对应的数据");
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getProcessingStatusByDiv() {
        List<Map<String, Object>> list = null;
        list = this.baseMapper.getProcessingStatusByDiv();
        if (list == null) {
            throw new RRException("未找到对应的数据");
        }
        return list;
    }

    /**
     * 根据电表箱资产号查询电表箱详情
     *
     * @param assetNumber
     * @return
     */
    @Override
    public MeterBoxDetailsDto selectByAssetNumber(String assetNumber) {
        return this.baseMapper.selectByAssetNumber(assetNumber);
    }

    @Override
    public HtUserVO getByUserId(String id) {

        if(id == null)
            return null;
        return this.baseMapper.getByUserId(id);
    }

}
