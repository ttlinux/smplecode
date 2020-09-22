package com.lottery.biying.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/25.
 */
public class NumberPosition {

    public int Position;
    private ArrayList<String> text;

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}
