package com.yw.basic.designpattern.dp.L10Flyweight;

/**
 * 享元模式在JDK-Interger的应用源码分析
 *
 * @author yangwei
 */
public class JDKFlyweight {
    public static void main(String[] args) {
        /*
         如果 Integer.valueOf(x) x 在 -127~128 之间，就是使用的享元模式返回
         public static Integer valueOf(int i) {
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
         }
         */
        Integer x = Integer.valueOf(127);
        Integer y = new Integer(127);
        Integer z = Integer.valueOf(127);
        Integer w = new Integer(127);


        System.out.println(x.equals(y));    // true
        System.out.println(x == y);         // false
        System.out.println(x == z);         // true
        System.out.println(w == x);         // false
        System.out.println(w == y);         // fasle

        Integer x1 = Integer.valueOf(200);
        Integer x2 = Integer.valueOf(200);
        System.out.println(x1 == x2);       // false
    }
}
