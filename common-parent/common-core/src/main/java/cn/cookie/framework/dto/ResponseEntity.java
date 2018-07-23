package cn.cookie.framework.dto;


import java.io.Serializable;

import cn.cookie.common.exception.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class ResponseEntity implements Serializable {
	private static final long serialVersionUID = -720807478055084231L;
	public static final String STATUS_OK = "1";
    public static final String STATUS_FAIL = "0";
    
	private String status;
	private String error;
	private String msg;
	private Object data;
	private Integer currentPage;
	private Integer pageCount;
	private Integer pageSize;
	private Integer recordsTotal;
	
	/**
	 * 
	 * @param entity set field: status, error, msg
	 * @param errorCode
	 */
	public static void populateByErrorCode(ResponseEntity entity, String errorCode, String errorMsg){
		errorCode = (null == errorCode || errorCode.isEmpty()) ? ErrorCode.DEFAULT_FAIL_CODE : errorCode;
		errorMsg = (null == errorMsg || errorMsg.isEmpty()) ? ErrorCode.getMsg(errorCode) : errorMsg;
		
		if(ErrorCode.DEFAULT_OK_CODE.equals(errorCode)){
			entity.setStatus(STATUS_OK);
		}else{
			entity.setStatus(STATUS_FAIL);
		}
		
		entity.setError(errorCode);
		entity.setMsg(errorMsg);
	}
	
	/**
	 * equivalent to {@link #populateByErrorCode(ResponseEntity, String, null)}
	 * @param entity
	 * @param errorCode
	 */
	public static void populateByErrorCode(ResponseEntity entity, String errorCode){
		populateByErrorCode(entity, errorCode, null);
	}
	
	/**
	 * 
	 * @param errorCode
	 * @return with field: status, error, msg
	 */
	public static ResponseEntity fromErrorCode(String errorCode){
		ResponseEntity ret = new ResponseEntity();
		populateByErrorCode(ret, errorCode);
		
		return ret;
	}
	
	public ResponseEntity(){
		
	}
	
	public ResponseEntity(String status){
		this.status = status;
	}
	
	public ResponseEntity(String status, String error){
		this.status = status;
		this.error = error;
	}
	
	public ResponseEntity(String status, Object data){
		this.status = status;
		this.data = data;
	}
	
	public ResponseEntity(String status, Object data,int currentPage,int pageCount,int pageSize,int recordsTotal){
		this.status = status;
		this.data = data;
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.pageSize = pageSize;
		this.recordsTotal = recordsTotal;
	}

	public ResponseEntity(String status, Object data,int pageCount){
		this.status = status;
		this.data = data;
		this.pageCount = pageCount;
	}

	public ResponseEntity(String status, String error, String msg, Object data){
		this.status = status;
		this.error = error;
		this.msg = msg;
		this.data = data;
	}

	public ResponseEntity setStatus(String status) {
		this.status = status;
		return this;
	}

	public ResponseEntity setError(String error) {
		this.error = error;
		return this;
	}


	public ResponseEntity setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMsg() {
		return msg;
	}

	public Object getData() {
		return data;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
}
