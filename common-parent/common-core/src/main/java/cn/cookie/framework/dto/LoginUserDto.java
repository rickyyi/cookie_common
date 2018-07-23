package cn.cookie.framework.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.cookie.framework.constant.enums.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by qiancai on 2015/1/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class LoginUserDto implements Serializable {
    private String id;

    private String userName;

    private Long userId;

    private String realName;

    private String password;

    private UserStatus status;

    private String remark;

    private Integer province;

    private Integer city;

    private String qq;

    private String phone;

    private Integer logintimes;

    private Date loginTime;

    private String loginIp;

    List<ResourcesDto> resourcesList;
    List<RolesDto> rolesList;

    private Long roleId;//登录的角色
    private Integer isExternal;//是否外包
}
