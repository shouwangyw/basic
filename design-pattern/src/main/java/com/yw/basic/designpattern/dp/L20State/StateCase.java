package com.yw.basic.designpattern.dp.L20State;

import lombok.Data;

import java.util.Random;

/**
 * 状态模式：APP抽奖活动问题
 *
 * @author yangwei
 */
public class StateCase {
    public static void main(String[] args) {
        // 创建活动对象，奖品池有2个奖品
        RaffleActivity activity = new RaffleActivity(2);
        for (int i = 0; i < 100; i++) {
            System.out.println("-----第" + (i + 1) + "次抽奖-----");
            // 参加抽奖，第一步点击扣除积分
            activity.deductMoney();
            // 第二步抽奖
            activity.raffle();
        }
    }
}

/**
 * 抽象状态类
 */
abstract class State {
    /**
     * 扣除积分
     */
    abstract void deductMoney();
    /**
     * 是否抽中奖品
     */
    abstract boolean raffle();
    /**
     * 发放奖品
     */
    abstract void dispensePrize();
}

@Data
class RaffleActivity {
    /**
     * 表示活动当前的状态，是变化的
     */
    private State state;
    /**
     * 奖品数量
     */
    private int count;
    /**
     * 四个属性，表示四种状态
     */
    private State noRaffleState = new NoRaffleState(this);
    private State canRaffleState = new CanRaffleState(this);
    private State dispenseState = new DispenseState(this);
    private State dispenseOutState = new DispenseOutState(this);
    /**
     * 初始化当前的状态为 noRafflleState(即不能抽奖的状态)
     * 初始化奖品的数量
     */
    public RaffleActivity(int count) {
        this.state = getNoRaffleState();
        this.count = count;
    }
    /**
     * 扣分，调用当前状态为deductMoney
     */
    public void deductMoney() {
        state.deductMoney();
    }
    /**
     * 抽奖
     */
    public void raffle() {
        // 如果当前的状态是抽奖成功
        if (state.raffle()) {
            // 领取奖品
            state.dispensePrize();
        }
    }
    /**
     * 这里注意，每领取一次奖品，count--
     */
    public int getCount() {
        return count--;
    }
}

/**
 * 不能抽奖的状态
 */
class NoRaffleState extends State {
    private RaffleActivity activity;
    NoRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }
    /**
     * 当前状态可以扣积分，扣除积分后，将状态设置为可以抽奖状态
     */
    @Override
    void deductMoney() {
        System.out.println("扣除50积分成功，您可以抽奖了");
        activity.setState(activity.getCanRaffleState());
    }
    /**
     * 当前在不能抽奖状态
     */
    @Override
    boolean raffle() {
        System.out.println("扣了积分才能抽奖哦！");
        return false;
    }
    @Override
    void dispensePrize() {
        System.out.println("不能发放奖品");
    }
}

/**
 * 可以抽奖的状态
 */
class CanRaffleState extends State {
    private RaffleActivity activity;
    CanRaffleState(RaffleActivity activity) {
        this.activity = activity;
    }
    /**
     * 已经扣除了积分，不能再扣
     */
    @Override
    void deductMoney() {
        System.out.println("已经扣除过积分了");
    }
    /**
     * 可以抽奖, 抽完奖后，根据实际情况，改成新的状态
     */
    @Override
    boolean raffle() {
        System.out.println("正在抽奖，请稍等");
        int num = new Random().nextInt(10);
        // 10%中奖机会
        if (num == 0) {
            // 改变活动状态为发放奖品
            activity.setState(activity.getDispenseState());
            return true;
        } else {
            System.out.println("很遗憾没有抽中奖品");
            activity.setState(activity.getNoRaffleState());
            return false;
        }
    }
    /**
     * 不能发放奖品
     */
    @Override
    void dispensePrize() {
        System.out.println("没中奖，不能发放奖品");
    }
}

/**
 * 发放奖品的状态
 */
class DispenseState extends State {
    private RaffleActivity activity;
    DispenseState(RaffleActivity activity) {
        this.activity = activity;
    }
    @Override
    void deductMoney() {
        System.out.println("不能扣除积分");
    }

    @Override
    boolean raffle() {
        System.out.println("不能抽奖");
        return false;
    }
    /**
     * 发放奖品
     */
    @Override
    void dispensePrize() {
        if (activity.getCount() > 0) {
            System.out.println("恭喜中奖了");
            // 改变状态为不能抽奖
            activity.setState(activity.getNoRaffleState());
        } else {
            System.out.println("很遗憾，奖品发放完了");
            // 改变状态为奖品发送完毕，后面就不能发放奖品了
            activity.setState(activity.getDispenseState());
        }
    }
}

/**
 * 奖品发放完毕的状态
 * 当我们 activity 改变成 DispenseOutState， 抽奖活动结束
 */
class DispenseOutState extends State {
    private RaffleActivity activity;
    DispenseOutState(RaffleActivity activity) {
        this.activity = activity;
    }
    @Override
    void deductMoney() {
        System.out.println("奖品发放完了，请下次再参加");
    }

    @Override
    boolean raffle() {
        System.out.println("奖品发放完了，请下次再参加");
        return false;
    }

    @Override
    void dispensePrize() {
        System.out.println("奖品发放完了，请下次再参加");
    }
}