package com.macro.mall.service;

import com.macro.mall.model.UmsMember;

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
     * 获取缓存后台用户信息
     */
    UmsMember getMember(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setMember(UmsMember member);




}
