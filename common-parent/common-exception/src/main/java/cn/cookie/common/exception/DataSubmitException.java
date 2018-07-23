package cn.cookie.common.exception;

/**
 * Created by qiancai on 2016/1/20 0020.
 */
public class DataSubmitException extends BaseException {
    private String errorCode;
    private String message;
    public DataSubmitException(String message){
        super(message);
    }
    public DataSubmitException(Throwable cause){
        super(cause);
    }
    public DataSubmitException(String errorCode,String messge){
        super(errorCode,messge);
        this.errorCode = errorCode;
        this.message = messge;
    }
}
