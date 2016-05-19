package com.tzf.libo.model;

public class InputItem implements Cloneable {

    private int id;

    private String name;

    @Override
    protected InputItem clone() throws CloneNotSupportedException {
        return (InputItem) super.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}