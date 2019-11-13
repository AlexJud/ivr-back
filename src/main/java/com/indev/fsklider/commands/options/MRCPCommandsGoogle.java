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

}
