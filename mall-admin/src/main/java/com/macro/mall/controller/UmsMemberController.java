package com.macro.mall.controller;

import com.macro.mall.common.api.CommonPage;
import com.macro.mall.common.api.CommonResult;
import com.macro.mall.dto.UpdateMemberPasswordParam;
import com.macro.mall.model.UmsMember;
import com.macro.mall.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 后台用户管理Controller
 * Created by macro on 2018/4/26.
 */
@Controller
@Api(tags = "UmsMemberController")
@Tag(name = "UmsMemberController", description = "后台分销商管理")
@RequestMapping("/member")
public class UmsMemberController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsMemberService memberService;

    @ApiOperation("根据用户名或姓名分页获取分销商列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsMember>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<UmsMember> memberList = memberService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(memberList));
    }

    @ApiOperation("获取指定分销商信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsMember> getItem(@PathVariable Long id) {
        UmsMember member = memberService.getItem(id);
        return CommonResult.success(member);
    }

    @ApiOperation("修改指定分销商信息")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody UmsMember member) {
        int count = memberService.update(id, member);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定分销商密码")
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateMemberPasswordParam updatePasswordParam) {
        int status = memberService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定分销商信息")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = memberService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status") Integer status) {
        UmsMember umsMember = new UmsMember();
        umsMember.setStatus(status);
        int count = memberService.update(id,umsMember);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
