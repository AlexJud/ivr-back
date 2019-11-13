package com.indev.fsklider.commands.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.dto.Issue;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.nodes.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.services.HttpHelper;
import com.indev.fsklider.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor
public class SaveToRedMine implements Executable {

    private Issue issue;
    private String options;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpHelper httpHelper = new HttpHelper();

    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilder graph) {


        issue = new Issue(Utils.replaceVar(options, graph.getVariableMap()));
        try {
            mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            String send = mapper.writeValueAsString(issue);
            log.debug("REDMINE" + httpHelper.postRedMine("http://redmine.indev/issues.json", send, String.class));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public String getDescription() {
        return "Сохранить в Redmine";
    }

    @Override
    public void setOptions(String options) {
        this.options = options;
    }

    //    public static void main(String[] args) throws JsonProcessingException {
//        Issue is = new Issue("Hello maxim");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        String j = mapper.writeValueAsString(is);
//        System.out.println(j);
//    }

//    public static void main(String[] args) {
//        SaveToRedMine s = new SaveToRedMine("3");
//        System.out.println(s.getDescription());
//    }


}
