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
    public String grammar() {
        return "http://localhost/theme:graph";
    }

    @Override
    public String customOptions() {
        return "b=0&t=5000&nit=5000";
    }
}
