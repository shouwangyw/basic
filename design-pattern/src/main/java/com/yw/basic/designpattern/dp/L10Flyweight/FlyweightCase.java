package com.yw.basic.designpattern.dp.L10Flyweight;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式：网站展现项目
 *
 * @author yangwei
 */
public class FlyweightCase {
    public static void main(String[] args) {
        WebSiteFactory factory = new WebSiteFactory();
        WebSite webSite1 = factory.getWebSite("新闻");
        webSite1.use(new User("张三"));

        WebSite webSite2 = factory.getWebSite("博客");
        webSite2.use(new User("李四"));
        WebSite webSite3 = factory.getWebSite("博客");
        webSite3.use(new User("王五"));

        System.out.println("网站的分类共 " + factory.getWebSiteCount() + " 个");
    }
}

abstract class WebSite {
    abstract void use(User user);
}

class ConcreteWebSite extends WebSite {
    /**
     * 网站类型：内部状态
     */
    private String type;
    ConcreteWebSite(String type) {
        this.type = type;
    }
    @Override
    public void use(User user) {
        System.out.println("网站的发布形式：" + type + "，" + user.getName() + "在使用中...");
    }
}

class WebSiteFactory {
    private Map<String, ConcreteWebSite> pool = new HashMap<>();
    public WebSite getWebSite(String type) {
        if (!pool.containsKey(type)) {
            pool.put(type, new ConcreteWebSite(type));
        }
        return pool.get(type);
    }
    public int getWebSiteCount() {
        return pool.size();
    }
}

/**
 * 外部状态
 */
@Data
@AllArgsConstructor
class User {
    private String name;
}
