package com.yw.basic.designpattern.dp.L19Interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * 解释器模式:四则运算问题
 *
 * @author yangwei
 */
public class InterpreterCase {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expStr = getExpStr(scanner);
        Map<String, Integer> var = getValue(expStr, scanner);
        Calculator calculator = new Calculator(expStr);
        System.out.println(expStr + "的运算结果：" + calculator.run(var));
    }

    private static String getExpStr(Scanner scanner) {
        System.out.println("请输入表达式：");
        return scanner.nextLine();
    }
    private static Map<String, Integer> getValue(String expStr, Scanner scanner) {
        Map<String, Integer> map = new HashMap<>(8);

        for (char ch : expStr.toCharArray()) {
            if (ch != '+' && ch != '-') {
                String key = String.valueOf(ch);
                if (!map.containsKey(key)) {
                    System.out.println("请输入" + key +"的值");
                    int value = scanner.nextInt();
                    map.put(key, value);
                }
            }
        }

        return map;
    }
}

/**
 * 抽象类表达式，通过Map键值对，可以获取到变量的值
 */
abstract class AbsExpression {
    /**
     * a + b + c
     * 解释公式和值，key就是公式(表达式)参数[a, b, c]，value就是具体值
     * 比如: {a: 10, b: 20}
     */
    abstract int interpret(Map<String, Integer> var);
}

/**
 * 变量的解释器
 */
class VarExpression extends AbsExpression {
    /**
     * 变量的key，key=a,b,c,...
     */
    private String key;
    VarExpression(String key) {
        this.key = key;
    }
    @Override
    int interpret(Map<String, Integer> var) {
        return var.get(this.key);
    }
}

/**
 * 抽象运算符号解释器，这里每个运算符号都只和自己左右两个数字有关系
 * 但左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression的实现类
 */
class SymbolExpression extends AbsExpression {
    protected AbsExpression left;
    protected AbsExpression right;
    SymbolExpression(AbsExpression left, AbsExpression right) {
        this.left = left;
        this.right = right;
    }
    /**
     * 这里是一个空实现，有具体的子类实现
     */
    @Override
    int interpret(Map<String, Integer> var) {
        return 0;
    }
}

/**
 * 加法表达式
 */
class AddExpression extends SymbolExpression {
    AddExpression(AbsExpression left, AbsExpression right) {
        super(left, right);
    }
    @Override
    public int interpret(Map<String, Integer> var) {
        return left.interpret(var) + right.interpret(var);
    }
}

/**
 * 减法表达式
 */
class SubExpression extends SymbolExpression {
    SubExpression(AbsExpression left, AbsExpression right) {
        super(left, right);
    }
    @Override
    public int interpret(Map<String, Integer> var) {
        return left.interpret(var) - right.interpret(var);
    }
}

/**
 * 计算器
 */
class Calculator {
    private AbsExpression expression;
    Calculator(String expStr) {
        Stack<AbsExpression> stack = new Stack<>();
        AbsExpression left, right;
        for (int i = 0; i < expStr.length(); i++) {
            switch (expStr.charAt(i)) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(expStr.charAt(++i)));
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    left =  stack.pop();
                    right = new VarExpression(String.valueOf(expStr.charAt(++i)));
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    stack.push(new VarExpression(String.valueOf(expStr.charAt(i))));
                    break;
            }
        }
        this.expression = stack.pop();
    }
    public int run(Map<String, Integer> var) {
        return this.expression.interpret(var);
    }
}