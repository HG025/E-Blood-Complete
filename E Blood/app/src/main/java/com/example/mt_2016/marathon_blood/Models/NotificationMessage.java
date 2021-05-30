package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by BabarMustafa on 10/24/2016.
 */

public class NotificationMessage {
    private String message;
    private String pushId;
    private String UUID;
    long mTimestamp;
    String notfpush;
    String whoMessage;




    public NotificationMessage() {
    }

    public NotificationMessage(String message, String notfpush, String UUID, long mTimestamp) {
        this.message = message;
        this.notfpush = notfpush;
        this.UUID = UUID;
       this.mTimestamp = mTimestamp;
    }

    public String getWhoMessage() {
        return whoMessage;
    }

    public void setWhoMessage(String whoMessage) {
        this.whoMessage = whoMessage;
    }

    public String getNotfpush() {
        return notfpush;
    }

    public void setNotfpush(String notfpush) {
        this.notfpush = notfpush;
    }

    public long getmTimestamp() {
        return mTimestamp;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}
