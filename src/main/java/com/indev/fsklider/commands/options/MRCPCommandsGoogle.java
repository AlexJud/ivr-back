package com.indev.fsklider.commands.options;

public class MRCPCommandsGoogle implements MRCPCommands {

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
        return "builtin:speech/transcribe";
    }

    @Override
    public String customOptions() {
        return "builtin:speech/transcribe, b=0&t=5000&nit=5000";
    }
}
