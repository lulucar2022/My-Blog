package cn.lulucar.blog.mapper;

import cn.lulucar.blog.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author wenxiaolan
 * @ClassName AdminUserMapper
 * @date 2024/4/20 15:52
 */
public interface AdminUserMapper {
    // 添加 adminUser
    int insert(AdminUser record);
    //
    int insertSelective(AdminUser record);

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    AdminUser selectByPrimaryKey(Integer adminUserId);
    
    AdminUser selectByUserName(String userName);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);
}
