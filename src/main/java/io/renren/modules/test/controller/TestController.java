/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.test.controller;


import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * APP登录授权
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试", value = "测试" )
public class TestController {
    @Autowired
    private SysLogService sysLogService;
    /**
     * 登录
     */
    @GetMapping("test")
    @ApiOperation("登录-这种情况swagger没法测")
    public R test(@RequestParam Map<String, Object> params){
            PageUtils page = sysLogService.queryPage(params);
            return R.ok().put("page", page);
    }
    @PostMapping("test2")
    @ApiOperation("登录2")
    public R test2(@RequestBody Map<String, Object> params){
        PageUtils page = sysLogService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 登录
     */
    @GetMapping("test3")
    @ApiOperation("登录3")
    public R test3(@RequestParam String params){
        //PageUtils page = sysLogService.queryPage(params);
        return R.ok().put("page", params);
    }
}
