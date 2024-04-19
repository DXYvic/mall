package com.macro.mall.service;

import com.macro.mall.dto.UpdateMemberPasswordParam;
import com.macro.mall.model.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 后台用户管理Service
 * Created by macro on 2018/4/26.
 */
public interface UmsMemberService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsMember getMemberByUsername(String username);

    

    

    

    /**
     * 根据用户id获取用户
     */
    UmsMember getItem(Long id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<UmsMember> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    int update(Long id, UmsMember member);

    /**
     * 删除指定用户
     */
    int delete(Long id);

   

    

    
    /**
     * 修改密码
     */
    int updatePassword(UpdateMemberPasswordParam updatePasswordParam);


    /**
     * 获取缓存服务
     */
    UmsMemberCacheService getCacheService();
}
