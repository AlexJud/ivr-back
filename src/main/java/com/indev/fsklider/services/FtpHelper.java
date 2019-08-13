package com.indev.fsklider.services;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FtpHelper {
    private FTPClient ftp = new FTPClient();
    @Value("${ftp.server.host}")
    private String host;
    @Value("${ftp.server.username}")
    private String username;
    @Value("${ftp.server.password}")
    private String password;

    public void sendFile(String fileName, FileInputStream fis) {
        try {
            ftp.connect(host);
            ftp.login(username, password);
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory("grammars");
            System.out.println(fileName);
            System.out.println(new FileReader(fis.getFD()).read());
            System.out.println(ftp.storeFile(fileName, fis));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
