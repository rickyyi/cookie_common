package cn.cookie.framework.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cookie.framework.web.servlet.wrapper.ParameterRequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.cookie.common.util.pki.RSAUtils;
import cn.cookie.framework.web.servlet.wrapper.EncryptedResponseWrapper;

/**
 * @author zhou shengzong
 * @date:2015-12-18
 * @version :1.0
 * 
 */
public class ApiServlet extends HttpServlet {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	private PrivateKey privateKey;
	
	// 仅取加密参数(忽略明文参数)
	private boolean onlyEncrypted;
	
	public void init(ServletConfig config) throws ServletException {
		logger.debug("enter method");
		InputStream input = config.getServletContext().getResourceAsStream("/WEB-INF/pki/rsa_key_paire_pkcs8.pem");
		this.privateKey = RSAUtils.loadPrivateKey(input);
		this.onlyEncrypted = "1".equals(config.getInitParameter("onlyEncrypted")); // 0 or 1
		logger.debug("exit method");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String URI = req.getParameter("forward");
		logger.debug("forward uri: {}", URI);
		if(StringUtils.isBlank(URI)){
			logger.error("URI is empty");
		}
		
		try {
			//先验签
			if (true) {
				//decrypt
				String reqData = req.getParameter("reqData");
				if(logger.isDebugEnabled()){
					logger.debug("encrypted reqData:{}", reqData);
				}
				
				Map<String, Object> fwParams = new HashMap<String, Object>();
				if(! onlyEncrypted){
					fwParams.putAll(req.getParameterMap());
				}
				
				if(null != reqData){
					reqData =  reqData.replace(' ', '+');
					String dataPlain = RSAUtils.decryptByPrivateKey(reqData, privateKey, req.getCharacterEncoding());
					if(logger.isDebugEnabled()){
						logger.debug("plain reqData:{}", dataPlain);
					}
					JSONObject m = JSONObject.parseObject(dataPlain);
					for(String key : m.keySet()){
						fwParams.put(key, m.get(key));
					}
				}
				
				
				ParameterRequestWrapper reqWrapper = new ParameterRequestWrapper(req, fwParams);
				EncryptedResponseWrapper respWrapper = new EncryptedResponseWrapper(resp);
				
				//String URI = xmlConfig.getApiUrlConfig().get("getLoanInfo");
				RequestDispatcher dispatcher = req.getRequestDispatcher(URI);
				dispatcher.forward(reqWrapper, respWrapper);
				
				String content = respWrapper.getResult(); //plain text
				if(logger.isDebugEnabled()){
					logger.debug("bussiness api return plain:{}", content);
				}
				
				OutputStream out = resp.getOutputStream();
				if(null != content) {
					content = RSAUtils.encryptByPrivateKey(content, privateKey, resp.getCharacterEncoding()); // ASCII string
					if(logger.isDebugEnabled()){
						logger.debug("bussiness api return encrypted:{}", content);
					}
					byte[] data = content.getBytes("ASCII"); //content is ASCII string
					resp.setContentLength(data.length);
					out.write(data);
				}
				
				out.close();
			}
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error("error", e);
		}
	}
}
