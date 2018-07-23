package cn.cookie.common.util;
import javax.xml.namespace.QName;

/*import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qiancai
 */
public class WsClientUtil {

/*	private static final Logger logger = LoggerFactory.getLogger(WsClientUtil.class);
	
	*//**
	 * 功能: 调用某个Java语言的WebService的服务
	 * @param serverurl 访问WebService服务端的Url地址  (例如: http://localhost:8081/authService/services/AuthService)
	 * @param methodname WebService服务端的某个方法名字  (例如: loginSessionUserinfo)
	 * @param pkgname WebService服务端程序所在的包路径[targetnamespace] (例如: http://service.wsapplication.com)
	 * @param jsonparams WebService服务端要接受的json字符串参数 (例如: {'username':'shihuan', 'password':'123456'})
	 * @throws AxisFault 
	 * @throws Exception
	 * *//*
	public static String getWsResult(String serverurl, String methodname, String pkgname, String jsonparams) throws AxisFault, Exception {
		if("".equals(serverurl) || "".equals(methodname) || "".equals(pkgname) || "".equals(jsonparams)){
			throw new Exception(String.format("调用WebService服务的参数不合法! serverurl=%1$s, methodname=%2$s, pkgname=%3$s, jsonparams=%4$s", 
					serverurl, methodname, pkgname, jsonparams));
		}
		
		try{
            RPCServiceClient client = new RPCServiceClient();
            Options options = client.getOptions();
            String url = serverurl;
            EndpointReference end = new EndpointReference(url);
            options.setTo(end);
             
            Object[] obj = new Object[]{jsonparams};
             Class<?>[] classes = new Class[] { String.class };  
             
            QName qname = new QName(pkgname, methodname);  
            String result = (String) client.invokeBlocking(qname, obj,classes)[0];
            return result;
        }catch(Exception e){
            logger.error("webservice调用失败",e);
        }
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getWsResult(String serverurl, String methodname, String pkgname, Object[] params ) throws Exception {
		if("".equals(serverurl) || "".equals(methodname) || "".equals(pkgname) || null == params || params.length == 0){
			throw new Exception(String.format("调用WebService服务的参数不合法! serverurl=%1$s, methodname=%2$s, pkgname=%3$s, jsonparams=%4$s", 
					serverurl, methodname, pkgname, params));
		}
		
		RPCServiceClient serviceClient = null;
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(serverurl);
	        options.setTo(targetEPR);
	        options.setAction(methodname);
	        Object[] opAddEntryArgs = params;
	        Class[] classes = new Class[]{String.class,String.class,String.class,String.class};
	        QName opAddEntry = new QName(pkgname, methodname);
	        //设置60秒超时
	        options.setTimeOutInMilliSeconds(60000);
	        String str = serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0].toString();
	        serviceClient.cleanupTransport();  //为了防止连接超时
	        return str;
		} catch (Exception e){
			throw e;
		}
	}*/
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		/*//拉卡拉网关地址
		//public static String LAKALA_GATEWAY_NEW=Constants.testUrl;
		public static String PENGYUAN_RUL="http://www.pycredit.com:8001/services/WebServiceSingleQuery";
		
		public static String METHOD_NAME="queryReport";
		public static String PACKAGE_NAME="http://batoffline.report.szpcs.scrc.com";
		
	   //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		// 合作身份者ID，注册时得到
		public static String USERID = "qljrwsquery";
		
		public static String PASSWORD = "{MD5}Sr4qlL9Wq/Rg/gwf51CEOQ==";*/
		
		//请求参数 name documentNo accountNo
//		try {
//			String userID="qljrwsquery";
//			String password="{MD5}Sr4qlL9Wq/Rg/gwf51CEOQ==";
//			String queryCondition = null;
//			String[] params = null;
//			Document doc = DocumentHelper.createDocument();
//			doc.setXMLEncoding("GBK");
//			Element root = doc.addElement("conditions");
//			Element condition = root.addElement("condition ").addAttribute("queryType", "25173");
//
//			Element item = condition.addElement("item");
//			item.addElement("name").addText("name");
//			item.addElement("value").addText("陶进");
//
//
//			Element itemdocumentNo = condition.addElement("item");
//			itemdocumentNo.addElement("name").addText("documentNo");
//			itemdocumentNo.addElement("value").addText("430221199109055915");
//
//
//
//			Element itemaccountNo = condition.addElement("item");
//			itemaccountNo.addElement("name").addText("accountNo");
//			itemaccountNo.addElement("value").addText("6217920105532274");
//
//			Element itemsubreportIDs = condition.addElement("item");
//			itemsubreportIDs.addElement("name").addText("subreportIDs");
//			itemsubreportIDs.addElement("value").addText("14500");
//
//
//
//			String conditionStr = doc.asXML();
//			params = new String[] {userID,password,conditionStr,"xml"};
//			String result = WsClientUtil.getWsResult("http://www.pycredit.com:8001/services/WebServiceSingleQuery", "queryReport", "http://batoffline.report.szpcs.scrc.com", params);
//			System.out.println(result);
//
//
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	

	
}