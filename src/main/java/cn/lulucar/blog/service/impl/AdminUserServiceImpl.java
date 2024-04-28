package cn.lulucar.blog.service.impl;

import cn.lulucar.blog.entity.AdminUser;
import cn.lulucar.blog.mapper.AdminUserMapper;
import cn.lulucar.blog.service.AdminUserService;
import cn.lulucar.blog.util.PasswordEncodeUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wenxiaolan
 * @ClassName AdminUserServiceImpl
 * @date 2024/4/21 12:55
 */
@Slf4j
@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Resource
    private AdminUserMapper adminUserMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    /**
     * 用户登录
     * 使用 SpringBoot-security框架提供的Bcrypt加密功能加密用户密码
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    @Override
    public AdminUser login(String userName, String password) {
        AdminUser adminUser = adminUserMapper.selectByUserName(userName);
        if (passwordEncoder.matches(password,adminUser.getLoginPassword())) {
            log.info("用户：{} \n 密码：{}",userName,adminUser.getLoginPassword());
            return adminUserMapper.login(userName, adminUser.getLoginPassword());
        }
        
        return null;
        
    }

    /**
     * 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    /**
     * 根据 用户名 获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    public AdminUser getUserDetailByUserName(String userName) {
        return adminUserMapper.selectByUserName(userName);
    }

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId 用户ID
     * @param originalPassword 用户的旧密码
     * @param newPassword 用户的新密码
     * @return 返回 true 表示修改密码成功；false 表示修改密码失败
     */
    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        // 获取目标用户的账户信息。
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        // 判断用户非空，才能修改密码
        if (adminUser != null && newPassword != null && !(newPassword.isEmpty()) ) {
            String originalPasswordEncoded = PasswordEncodeUtil.encode(originalPassword);
            String newPasswordEncoded = PasswordEncodeUtil.encode(newPassword);
            // 判断 旧密码是否正确
            if (originalPasswordEncoded.equals(adminUser.getLoginPassword())){
                // 把加密过后的新密码替换旧密码
                adminUser.setLoginPassword(newPasswordEncoded);
                // 执行sql语句存入数据库
                // 执行成功则返回true
                return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
            }
        }
        // 修改密码失败
        return false;
    }

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId 用户ID
     * @param loginUserName 用户名称
     * @param nickName 昵称
     * @return
     */
    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String nickName) {
        // 获取当前用户的账户信息。
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        // 判断 用户非空才能修改信息
        if (adminUser != null) {
            // 修改信息
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            // 执行sql语句，存入数据库
            // 执行成功则返回 true
            return adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0;
            
        }
        // 修改用户信息失败
        log.error("updateName 执行失败");
        return false;
    }

    /**
     * 注册管理员账户
     * @param loginUserName 用户名
     * @param nickName 昵称
     * @param encodedPassword 加密后的密码
     * @return
     */
    @Override
    public Boolean signUp(String loginUserName, String nickName, String encodedPassword) {
        AdminUser adminUser = adminUserMapper.selectByUserName(loginUserName);
        if (adminUser == null) {
            adminUser = new AdminUser();
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            adminUser.setLoginPassword(encodedPassword);
            adminUser.setLocked((byte) 0);
            return adminUserMapper.insert(adminUser) > 0;
        }
        return false;
    }
}
