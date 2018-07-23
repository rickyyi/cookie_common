/**
 * 营运系统
 * com.ane56.aneos.common.dto
 * PageQueryCondition.java
 * 1.0
 * 2014年10月30日-下午1:50:37
 *  2014安能物流-版权所有
 *
 */
package cn.cookie.framework.dto;

import lombok.Data;

/**
 *
 * 类名称：PageQueryCondition
 * 类描述：
 * 创建人：qiancai
 * 修改人：qiancai
 * 修改时间：2015年12月7日 下午11:02:37
 * 修改备注：
 * @version 1.0.0
 *
 */
@Data
public class PageQueryCondition {
	private int sEcho;
	private int columns;
	/** 每页开始数标 */
	private int pageStart;
	/** 每页显示条数 */
	private int pageSize=0;
	 /** 当前请求页 */
    private int currentPage = 1;

    public int getPageStart(){
        return (this.getCurrentPage()-1) <= 0 ? 0:(this.getCurrentPage()-1)*this.getPageSize();
    }

    public Page getPage(){
        Page page = new Page();
        page.setCurrentPage(this.getCurrentPage());
        page.setPageSize(this.getPageSize());
        return page;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageQueryCondition other = (PageQueryCondition) obj;
		if (columns != other.columns)
			return false;
		if (currentPage != other.currentPage)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (pageStart != other.pageStart)
			return false;
		if (sEcho != other.sEcho)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columns;
		result = prime * result + currentPage;
		result = prime * result + pageSize;
		result = prime * result + pageStart;
		result = prime * result + sEcho;
		return result;
	}
}
