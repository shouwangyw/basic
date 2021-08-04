package com.yw.basic.designpattern.dp2.prototype;

import java.io.*;

/**
 * 深浅复制
 */
public class DeepShallowCopy implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private String string;
    private SerializableObject obj;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public SerializableObject getObj() {
        return obj;
    }

    public void setObj(SerializableObject obj) {
        this.obj = obj;
    }

    /**
     * 浅复制
     */
    public Object shallowClone() throws CloneNotSupportedException {
        DeepShallowCopy proto = (DeepShallowCopy) super.clone();
        return proto;
    }

    /**
     * 深复制
     *
     * @throws ClassNotFoundException
     */
    public Object deepClone() throws IOException, ClassNotFoundException {
        // 写入当前对象的二进制流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        // 读出二进制流产生的新对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}

class SerializableObject implements Serializable {
    private static final long serialVersionUID = 1L;
}