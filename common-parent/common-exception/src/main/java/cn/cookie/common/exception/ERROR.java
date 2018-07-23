package cn.cookie.common.exception;

/**
 * Created by qiancai on 2015/12/18 0018.
 * 
 * 
 * by zhou shengzong
 * 不推荐使用枚举数定义错误码
 * 请使用 #ErrorCode 代替
 * 
 */
//global
public enum  ERROR {
    E00000000("成功(没有异常)"),
    E00000001("系统忙(未知异常)"),
    E00010000("参数格式不正确"),
    E00010001("参数签名不正确"),
    E00010002("对称秘钥过期"),
    E00020000("网络超时"),
    E00030000("权限不足"),
    E00040000("数据库操作异常"),
    E00050000("不支持的客户端"),
    E00050001("不支持的客户端APP版本"),
    E00050002("不支持的设备型号"),
    E00050003("不支持的客户端网络环境"),
    E00050004("不支持的客户端操作系统"),
    E00060001("操作超时"),
    E00060002("验证码不正确"),
    E00070000("请求受理中，请留意最新消息。"),
    E00090000(""), //自定义错误，消息内容自定义
    ;

    private String type;
    private ERROR(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return type;
    }
    
    //userproduct
    public enum USER{
    	E03010001("手机号码格式不正确"),
    	E03010002("身份证号码格式不正确"),
    	E03010003("密码格式不正确"),
    	E03010004("用户名或密码不正确"),
    	E03010005("手机号不可用"), //已注册
    	E03010006("手机号不可用"), //修改过
    	E03010007("手机号和身份证不匹配"),
    	E03010008("当前有借款，不允许该操作"),
    	E03010009("邮箱格式不正确"),
    	E03010010("身份证、姓名不匹配"),
    	E03010011("身份证已验证"),
    	E03010012("资料填写不完整, 请先完善资料"),
    	E03010013("额度不足, 请先完善资料"),
    	E03010014("身份证未通过验证，不允许此项操作。"),
    	E03010015("银行卡验证中，请耐心等待。"),
    	E03010016("抱歉，不支持该类型的银行卡。"),
    	E03010017("抱歉，不支持的该银行卡的卡片。"),
    	E03010018("该银行卡已经绑定过"),
    	;
    	
    	private String msg;
        private USER(String msg) {
            this.msg = msg;
        }
        
        @Override
        public String toString() {
            return msg;
        }
    }
}
