package io.github.wishsummer.utils;

import io.github.wishsummer.exception.HttpConnectException;
import io.github.wishsummer.exception.HttpInvalidInvalidAccountOrPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FtpUtils {

    public static void upload(String url, String username, String password, List<Path> pathList, String path,
                              int port) throws HttpInvalidInvalidAccountOrPasswordException, HttpConnectException,
            IOException {
        FTPClient ftpClient = getFtpClient(url, username, password, port);

        changeRemoteWorkingDirectory(path, ftpClient);

        /**
         * 设置为二进制传输、防止乱码
         */
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        for (Path p : pathList) {
            storeFile(ftpClient, p.toFile());
        }

        ftpClient.disconnect();
    }

    public static FTPClient getFtpClient(String url, String username, String password, int port) throws IOException, HttpInvalidInvalidAccountOrPasswordException, HttpConnectException {
        /**
         * 配置ftp 连接参数
         */
        FTPClient ftpClient = new FTPClient();
        ftpClient.setDefaultTimeout(60 * 1000);
        ftpClient.setConnectTimeout(60 * 1000);
        ftpClient.setDataTimeout(3 * 60 * 1000);
        ftpClient.setCharset(Charset.forName("UTF-8"));

        try {
            ftpClient.connect(url, port);
        } catch (Exception e) {
            log.error("trying connect ftp server error:", e);
        }

        if (!ftpClient.login(username, password)) {
            throw new HttpInvalidInvalidAccountOrPasswordException("login ftp is wrong with account or password");
        }

        /**
         * 获取ftp最后一次 回复code 确认是否连接成功
         */
        int reply = ftpClient.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
            throw new HttpConnectException("open ftp server fail");
        }

        ftpClient.setBufferSize(1024 * 1024);

        ftpClient.enterLocalPassiveMode();
        return ftpClient;
    }

    /**
     * 进入ftp工作目录
     *
     * @param path      需要进入的远程ftp目录位置
     * @param ftpClient ftp连接对象
     * @throws IOException
     */
    private static void changeRemoteWorkingDirectory(String path, FTPClient ftpClient) throws IOException {
        boolean changeDirectory = ftpClient.changeWorkingDirectory(path);
        if (!changeDirectory) {
            String[] dirs = path.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += File.separator + dir;
                if (!ftpClient.changeWorkingDirectory(tempPath)) {
                    ftpClient.makeDirectory(tempPath);
                    ftpClient.changeWorkingDirectory(tempPath);
                }
            }
        }
    }

    private static void storeFile(FTPClient ftpClient, File file) throws IOException {
        storeFile(ftpClient, file, null, null);
    }


    public static void storeFile(FTPClient ftpClient, File file, String fileNameSuffix, String targetNameSuffix) throws IOException {
        try (FileInputStream input = new FileInputStream(file)) {
            ftpClient.storeFile(file.getName(), input);
            if (fileNameSuffix != null) {
                changeFileName(ftpClient, file, fileNameSuffix, targetNameSuffix);
            }
        }
    }

    private static void changeFileName(FTPClient ftpClient, File file, String fileNameSuffix, String targetNameSuffix) throws IOException {
        if (file.getName().endsWith(fileNameSuffix)) {
            //上传完成以后修改后缀名
            ftpClient.rename(file.getName(), file.getName().replaceAll(fileNameSuffix, targetNameSuffix));
        }
    }
}
