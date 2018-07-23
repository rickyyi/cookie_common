package cn.cookie.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by qiancai on 2015/12/18 0018.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class DataResponseException extends BaseException {

    private String errorCode;
    private String message;
    public DataResponseException(String message){
        super(message);
    }
    public DataResponseException(Throwable cause){
        super(cause);
    }
    public DataResponseException(String errorCode,String messge){
        super(errorCode,messge);
        this.errorCode = errorCode;
        this.message = messge;
    }
}
