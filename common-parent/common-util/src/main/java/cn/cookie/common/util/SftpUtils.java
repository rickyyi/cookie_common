package cn.cookie.common.util;


import com.jcraft.jsch.*;

import java.io.*;
import java.util.*;

/**
 * Created by gxx on 2016-06-08.
 */
public class SftpUtils {

    public static ChannelSftp sftp=null;

    /*****
     * @author gxx
     * @param url  sftp地址
     * @param port   端口号
     * @param userName  用户名
     * @param passWord   密码
     *   连接sftp
     */
    public static boolean connect(String url,int port,String userName,String passWord){
        boolean connFlag=false;
        try {
            JSch jSch=new JSch();
            Session sshSession=jSch.getSession(userName,url,port);
            sshSession.setPassword(passWord);
            Properties sshConfig=new Properties();
            sshConfig.put("StrictHostKeyChecking","no");
            sshSession.setConfig(sshConfig);
            sshSession.connect(20000);
            Channel channel=sshSession.openChannel("sftp");
            channel.connect();
            sftp=(ChannelSftp) channel;
            connFlag=true;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            return connFlag;
        }
    }


    /******
     * @author gxx
     * @param path  sftp目录
     * @param fileName   上传文件的名称
     * @param inputStream  输入流
     * @return
     * 上传文件(sftp)
     */
    public static boolean uploadFile(String path, String fileName, InputStream inputStream){
        boolean uploadFlag=false;
        try {
            sftp.cd(path);
            sftp.put(inputStream, fileName);
            uploadFlag=true;
        } catch (SftpException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            return uploadFlag;
        }
    }

    /**
     * 下载单个文件
     *
     * @param path
     *            下载目录
     * @param pathFile
     *            下载的文件
     * @param savePath
     *            存在本地的路径
     *
     * @throws Exception
     */
    public static boolean downLoadFile(String path, String pathFile, String savePath) {
        boolean downLoadFalg=false;
        try {
            String saveFile = savePath + "//" + pathFile;
            sftp.cd(path);
            File file = new File(saveFile);
            sftp.get(pathFile, new FileOutputStream(file));
            downLoadFalg=true;
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            return downLoadFalg;
        }
    }

    /**
     * @author gxx
     * 删除文件
     *
     * @param path
     *            要删除文件所在目录
     * @param deleteFile
     *            要删除的文件
     *
     * @throws Exception
     */
    public static boolean deleteFile(String path, String deleteFile) {
        boolean deleteFlag=false;
        try {
            sftp.cd(path);
            sftp.rm(deleteFile);
            deleteFlag=true;
        } catch (SftpException e) {
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            return deleteFlag;
        }
    }

    /**
     * @author gxx
     * 下载目录下全部文件
     *
     * @param path
     *            下载目录
     *
     * @param savePath
     *            存在本地的路径
     *
     * @throws Exception
     */
    public static boolean downLoadAllFiles(String path, String savePath) {
        String downloadFile = "";
        List<String> downloadFileList = listFiles(path);
        if(downloadFileList==null||downloadFileList.size()==0){
            return false;
        }
        try {
            Iterator<String> it = downloadFileList.iterator();
            while(it.hasNext())
            {
                downloadFile = it.next().toString();
                if(downloadFile.toString().indexOf(".")<0){
                    continue;
                }
                downLoadFile(path, downloadFile, savePath);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * 列出目录下的文件
     *
     * @param path
     *            要列出的目录
     *
     * @return list 文件名列表
     *
     * @throws Exception
     */
    public static List<String> listFiles(String path) {
        Vector fileList;
        try {
            List<String> fileNameList = new ArrayList();
            fileList = sftp.ls(path);
            Iterator it = fileList.iterator();
            while(it.hasNext())
            {
                String fileName = ((ChannelSftp.LsEntry)it.next()).getFilename();
                if(".".equals(fileName) || "..".equals(fileName)){
                    continue;
                }
                fileNameList.add(fileName);
            }
            return fileNameList;
        } catch (SftpException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    /******
     *@author  gxx
     * @param sInputString  字符串
     * @return
     * 字符串转换成输入流
     */
    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
                }catch (Exception ex){
                ex.printStackTrace();
                }
            }
        return null;
    }


   /* public static void readTxtFile(String filePath)*//*{
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String columnLine=lineTxt.substring(0,lineTxt.length()-1);
                    System.out.println(columnLine);
                    String colums[]=columnLine.split("\\|");
                      for (String colum:colums){
                          System.out.println(colum);
                      }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }*/

}
