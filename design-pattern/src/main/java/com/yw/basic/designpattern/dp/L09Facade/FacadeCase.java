package com.yw.basic.designpattern.dp.L09Facade;

/**
 * 外观模式：影院管理
 *
 * @author yangwei
 */
public class FacadeCase {
    public static void main(String[] args) {
        // 如果直接调用，很不方便
        HomeTheaterFacade facade = new HomeTheaterFacade();
        facade.ready();
        facade.play();
        facade.end();
    }
}

class DVDPlayer {
    private DVDPlayer() {}
    private static DVDPlayer INSTANCE = new DVDPlayer();
    public static DVDPlayer getInstance() {
        return INSTANCE;
    }
    public void on() {
        System.out.println("DVD on");
    }
    public void off() {
        System.out.println("DVD off");
    }
    public void play() {
        System.out.println("DVD playing");
    }
    public void pause() {
        System.out.println("DVD paused");
    }
}

class Popcorn {
    private Popcorn() {}
    private static Popcorn INSTANCE = new Popcorn();
    public static Popcorn getInstance() {
        return INSTANCE;
    }
    public void on() {
        System.out.println("Popcorn on");
    }
    public void off() {
        System.out.println("Popcorn off");
    }
    public void pop() {
        System.out.println("Popcorn popping");
    }
}

class Projector {
    private Projector() {}
    private static Projector INSTANCE = new Projector();
    public static Projector getInstance() {
        return INSTANCE;
    }
    public void on() {
        System.out.println("Projector on");
    }
    public void off() {
        System.out.println("Projector off");
    }
    public void focus() {
        System.out.println("Projector focus");
    }
}

class Screen {
    private Screen() {}
    private static Screen INSTANCE = new Screen();
    public static Screen getInstance() {
        return INSTANCE;
    }
    public void up() {
        System.out.println("Screen up");
    }
    public void down() {
        System.out.println("Screen down");
    }
}

class Stereo {
    private Stereo() {}
    private static Stereo INSTANCE = new Stereo();
    public static Stereo getInstance() {
        return INSTANCE;
    }
    public void on() {
        System.out.println("Stereo on");
    }
    public void off() {
        System.out.println("Stereo off");
    }
    public void up() {
        System.out.println("Stereo up");
    }
    public void down() {
        System.out.println("Stereo down");
    }
}

class TheaterLight {
    private TheaterLight() {}
    private static TheaterLight INSTANCE = new TheaterLight();
    public static TheaterLight getInstance() {
        return INSTANCE;
    }
    public void on() {
        System.out.println("TheaterLight on");
    }
    public void off() {
        System.out.println("TheaterLight off");
    }
    public void dim() {
        System.out.println("TheaterLight dim");
    }
    public void bright() {
        System.out.println("TheaterLight bright");
    }
}

class HomeTheaterFacade {
    /**
     * 定义各个子系统的对象
     */
    private DVDPlayer dvdPlayer;
    private Popcorn popcorn;
    private Projector projector;
    private Screen screen;
    private Stereo stereo;
    private TheaterLight theaterLight;

    public HomeTheaterFacade() {
        this.dvdPlayer = DVDPlayer.getInstance();
        this.popcorn = Popcorn.getInstance();
        this.projector = Projector.getInstance();
        this.screen = Screen.getInstance();
        this.stereo = Stereo.getInstance();
        this.theaterLight = TheaterLight.getInstance();
    }

    public void ready(){
        popcorn.on();
        popcorn.pop();
        screen.down();
        projector.on();
        stereo.on();
        dvdPlayer.on();
        theaterLight.dim();
    }

    public void play() {
        dvdPlayer.play();
    }

    public void pause() {
        dvdPlayer.pause();
    }

    public void end() {
        popcorn.off();
        screen.up();
        projector.off();
        stereo.off();
        dvdPlayer.off();
        theaterLight.off();
    }
}