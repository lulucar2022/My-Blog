package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.AdminUser;
import cn.lulucar.blog.mapper.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * @author wenxiaolan
 * @ClassName CustomersUserDetails
 * @date 2024/4/24 20:59
 */
@Service
public class CustomersUserDetails implements UserDetailsService {
    
    private final AdminUserMapper adminUserMapper;

    public CustomersUserDetails(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        AdminUser adminUser = adminUserMapper.selectByUserName(username);
        if (adminUser == null) {
            // 如果用户不存在，抛出异常
            throw new UsernameNotFoundException("未找到该用户名的用户："+username);
        }

        // 创建一个包含单个权限的集合  
        GrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        Collection<? extends GrantedAuthority> authorities = Collections.singleton(userAuthority);
        // 将查询到的用户信息转换为 UserDetails对象
        return new User(
                adminUser.getLoginUserName(),
                adminUser.getLoginPassword(),
                authorities
        );
    }
}
