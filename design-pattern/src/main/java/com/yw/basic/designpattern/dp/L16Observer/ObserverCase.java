package com.yw.basic.designpattern.dp.L16Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式：天气预报项目
 *
 * @author yangwei
 */
public class ObserverCase {
    public static void main(String[] args) {
//        // 创建接入方 currentConditions
//        CurrentConditions conditions = new CurrentConditions();
//        // 创建 WeatherData 并将 接入方 currentConditions 传递到 WeatherData 中
//        WeatherData weatherData = new WeatherData(conditions);
//        // 更新天气情况
//        weatherData.setData(30, 150, 40);
//
//        //天气情况变化
//        System.out.println("============天气情况变化=============");
//        weatherData.setData(40, 160, 20);

        // 创建一个 WeatherData
        WeatherData weatherData = new WeatherData();
        // 创建观察者
        CurrentCondition currentConditions = new CurrentCondition();
        BaiduSite baiduSite = new BaiduSite();

        // 注册到 weatherData
        weatherData.registerObserver(currentConditions);
        weatherData.registerObserver(baiduSite);

        // 测试
        System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);

        // 测试移除
        weatherData.removeObserver(currentConditions);
        System.out.println(); System.out.println("通知各个注册的观察者, 看看信息");
        weatherData.setData(10f, 100f, 30.3f);
    }
}
///**
// * 显示当前天气情况(可以理解成是气象站自己的网站)
// */
//class CurrentConditions {
//    /**
//     * 温度、气压、湿度
//     */
//    private float temperature;
//    private float pressure;
//    private float humidity;
//    /**
//     * 更新 天气情况，是由 WeatherData 来调用 ==>> 使用推送模式
//     */
//    public void update(float temperature, float pressure, float humidity) {
//        this.temperature = temperature;
//        this.pressure = pressure;
//        this.humidity = humidity;
//
//        display();
//    }
//    /**
//     * 显示
//     */
//    private void display() {
//        System.out.println("***Today temperature: " + temperature + "***");
//        System.out.println("***Today pressure: " + pressure + "***");
//        System.out.println("***Today humidity: " + humidity + "***");
//    }
//}
///**
// * 类是核心
// * 1. 包含最新的天气情况信息
// * 2. 含有 CurrentConditions 对象
// * 3. 当数据有更新时，就主动的调用 CurrentConditions 对象 update 方法(含 display), 这样他们(接入方)就看到最新的信息
// */
//class WeatherData {
//    private float temperatrue;
//    private float pressure;
//    private float humidity;
//    /**
//     * 加入新的第三方
//     */
//    private CurrentConditions currentConditions;
//    public WeatherData(CurrentConditions currentConditions) {
//        this.currentConditions = currentConditions;
//    }
//    /**
//     * 当数据有更新时，就调用 setData
//     */
//    public void setData(float temperature, float pressure, float humidity) {
//        this.temperatrue = temperature;
//        this.pressure = pressure;
//        this.humidity = humidity;
//        //调用 dataChange， 将最新的信息 推送给 接入方 currentConditions
//        dataChange();
//    }
//    private void dataChange() {
//        //调用接入方的 update
//        currentConditions.update(getTemperature(), getPressure(), getHumidity());
//    }
//    private float getTemperature() {
//        return temperatrue;
//    }
//    private float getPressure() {
//        return pressure;
//    }
//    private float getHumidity() {
//        return humidity;
//    }
//}

/***************************************方案改进***********************************/

/**
 * 目标接口
 */
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}

/**
 * 观察者接口
 */
interface Observer {
    void update(float temperature, float pressure, float humidity);
}

/**
 * 具体目标-核心：天气信息
 * 1. 包含最新的天气情况信息
 * 2. 含有 观察者集合，使用 ArrayList 管理
 * 3. 当数据有更新时，就主动的调用 ArrayList, 通知所有的(接入方)就看到最新的信息
 */
class WeatherData implements Subject {
    private float temperatrue;
    private float pressure;
    private float humidity;
    /**
     * 观察者集合
     */
    private List<Observer> observers = new ArrayList<>();

    public void setData(float temperature, float pressure, float humidity) {
        this.temperatrue = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        //调用 dataChange， 将最新的信息 推送给 接入方 currentConditions
        dataChange();
    }
    private void dataChange() {
        notifyObservers();
    }
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperatrue, pressure, humidity);
        }
    }
}

/**
 * 具体观察者
 */
class CurrentCondition implements Observer {
    /**
     * 温度、气压、湿度
     */
    private float temperature;
    private float pressure;
    private float humidity;
    /**
     * 更新 天气情况，是由 WeatherData 来调用 ==>> 使用推送模式
     */
    @Override
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;

        display();
    }
    /**
     * 显示
     */
    private void display() {
        System.out.println("====气象局====");
        System.out.println("***Today temperature: " + temperature + "***");
        System.out.println("***Today pressure: " + pressure + "***");
        System.out.println("***Today humidity: " + humidity + "***");
    }
}

class BaiduSite implements Observer {
    private float temperature;
    private float pressure;
    private float humidity;
    @Override
    public void update(float temperature, float pressure, float humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }
    private void display() {
        System.out.println("====百度网站====");
        System.out.println("***百度网站 气温 : " + temperature + "***");
        System.out.println("***百度网站 气压: " + pressure + "***");
        System.out.println("***百度网站 湿度: " + humidity + "***");
    }
}