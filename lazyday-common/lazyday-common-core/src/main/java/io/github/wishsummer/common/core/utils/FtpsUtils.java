package io.github.wishsummer.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FtpsUtils {


    /**
     * 建立 ftps连接
     *
     * @param ftpIp    ftpIP
     * @param port     ftp 端口
     * @param username 用户名
     * @param password 密码
     * @throws IOException
     */
    public static FTPSClient connect(String ftpIp, int port, String username,
                                     String password) throws IOException {
        FTPSClient ftp = new FTPSClient();

        ftp.connect(ftpIp, port);
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            if (ftp.login(username, password)) {
                ftp.enterLocalPassiveMode();// 被动模式
                // 设置以二进制方式传输
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.setControlEncoding("UTF-8");
                ftp.setBufferSize(1024 * 1024);
                ftp.setDataTimeout(30000);
                ftp.execPBSZ(0);
                // Set data channel protection to private
                ftp.execPROT("P");
            }
        }
        return ftp;
    }

    /**
     * 传输文件到当前目录下
     */
    public static void upload(FTPSClient ftp, File file) throws Exception {
        upload(ftp, file, null);
    }


    /**
     * ftps 上传文件
     * @param targetPath 进入文件传输目录
     * @param ftp  ftp连接
     * @param file 文件
     * @throws Exception
     */
    public static void upload(FTPSClient ftp, File file, String targetPath) throws Exception {
        if (file == null || !file.isFile()) {
            return;
        }

        if (targetPath != null && !ftp.changeWorkingDirectory(targetPath)) {
            //根目录不存在，则创建文件夹
            ftp.makeDirectory(targetPath);
            ftp.changeWorkingDirectory(targetPath);
        }

        try (InputStream input = new FileInputStream(file)) {

            boolean loadStatus = ftp.storeFile(file.getName(), input);
            if (loadStatus) {
                log.info(file.getAbsolutePath() + " 上传成功！");
            }
        }
    }
}
