package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by BabarMustafa on 1/31/2017.
 */

public class groups_create_info {
    String admin_uuid;
    String group_name;
    String g_i_url;
    long mTimestamp;


    public groups_create_info() {
    }

    public groups_create_info(String admin_uuid, String group_name, String g_i_url,long mTimestamp) {
        this.admin_uuid = admin_uuid;
        this.group_name = group_name;
        this.g_i_url = g_i_url;
        this.mTimestamp=mTimestamp;
    }
    public long getTimeInMillis() {
        return mTimestamp;
    }


    public String getAdmin_uuid() {
        return admin_uuid;
    }

    public void setAdmin_uuid(String admin_uuid) {
        this.admin_uuid = admin_uuid;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getG_i_url() {
        return g_i_url;
    }

    public void setG_i_url(String g_i_url) {
        this.g_i_url = g_i_url;
    }
}
