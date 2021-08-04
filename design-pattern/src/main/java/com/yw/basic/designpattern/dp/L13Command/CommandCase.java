package com.yw.basic.designpattern.dp.L13Command;

/**
 * 命令模式：智能生活项目
 *
 * @author yangwei
 */
public class CommandCase {
    public static void main(String[] args) {
        // 创建点灯对象(接收者)
        LightReceiver receiver = new LightReceiver();
        // 创建点灯相关的开关命令
        LightOnCommand lightOnCommand = new LightOnCommand(receiver);
        LightOffCommand lightOffCommand = new LightOffCommand(receiver);
        // 创建一个遥控器
        RemoteController remoteController = new RemoteController();
        // 给遥控器设置命令
        remoteController.setCommand(0, lightOnCommand, lightOffCommand);

        System.out.println("测试遥控器");
        System.out.println("--------按下灯的开按钮--------");
        remoteController.onButtonWasPushed(0);
        System.out.println("--------按下灯的关按钮--------");
        remoteController.offButtonWasPushed(0);
        System.out.println("--------按下撤销按钮--------");
        remoteController.undoButtonWasPushed();
    }
}

/**
 * 命令接口
 */
interface Command {
    void execute();
    void undo();
}

/**
 * 命令接收者
 */
class LightReceiver {
    public void on() {
        System.out.println("点灯打开了...");
    }
    public void off() {
        System.out.println("点灯关闭了...");
    }
}

/**
 * 具体命令
 */
class LightOnCommand implements Command {
    /**
     * 聚合点灯命令接收者
     */
    private LightReceiver receiver;
    LightOnCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.on();
    }
    @Override
    public void undo() {
        receiver.off();
    }
}

class LightOffCommand implements Command {
    /**
     * 聚合点灯命令接收者
     */
    private LightReceiver receiver;
    LightOffCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.off();
    }
    @Override
    public void undo() {
        receiver.on();
    }
}

/**
 * 空命令:用于初始化每个按钮，当调用空命令时，对象什么都不做
 * 其实这也是一种设计模式，可以省掉对空的判断
 */
class NoCommand implements Command {
    @Override
    public void execute() { }
    @Override
    public void undo() { }
}

/**
 * 命令的调用者/发布者：遥控器
 */
class RemoteController {
    private Command[] onCommands;
    private Command[] offCommands;
    /**
     * 撤销命令
     */
    private Command undoCommand;
    RemoteController() {
        onCommands = initCommand(5);
        offCommands = initCommand(5);
    }
    private Command[] initCommand(int num) {
        Command[] commands = new Command[num];
        for (int i = 0; i < num; i++) {
            commands[i] = new NoCommand();
        }
        return commands;
    }
    /**
     * 给遥控器按钮设置需要的命令
     */
    public void setCommand(int no, Command onCommand, Command offCommand) {
        onCommands[no] = onCommand;
        offCommands[no] = offCommand;
    }
    /**
     * 按下开按钮
     */
    public void onButtonWasPushed(int no) {
        // 找到你按下的 开 的按钮，并调用对应的方法
        onCommands[no].execute();
        // 记录这次操作，用于撤销
        undoCommand = onCommands[no];
    }
    /**
     * 按下关按钮
     */
    public void offButtonWasPushed(int no) {
        // 找到你按下的 关 的按钮，并调用对应的方法
        offCommands[no].execute();
        // 记录这次操作，用于撤销
        undoCommand = offCommands[no];
    }
    /**
     * 按下撤销按钮
     */
    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}
