package cn.chuanwise.panda.command;

import cn.chuanwise.command.Commander;

public abstract class PandaCommander
    extends Commander {

    protected PandaCommander() {}

    public abstract PlatformCommandBuilder builder();
}
