package cn.cookie.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * Created by gxx on 2016-06-15.
 */
public class SocketUtils {
    static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);

    public static String sendRequest (String xmlInfo,String socketUrl,int socketPort)
    {
        boolean socketIsClosed=true;
        StringBuffer buffer=new StringBuffer();
        String resultString=null;
        try
        {
            logger.info("socket.linking........");
            Socket socket = new Socket(socketUrl,socketPort);
            System.out.println("socketIsClosed:"+socket.isClosed());
            while ((socketIsClosed=socket.isClosed())==false){
                logger.info("socket.link.success");
                logger.info("socket.request.info:[{}]",xmlInfo);
                // 向服务器发消息
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GBK"),true);
                out.println(xmlInfo.toString());
                // 接受服务器端消息
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
                int read=0;
                while ((read=in.read())!=-1){
                    buffer.append(String.valueOf((char) read));
                }
                logger.info("scoket.response.info:[{}]",buffer.toString());
                out.close();
                in.close();
                socket.close();
            }
            return buffer.toString();
        }
        catch (IOException e)
        {
            logger.error("socket.link.Exception",e);
            return null;
        }
    }
}
