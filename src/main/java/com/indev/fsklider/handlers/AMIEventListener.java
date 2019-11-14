package com.indev.fsklider.handlers;

import org.asteriskjava.manager.ManagerEventListener;
import org.asteriskjava.manager.event.ManagerEvent;

public class AMIEventListener implements ManagerEventListener {
    @Override
    public void onManagerEvent(ManagerEvent managerEvent) {
        System.out.println("MANAGER ======== Seq "+ managerEvent.getSequenceNumber());
        System.out.println(" LineNum "+ managerEvent.getConnectedLineNum());
        System.out.println(" LIne "+ managerEvent.getLine());
        System.out.println(" source "+ managerEvent.getSource().toString());
        System.out.println(" exten "+ managerEvent.getExten());
        System.out.println(" +++++++++++++++++ ");
        System.out.println(managerEvent);
        System.out.println(" //////////////////");
    }
}
