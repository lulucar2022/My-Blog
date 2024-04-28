package cn.lulucar.blog.service;

import cn.lulucar.blog.entity.AdminUser;

/**
 * @author wenxiaolan
 * @ClassName AdminUserService
 * @date 2024/4/21 12:53
 */
public interface AdminUserService {
    AdminUser login(String userName, String password);

    /**
     * 根据 Id 获取用户信息
     *
     * @param loginUserId
     * @return
     */
    AdminUser getUserDetailById(Integer loginUserId);

    /**
     * 根据 用户名 获取用户信息
     * @param userName
     * @return
     */
    AdminUser getUserDetailByUserName(String userName);

    /**
     * 修改当前登录用户的密码
     *
     * @param loginUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);

    /**
     * 注册用户
     * @param loginUserName
     * @param nickName
     * @param encodedPassword
     * @return
     */
    Boolean signUp(String loginUserName, String nickName, String encodedPassword);
}
