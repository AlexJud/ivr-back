package com.indev.fsklider.commands.options;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Properties;
@Log4j
public class MRCPFactory {
    private static MRCPCommands mrcpCommands;
    private static MRCPFactory instanse;

   public static void configure(String connection){
        if (connection.equalsIgnoreCase("vn")){
            mrcpCommands = new MRCPCommandsVoiceNavigator();
        }
        if (connection.equalsIgnoreCase("google")){
            mrcpCommands = new MRCPCommandsGoogle();
        }
    }

    public static MRCPFactory instance(){
       if (instanse == null) {
           instanse = new MRCPFactory();
       }
       return instanse;
    }

    public MRCPCommands commands(){
       return mrcpCommands;
    }

    private MRCPFactory() {
        Properties properties = new Properties();
        try {

//            System.out.println("FACTORY IS IN: - "+ System.getProperty("java.class.path"));
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream stream = classloader.getResourceAsStream("application.properties");
            properties.load(stream);
//            properties.load(new FileReader(System.getProperty("user.dir") + "/src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = properties.getProperty("mrcp.connection");
        log.info("MRCP protocol - connection to: " + value);
        if (value == null ){
            value = "vn";
        }
        configure(value);
//        properties.load(FileInputStream(System.getProperty("user.dir") + "/src/main/resources/application.properties")));
    }
}
