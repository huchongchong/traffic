package com.aiseminar.platerecognizer.model;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class Task {
    public int type;
    public String content;
    public List<String> notice;
    public String title;
    public String locationDes;
    public String time;
    public String num;
    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public List<String> getNotice() {
        return notice;
    }

    public String getTitle() {
        return title;
    }
}
