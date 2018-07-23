/**
 * 核心框架
 * BaseEntity.java
 * 1.0
 */
package cn.cookie.framework.entity;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

/**
 *
 * 类名称：BaseEntity
 * 类描述：
 * 创建人：qiancai
 * 修改人：qiancai
 * 修改时间：2015年12月7日 下午10:54:18
 * 修改备注：
 * @version 1.0.0
 *
 */
@Data
public class BaseEntity implements Serializable{

	/**
	 * serialVersionUID
	 *
	 * @since 1.0.0
	 */
	
	private static final long serialVersionUID = 4089285837632643882L;

	public static final String CREATE_DATE_PROPERTY_NAME = "addTime";// "创建日期"属性名称
	public static final String MODIFY_DATE_PROPERTY_NAME = "updateTime";// "修改日期"属性名称
	@Id
	protected Long id;// ID
	protected Long addTime;// 创建日期
	protected Long addUser;// 创建人
	protected Long updateTime;// 修改日期
	protected Long updateUser;// 修改人

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (id == null || other.getId() == null) {
			return false;
		} else {
			return (id.equals(other.getId()));
		}
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
