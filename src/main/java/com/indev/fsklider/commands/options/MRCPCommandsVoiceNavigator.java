package com.indev.fsklider.commands.options;

public class MRCPCommandsVoiceNavigator implements MRCPCommands {

    @Override
    public String speakAndListen() {
        return "SynthAndRecog";
    }

    @Override
    public String speak() {
        return "MRCPSynth";
    }

    @Override
    public String hangUp() {
        return "Hangup";
    }

    @Override
    public String options() {
        return "http://localhost/theme:graph, b=0&t=5000&nit=5000";
    }
}
