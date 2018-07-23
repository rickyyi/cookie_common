package cn.cookie.framework.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * 类名称：Page
 * 类描述：
 * 创建人：qiancai
 * 修改人：qiancai
 * 修改时间：2015年12月7日 下午11:01:37
 * 修改备注：
 * @version 1.0.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Page {

    public static int UNPAGE = 0;//不分页标志

	private int recordsTotal;
	private List<?> data;
    private int currentPage;
    private int pageCount;
    private int pageSize=10;

    public int getPageCount(){
        return recordsTotal%this.getPageSize()==0? (recordsTotal/this.getPageSize()):(recordsTotal/this.getPageSize()+1);
    }
}
