package cn.cookie.framework.web.servlet.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * response wrapper
 * 
 * @author zhoushengzong
 *
 */
public class EncryptedResponseWrapper extends HttpServletResponseWrapper {
	private ByteArrayOutputStream cacheStream;
	private PrintWriter bufferWriter;
	private ServletOutputStreamWrapper out;
	
	public EncryptedResponseWrapper(HttpServletResponse response) {
		super(response);
		this.cacheStream = new ByteArrayOutputStream();
		this.bufferWriter = new PrintWriter(this.cacheStream);
		this.out = new ServletOutputStreamWrapper(this.cacheStream);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return this.bufferWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		super.getOutputStream();
		return this.out;
	} 
	
	public String getResult(){
		try {
			return new String(this.cacheStream.toByteArray(), super.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

class ServletOutputStreamWrapper extends ServletOutputStream{
	private OutputStream cache;
	public ServletOutputStreamWrapper(OutputStream out){
		super();
		this.cache = out;
	}

	@Override
	public void write(int b) throws IOException {
		this.cache.write(b);
	}
}