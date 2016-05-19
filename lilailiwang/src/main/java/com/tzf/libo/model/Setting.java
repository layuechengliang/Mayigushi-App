package com.tzf.libo.model;

import java.io.Serializable;

/**
 * @author tangzhifei on 15/11/21.
 */
public class Setting implements Serializable {

    private int type;

    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
