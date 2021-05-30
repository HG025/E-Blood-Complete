package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by BabarMustafa on 2/8/2017.
 */

public class grouop_convo {
    String msg ;
    long mTimestamp;
    String sendr_id;
    String sendername;
    String groupName;
    String notifPush;

    public grouop_convo() {
    }

    public grouop_convo(String msg, long mTimestamp, String sendr_id,String sendername) {
        this.msg = msg;
        this.mTimestamp = mTimestamp;
        this.sendr_id = sendr_id;
        this.sendername =sendername;
    }

    public String getNotifPush() {
        return notifPush;
    }

    public void setNotifPush(String notifPush) {
        this.notifPush = notifPush;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimeInMillis() {
        return mTimestamp;
    }

    public String getSendr_id() {
        return sendr_id;
    }

    public void setSendr_id(String sendr_id) {
        this.sendr_id = sendr_id;
    }
}
