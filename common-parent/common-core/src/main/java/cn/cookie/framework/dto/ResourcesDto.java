package cn.cookie.framework.dto;

import java.util.List;

import cn.cookie.framework.constant.enums.ResourcesType;
import cn.cookie.framework.entity.BaseEntity;
import cn.cookie.framework.constant.enums.ResourcesStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ResourcesDto extends BaseEntity {
    private String parentId;

    private String modulesName;

    private String modulesCode;

    private String name;

    private String description;

    private String url;

    private String controller;

    private String action;

    private ResourcesStatus status;

    private ResourcesType type;

    private Integer sort;
    private String remark;
    private String titleName;
    private String content;

    //判断下面是否有子节点
    private Boolean isLeaf;

    private String parentName;

    private List<ResourcesDto> children;


}