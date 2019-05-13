package com.indev.fsklider.agiscripts;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.springframework.stereotype.Service;

@Service
public class GoogleServices extends BaseAgiScript {
    @Override
    public void service(AgiRequest agiRequest, AgiChannel agiChannel) throws AgiException {
        answer();
        exec("MRCPSynth","Привет дружок,l=ru-RU&p=uni2");
        exec("MRCPRecog", "builtin:speech/transcribe,spl=ru-RU&f=beep&p=uni2");
//        speechCreate();
//        speechActivateGrammar("builtin:speech/transcribe");
//        speechDestroy();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getVariable("RECOGSTATUS"));
        System.out.println(getVariable("RECOG_COMPLETION_CAUSE"));
        System.out.println(getVariable("RECOG_INPUT(0)"));
//        exec("Verbose", "1, status: ${RECOGSTATUS}, completion-cause: ${RECOG_COMPLETION_CAUSE}, result: ${RECOG_RESULT}");
        hangup();

    }
}
