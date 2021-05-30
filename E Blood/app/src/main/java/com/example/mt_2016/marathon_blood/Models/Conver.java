package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by BabarMustafa on 10/31/2016.
 */

public class Conver {
    String conversationId;
    String userid;



    public Conver(String conversationId, String userid) {
        this.conversationId = conversationId;
        this.userid = userid;
    }

    public Conver() {
    }

    public Conver(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
