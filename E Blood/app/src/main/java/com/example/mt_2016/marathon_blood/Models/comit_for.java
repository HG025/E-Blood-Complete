package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by MT-2016 on 2/26/2017.
 */
public class comit_for {
    String comit_nmae;
    String commit_text;
    String commit_uuid;
    String confirm_donation;
    String post_push;
    String push_of;
    long mTimestamp;


    public comit_for() {
    }

    public comit_for(String comit_nmae, String commit_text,long mTimestamp) {
        this.comit_nmae = comit_nmae;
        this.commit_text = commit_text;
        this.mTimestamp = mTimestamp;
    }

    public comit_for(String comit_nmae, String commit_text, String commit_uuid, String confirm_donation,String post_push,String push_of,long mTimestamp) {
        this.comit_nmae = comit_nmae;
        this.commit_text = commit_text;
        this.commit_uuid = commit_uuid;
        this.confirm_donation = confirm_donation;
        this.post_push =post_push;
        this.push_of = push_of;
        this.mTimestamp = mTimestamp;

    }
    public comit_for(String comit_nmae, String commit_text,String confirm_donation,String push_of,long mTimestamp) {
        this.comit_nmae = comit_nmae;
        this.commit_text = commit_text;
        this.confirm_donation = confirm_donation;
        this.push_of = push_of;
        this.mTimestamp = mTimestamp;

    }
    public long getTimeInMillis() {
        return mTimestamp;
    }
    public String getPush_of() {
        return push_of;
    }

    public void setPush_of(String push_of) {
        this.push_of = push_of;
    }

    public String getConfirm_donation() {
        return confirm_donation;
    }

    public String getPost_push() {
        return post_push;
    }

    public void setPost_push(String post_push) {
        this.post_push = post_push;
    }

    public void setConfirm_donation(String confirm_donation) {
        this.confirm_donation = confirm_donation;
    }

    public String getCommit_uuid() {
        return commit_uuid;
    }

    public void setCommit_uuid(String commit_uuid) {
        this.commit_uuid = commit_uuid;
    }

    public String getComit_nmae() {
        return comit_nmae;
    }

    public void setComit_nmae(String comit_nmae) {
        this.comit_nmae = comit_nmae;
    }

    public String getCommit_text() {
        return commit_text;
    }

    public void setCommit_text(String commit_text) {
        this.commit_text = commit_text;
    }
}
