package io.renren.modules.sys.controller;

import java.io.*;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.VO.HtUserVO;
import io.renren.modules.sys.dto.MalfunctionStatics;
import io.renren.modules.sys.dto.MeterBoxDetailsDto;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.renren.common.utils.Common.dateArray;
import static io.renren.common.utils.Common.getPastDate;


/**
 * 计量箱表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-28 10:41:10
 */
@RestController
@RequestMapping("sys/meterbox")
public class MeterBoxController extends AbstractController {
    @Autowired
    private MeterBoxService meterBoxService;

    @Autowired
    private MalfunctionService malfunctionService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private AcceptanceService acceptanceService;

    @Autowired
    private EquipmentOwnerChangeRecordService equipmentOwnerChangeRecordService;

    @Autowired
    private HtUserService htUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:meterbox:list")
    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = meterBoxService.queryPage(params);

        return R.ok().put("page", meterBoxService.pageList(params));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:meterbox:info")
    public R info(@PathVariable("id") String id) {
        //MeterBoxEntity meterBox = meterBoxService.getById(id);
        //MeterBoxEntity meterBox = meterBoxService.getById(id);

        HtUserVO htUserVO = meterBoxService.getByUserId(id);
        return R.ok().put("meterBox", htUserVO);
    }

    /**
     * 详情
     */
    //@RequestMapping("/details/{asset}")
    //@RequiresPermissions("sys:meterbox:info")
    //public R info(@PathVariable("asset") String asset) {
    //    MeterBoxDetailsDto meterBox = meterBoxService.selectByAssetNumber(asset);
    //
    //    return R.ok().put("data", meterBox);
    //}

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:meterbox:save")
    public R save(@RequestBody MeterBoxEntity meterBox) {
        meterBoxService.save(meterBox);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:meterbox:update")
    public R update(@RequestBody MeterBoxEntity meterBox) {
        ValidatorUtils.validateEntity(meterBox);
        meterBoxService.updateById(meterBox);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:meterbox:delete")
    public R delete(@RequestBody Long[] ids) {
        meterBoxService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 改造 表箱资产号  0-新装 1-改造 2-更换 3-消缺
     */
    @RequestMapping("/transformation/{id}/{acceptanceType}")
    public R transformation(@PathVariable(name = "id") String id, @PathVariable(name = "acceptanceType") Integer acceptanceType) {
        AcceptanceEntity acceptanceEntity = new AcceptanceEntity();
        acceptanceEntity.setType(0);
        acceptanceEntity.setAssetNumber(id);
        acceptanceEntity.setAcceptanceType(acceptanceType);
        acceptanceEntity.setBuildDate(new Date());
        acceptanceEntity.setRegionId(1L);
        acceptanceEntity.setStep(0);
        acceptanceEntity.setDeleteStatus(0);
        acceptanceEntity.setType(0);

        acceptanceService.save(acceptanceEntity);

        return R.ok();
    }


    /**
     * 核销模板文件下载
     */
//    @RequestMapping("/download")
//    @RequiresPermissions("sys:watchbox:upload")
//    public void downLoad(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        String fileName = "导入模板.xlsx";
//        fileName = new String(fileName.getBytes(), "ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//        InputStream fis = null;
//        OutputStream os = null;
//        try {
//            //读取文件
//            File file = null;
//            String sys = System.getProperty("os.name");
//            if (sys.toLowerCase().startsWith("win")) {
//                //在开发环境下这么读取文件，在生产环境中不能这么读取
//                file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "file/" + fileName);
//                fis = new FileInputStream(file);
//                //写入输出流返回客户端
//                os = response.getOutputStream();
//                int len = -1;
//                byte[] b = new byte[1024 * 10];
//                while ((len = fis.read(b)) != -1) {
//                    os.write(b, 0, len);
//                }
//                os.flush();
//
//            } else {
//                //在生产环境下   path 不以’/'开头时默认是从此类所在的包下取资源，以’/'开头则是从ClassPath根下获取
//                //这种方式打成jar包后，文件会被损坏！！！使用poi解决
//                fis = this.getClass().getResourceAsStream("file/" + fileName);
//                //HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls
//                //XSSFWorkbook:是操作Excel2007的版本，扩展名是.xlsx
//                XSSFWorkbook workbook = new XSSFWorkbook(fis);
//                os = response.getOutputStream();
//                workbook.write(os);
//                workbook.close();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//
////        ServletContext servletContext = request.getServletContext();
////        String fileName = "导入模板.xlsx";
////        //得到文件所在位置
//////        String realPath = servletContext.getRealPath("templates/download/" + fileName);
////        //将该文件加入到输入流之中
////        InputStream in = getClass().getClassLoader().getResourceAsStream("file/" + fileName);
////        byte[] body = null;
////        // 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
////        body = new byte[in.available()];
////        //读入到输入流里面
////        in.read(body);
////
////        //防止中文乱码
////        fileName = new String(fileName.getBytes("gbk"), "iso8859-1");
////        //设置响应头
////        HttpHeaders headers = new HttpHeaders();
////        headers.add("Content-Disposition", "attachment;filename=" + fileName);
////        //设置响应码
////        HttpStatus statusCode = HttpStatus.OK;
////        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
////        return response;
//    }


    @RequestMapping("/upload")
    @RequiresPermissions("sys:watchbox:upload")
    public R update(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        meterBoxService.batchImport(fileName, file);
        return R.ok();
    }


    @GetMapping("/main")
    public R mainPage() {
        //1、表箱总数 2、运行总数 3、故障数 4、本月巡检次数 5、故障统计：故障总数 与已处理故障数 6、实时显示故障
        Map<String, Object> map = new HashMap<>(16);

        //表箱总数
        //map.put("meterBoxCount", meterBoxService.count(new QueryWrapper<MeterBoxEntity>().eq("delete_status", 0)));
        map.put("meterBoxCount", htUserService.count(new QueryWrapper<HtUserEntity>().eq("del_status", 0)));
        //表箱运行总数
        //map.put("runCount", meterBoxService.count(new QueryWrapper<MeterBoxEntity>().eq("delete_status", 0).eq("meter_box_status", "运行")));
        map.put("runCount", htUserService.count(new QueryWrapper<HtUserEntity>()
                .eq("del_status", 0)));
        //故障发生总数
        map.put("malfunctionCount", malfunctionService.count());
        //本月巡检次数
        map.put("nowMonthCount", inspectionService.countByNowMonth());

        //故障统计
//        SeriesEntity seriesEntity = new SeriesEntity();
//        seriesEntity.setName("故障总数");

        List<MalfunctionStatics> malfunctionStatics = malfunctionService.countByProcessingStatus(null);
        map.put("malfunctionTotal", malfunctionStatics);
        List<MalfunctionStatics> malfunctionStatics1 = malfunctionService.countByProcessingStatus(2);
        map.put("handelTotal", malfunctionStatics1);
        map.put("topSix", malfunctionService.selectTopSix());

        //根据缺陷等级统计
        map.put("defectLevel", malfunctionService.groupByDefectLevel());

        map.put("dateArray", dateArray(7));

        //近几天巡检次数统计
        map.put("createTime", inspectionService.groupByCreateTime(getPastDate(6), getPastDate(-1)));

        return R.ok().put("data", map);
    }


    /**
     * 更改责任人
     */
    @RequestMapping("/changeOwner/{id}/{ownerId}")
    @RequiresPermissions("sys:meterbox:info")
    public R info(@PathVariable("id") String id, @PathVariable("ownerId") Long ownerId) {
        meterBoxService.updateByIdAndOwnerId(id, ownerId);

        EquipmentOwnerChangeRecordEntity equipmentOwnerChangeRecordEntity = new EquipmentOwnerChangeRecordEntity();
        equipmentOwnerChangeRecordEntity.setChangeTime(new Date());
        equipmentOwnerChangeRecordEntity.setDeleteStatus(0);
        equipmentOwnerChangeRecordEntity.setHtUserId(id);
        equipmentOwnerChangeRecordEntity.setEquipmentOwnerId(ownerId);
        equipmentOwnerChangeRecordEntity.setChanger(this.getUserId());
        equipmentOwnerChangeRecordService.save(equipmentOwnerChangeRecordEntity);
        return R.ok();
    }


}
