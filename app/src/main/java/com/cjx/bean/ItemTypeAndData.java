package com.cjx.bean;

import java.io.Serializable;

/**
 * Created by CJX on 2016-8-1.
 *
 * 区分每一个item的类型及其数据
 */
public class ItemTypeAndData implements Serializable{

    private String content;
    private int type;

    public ItemTypeAndData(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
