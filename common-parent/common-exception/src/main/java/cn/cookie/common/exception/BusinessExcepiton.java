package cn.cookie.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class BusinessExcepiton extends BaseException {

    private String errorCode;

    private String message;


    public BusinessExcepiton(String errorCode,String message,Throwable cause){
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

}