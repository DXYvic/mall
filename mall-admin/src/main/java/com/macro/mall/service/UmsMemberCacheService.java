package com.macro.mall.service;

import com.macro.mall.model.UmsMember;
import com.macro.mall.model.UmsResource;

import java.util.List;

/**
 * 后台分销商缓存管理Service
 * Created by macro on 2020/3/13.
 */
public interface UmsMemberCacheService {
    /**
     * 删除后台用户缓存
     */
    void delMember(Long memberId);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long memberId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     */
    UmsMember getMember(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setMember(UmsMember member);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long memberId);

    /**
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long memberId, List<UmsResource> resourceList);
}
