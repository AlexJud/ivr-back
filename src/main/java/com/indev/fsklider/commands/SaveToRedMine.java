package com.indev.fsklider.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.dto.IssueBox;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.services.HttpHelper;
import com.indev.fsklider.utils.Utils;
import com.taskadapter.redmineapi.bean.Issue;

public class SaveToRedMine implements Executable {

    private IssueBox issueBox;
    private String options;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpHelper httpHelper = new HttpHelper();


    public SaveToRedMine(String options) {
        this.options = options;
    }


    @Override
    public boolean execute(Incoming asterisk, Dialog node) {


        issueBox = new IssueBox(Utils.replaceVar(options, asterisk.getBuilder().getVariableMap()));
        try {
            String send = mapper.writeValueAsString(issueBox);
            System.out.println("JSON " + send);
            System.out.println("REDMINE" + httpHelper.postRedMine("https://redmine.indev.studio/issues.json", send, String.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return true;
    }

}
