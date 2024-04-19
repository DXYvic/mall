package com.macro.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.macro.mall.dto.UpdateMemberPasswordParam;
import com.macro.mall.mapper.UmsMemberMapper;
import com.macro.mall.model.*;
import com.macro.mall.security.util.SpringUtil;
import com.macro.mall.service.UmsMemberCacheService;
import com.macro.mall.service.UmsMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 后台分销商管理Service实现类
 * Created by macro on 2018/4/26.
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UmsMemberMapper memberMapper;



    @Override
    public UmsMember getMemberByUsername(String username) {
        //先从缓存中获取数据
        UmsMember member = getCacheService().getMember(username);
        if (member != null) return member;
        //缓存中没有从数据库中获取
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if (memberList != null && memberList.size() > 0) {
            member = memberList.get(0);
            //将数据库中的数据存入缓存中
            getCacheService().setMember(member);
            return member;
        }
        return null;
    }
    

    
    @Override
    public UmsMember getItem(Long id) {
        return memberMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UmsMember> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsMemberExample example = new UmsMemberExample();
        UmsMemberExample.Criteria criteria = example.createCriteria();
        if (!StrUtil.isEmpty(keyword)) {
            criteria.andUsernameLike("%" + keyword + "%");
            example.or(example.createCriteria().andNickNameLike("%" + keyword + "%"));
        }
        return memberMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, UmsMember member) {
        member.setId(id);
        UmsMember rawMember = memberMapper.selectByPrimaryKey(id);
        if(rawMember.getPassword().equals(member.getPassword())){
            //与原加密密码相同的不需要修改
            member.setPassword(null);
        }else{
            //与原加密密码不同的需要加密修改
            if(StrUtil.isEmpty(member.getPassword())){
                member.setPassword(null);
            }else{
                member.setPassword(passwordEncoder.encode(member.getPassword()));
            }
        }
        int count = memberMapper.updateByPrimaryKeySelective(member);
        getCacheService().delMember(id);
        return count;
    }

    @Override
    public int delete(Long id) {
        int count = memberMapper.deleteByPrimaryKey(id);
        getCacheService().delMember(id);
        getCacheService().delResourceList(id);
        return count;
    }

    

    


    @Override
    public int updatePassword(UpdateMemberPasswordParam param) {
        if(StrUtil.isEmpty(param.getUsername())
                ||StrUtil.isEmpty(param.getOldPassword())
                ||StrUtil.isEmpty(param.getNewPassword())){
            return -1;
        }
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(param.getUsername());
        List<UmsMember> memberList = memberMapper.selectByExample(example);
        if(CollUtil.isEmpty(memberList)){
            return -2;
        }
        UmsMember umsMember = memberList.get(0);
        if(!passwordEncoder.matches(param.getOldPassword(),umsMember.getPassword())){
            return -3;
        }
        umsMember.setPassword(passwordEncoder.encode(param.getNewPassword()));
        memberMapper.updateByPrimaryKey(umsMember);
        getCacheService().delMember(umsMember.getId());
        return 1;
    }



    @Override
    public UmsMemberCacheService getCacheService() {
        return SpringUtil.getBean(UmsMemberCacheService.class);
    }
}
