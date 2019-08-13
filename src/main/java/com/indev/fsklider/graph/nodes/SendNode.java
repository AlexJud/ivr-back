package com.indev.fsklider.graph.nodes;

import com.indev.fsklider.graph.nodes.properties.SendProps;
import com.indev.fsklider.services.HttpHelper;

public class SendNode extends Node {
    private SendProps props;

    public SendProps getProps() {
        return props;
    }

    public void setProps(SendProps props) {
        this.props = props;
    }

    private String checkUnconditional() {
        if (getEdgeList() != null) {
            if (getEdgeList().size() == 1) {
                return getEdgeList().get(0).getId();
            }
        }
        return getContext().getPreviousId();
    }

    private String calculateNext() {
        return checkUnconditional();
    }

    @Override
    public String run() {
        System.out.println(getContext().getContextMap());
        HttpHelper http = new HttpHelper();
//        http.doPost(props.getDest(), getContext());
        return calculateNext();
    }
}
