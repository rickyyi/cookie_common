package cn.cookie.framework.service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cookie.framework.constant.enums.PermissionResult;
import cn.cookie.framework.dto.LoginResponse;
import cn.cookie.framework.dto.LoginUserDto;
import org.springframework.transaction.annotation.Transactional;

public interface LoginService {

    LoginUserDto userLoginSuccess(LoginUserDto user, String token, String loginIp, HttpServletRequest request, HttpServletResponse response) throws Exception;

    @Transactional(readOnly = false)
    boolean userLoginSuccess(LoginUserDto user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 过滤器检查用户登陆情况和权限
     * @param request
     * @return
     */
    public PermissionResult checkPermission(HttpServletRequest request);


    /**
     * <p>用户登录</p>
     * @author QiuYangjun
     * @date 2014-5-6 下午4:04:47
     * @param loginName
     * @param password
     * @param loginIp
     * @return
     * @see
     */
    public LoginResponse login(String loginName, String password, String loginIp, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 用户登出
     * @param userId
     * @param loginIp
     */
    public void logout(String userId, String loginIp) throws Exception;

}
