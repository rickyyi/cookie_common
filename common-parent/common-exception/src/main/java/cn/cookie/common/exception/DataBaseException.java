package cn.cookie.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;

/**
 * Created by qiancai on 2015/12/18 0018.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class DataBaseException extends DataAccessException {

    private String message;

    public DataBaseException(String message){
        super(message);
    }

   public DataBaseException(String message,Throwable cause){
       super(message,cause);

   }

}
