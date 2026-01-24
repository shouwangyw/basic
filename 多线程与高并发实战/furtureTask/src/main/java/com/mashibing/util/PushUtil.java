package com.mashibing.util;

import com.mashibing.entity.Air;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zjw
 * @description
 */
@Component
public class PushUtil {

    CopyOnWriteArrayList<Air> list = new CopyOnWriteArrayList();

    public boolean push(Air air){
        list.add(air);
        return true;
    }

    public CopyOnWriteArrayList<Air> get(){
        return list;
    }

}
