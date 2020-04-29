package io.renren.controller;


import io.renren.annotation.Login;
import io.renren.common.utils.R;
import io.renren.dto.MalfunctionDto;
import io.renren.entity.MalfunctionEntity;
import io.renren.service.MalfunctionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 缺陷表
 *
 * @author HEJUNCHENG
 * @email he_jun_cheng@163.com
 * @date 2019-07-27 11:23:44
 */
@RestController
@RequestMapping("malfunction")
@Api(tags = "缺陷接口")
public class ApiMalfunctionController {
    @Autowired
    private MalfunctionService malfunctionService;

    /**
     * 根据故障id查询详情
     */
    @RequestMapping("/details")
    public R details(@RequestParam("id") Long id) {
        MalfunctionDto malfunctionInfoDto = malfunctionService.getDetailById(id);
        return R.ok().put("data", malfunctionInfoDto);
    }

    /**
     * 根据表箱id详情
     */
    @Login
    @RequestMapping("/detailsByUserId")
    public R detailsByUserId(@RequestParam("htUserId") String htUserId) {
        System.out.println(htUserId);
        MalfunctionDto malfunctionInfoDto = malfunctionService.detailsByUserId(htUserId);
        return R.ok().put("data", malfunctionInfoDto);
    }


}
