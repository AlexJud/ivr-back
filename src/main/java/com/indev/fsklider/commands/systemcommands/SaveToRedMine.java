package com.indev.fsklider.commands.systemcommands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.dto.IssueDTO;
import com.indev.fsklider.services.GraphBuilderService;
import com.indev.fsklider.models.Executable;
import com.indev.fsklider.models.Dialog;
import com.indev.fsklider.services.HttpService;
import com.indev.fsklider.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor
public class SaveToRedMine implements Executable {

    private IssueDTO issueDTO;
    private String options;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpService httpService = new HttpService();

    @Override
    public boolean execute(Incoming asterisk, Dialog node, GraphBuilderService graph) {


        issueDTO = new IssueDTO(Utils.replaceVar(options, graph.getVariableMap()));
        try {
            mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            String send = mapper.writeValueAsString(issueDTO);
            log.debug("REDMINE" + httpService.postRedMine("http://redmine.indev/issues.json", send, String.class));

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
