package domain.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件上传工具
 */
public class FileUploader {
    private static Logger log = LoggerFactory.getLogger(FileUploader.class);

    private static final String FILE_SEPARATOR = File.separator;

    //TODO HTTP协议所需信息待补全
    private static final String HTTP_URL = "";
    private static final String HTTP_CONNECTION_CHARSET = "utf-8";

    //TODO FTP服务器信息待补全
    private static final String FTP_SERVER_ADDRESS = "";
    private static final int FTP_SERVICE_PORT = 0;
    private static final String FTP_USER_NAME = "";
    private static final String FTP_USER_PASSWORD = "";
    private static final String FTP_FILE_PATH = FILE_SEPARATOR + "imageYG" + FILE_SEPARATOR + "FOTIC" + FILE_SEPARATOR + "download";
    private static final boolean FTP_IS_PASSIVE_LOCAL_DATA_CONNECTION_MODE = false;
    private static final String FTP_CONTROL_ENCODING = "utf-8";
    private static final int FTP_FILE_TYPE = FTP.BINARY_FILE_TYPE;


    /**
     * HTTP协议-文件上传工具
     * @param target    上传文件
     * @return  true:上传成功   false:上传失败
     */
    public static boolean httpUpload(File target){
        return coreHttpUpload(target);
    }

    /**
     * HTTP协议-文件上传工具
     * @param targetPath    上传文件路径
     * @return true:上传成功  false:上传失败
     */
    public static boolean httpUpload(String targetPath){
        return coreHttpUpload(new File(targetPath));
    }

    private static boolean coreHttpUpload(File target){
        log.info("http-uploading...");
        boolean result = false;
        HttpURLConnection connection;
        try{
            URL url=new URL(HTTP_URL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset="+HTTP_CONNECTION_CHARSET);
            connection.setRequestProperty("content-type", "application/octet-stream");
            BufferedOutputStream out= new BufferedOutputStream(connection.getOutputStream());

            //读取文件并填充到上传数据流
            FileInputStream fileInputStream=new FileInputStream(target);
            final int BUF_LEN_FOR_SEND = 1024;
            byte[] buffer=new byte[BUF_LEN_FOR_SEND];
            int numReadByteForOutput;
            while ((numReadByteForOutput = fileInputStream.read(buffer,0,BUF_LEN_FOR_SEND)) > 0){
                out.write(buffer,0,numReadByteForOutput);
            }
            out.flush();
            out.close();
            fileInputStream.close();

            //上传完毕接收远程端响应
            DataInputStream responseInputStream = new DataInputStream(connection.getInputStream());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final int BUF_LEN_FOR_SAVE = 8192;
            buffer = new byte[BUF_LEN_FOR_SAVE];
            int numReadByteForInput = 0;
            while (-1 != (numReadByteForInput = responseInputStream.read(buffer,0,BUF_LEN_FOR_SAVE))){
                byteArrayOutputStream.write(buffer,0,numReadByteForInput);
            }
            log.info("upload success,recived response: " + byteArrayOutputStream.size());

            //TODO 上传完毕后是否会受到处理结果报告 未知
            if (byteArrayOutputStream.size() > 0){
                //TODO 响应数据编码格式待定
                String strMessage = new String(byteArrayOutputStream.toByteArray(),HTTP_CONNECTION_CHARSET);
                if (!strMessage.contains("<RETCODE>0000</RETCODE>")){
                    log.error("upload fail");
                }else {
                    result = true;
                    log.info("uploaded completed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * FTP协议-文件上传工具
     * @param sourceFilePath    本地待上传文件路径
     * @param targetFileName    远程上传文件名
     * @return true:成功  false:失败
     */
    public static boolean ftpUpload(String sourceFilePath,String targetFileName){
        return coreFtpUpload(new File(sourceFilePath),targetFileName);
    }

    /**
     * FTP协议-文件上传工具
     * @param sourceFile    本地待上传文件
     * @param targetFileName    远程上传文件名
     * @return true:成功  false:失败
     */
    public static boolean ftpUpload(File sourceFile,String targetFileName){
        return coreFtpUpload(sourceFile,targetFileName);
    }

    private static boolean coreFtpUpload(File sourceFile, String targetFileName) {
        log.info("ftp-uploading...");
        InputStream input = null;
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(FTP_SERVER_ADDRESS, FTP_SERVICE_PORT);
            int reply = ftpClient.getReplyCode();
            log.info("FTP server reply is:" + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new IOException("FTP server refused connection.");
            }
            if (!ftpClient.login(FTP_USER_NAME, FTP_USER_PASSWORD)) {
                throw new IOException("FTP server login failed.");
            }
            if (FTP_IS_PASSIVE_LOCAL_DATA_CONNECTION_MODE) {
                ftpClient.enterLocalPassiveMode();
            }
            ftpClient.setControlEncoding(FTP_CONTROL_ENCODING);
            ftpClient.setFileType(FTP_FILE_TYPE);
            // 更变目录路径
            if (!ftpClient.changeWorkingDirectory(FTP_FILE_PATH)) {
                // 创建目录
                if (!ftpClient.makeDirectory(FTP_FILE_PATH)) {
                    log.error("failed to create directory on server");
                    throw new IOException("failed to create directory on server");
                } else {
                    ftpClient.changeWorkingDirectory(FTP_FILE_PATH);
                }
            }
//            log.info("file path of ftp server:" +FTP_FILE_PATH);
            input = new FileInputStream(sourceFile);
            ftpClient.enterLocalPassiveMode();
            boolean returnValue = ftpClient.storeFile(targetFileName, input);
            ftpClient.logout();
            if (returnValue){
                log.info("uploaded completed");
            }else {
                log.error("upload failed");
            }
            return returnValue;
        } catch (Exception e) {
            log.error("error message:" + e);
            log.error("upload file failed.", e);
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("error message:" + e);
                    log.error("stream close exception", e);
                }
            }
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("error message:" + e);
                    log.error("FTP server disconnect exception", e);
                }
            }
        }
    }

}
