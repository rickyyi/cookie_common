package cn.cookie.common.cache.entity;

import cn.cookie.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created by qiancai on 2016/1/19 0019.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "plum_third_service_cfg")
public class ThirdServiceCfg extends BaseEntity {
    @Column(name="`key`")
    private String key;
    private String value;
    private String remark;
}
