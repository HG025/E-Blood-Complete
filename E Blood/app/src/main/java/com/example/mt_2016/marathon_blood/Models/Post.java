package com.example.mt_2016.marathon_blood.Models;

/**
 * Created by MT-2016 on 2/26/2017.
 */
public class Post {
    String remainings;
    String post_by_image;
    String push_c;
    String p_naem;
    String p_group;
    String p_units;
    String p_urgency;
    String p_country;
    String p_state;
    String p_city;
    String p_hos;
    String p_rel;
    String cont;
    String add_i;
    String post_by_id;
    long mTimestamp;


    public Post() {
    }

    public Post(String post_by_id,String remainings,String post_by_image, String push_c, String p_naem, String p_group,
                String p_units, String p_urgency, String p_country, String p_state, String p_city, String p_hos, String p_rel,
                String cont, String add_i, long mTimestamp) {
        this.post_by_id =post_by_id;
        this.remainings = remainings;
        this.post_by_image = post_by_image;
        this.push_c = push_c;
        this.p_naem = p_naem;
        this.p_group = p_group;
        this.p_units = p_units;
        this.p_urgency = p_urgency;
        this.p_country = p_country;
        this.p_state = p_state;
        this.p_city = p_city;
        this.p_hos = p_hos;
        this.p_rel = p_rel;
        this.cont = cont;
        this.add_i = add_i;
        this.mTimestamp = mTimestamp;
    }


    /*
    public Post(String push_c, String p_naem, String p_group, String p_units, String p_urgency, String p_country, String p_state, String p_city, String p_hos, String p_rel, String cont, String add_i) {
        this.push_c = push_c;
        this.p_naem = p_naem;
        this.p_group = p_group;
        this.p_units = p_units;
        this.p_urgency = p_urgency;
        this.p_country = p_country;
        this.p_state = p_state;
        this.p_city = p_city;
        this.p_hos = p_hos;
        this.p_rel = p_rel;
        this.cont = cont;
        this.add_i = add_i;
    }

    public Post(String add_i, String p_naem, String p_group, String p_units, String p_urgency, String p_country, String p_state, String p_hos, String p_rel, String cont) {
        this.add_i = add_i;
        this.p_naem = p_naem;
        this.p_group = p_group;
        this.p_units = p_units;
        this.p_urgency = p_urgency;
        this.p_country = p_country;
        this.p_state = p_state;
        this.p_hos = p_hos;
        this.p_rel = p_rel;
        this.cont = cont;
    }

    public Post(String p_naem, String p_group, String p_units, String p_urgency, String p_country, String p_state, String p_city, String p_hos, String p_rel, String cont, String add_i) {
        this.p_naem = p_naem;
        this.p_group = p_group;
        this.p_units = p_units;
        this.p_urgency = p_urgency;
        this.p_country = p_country;
        this.p_state = p_state;
        this.p_city = p_city;
        this.p_hos = p_hos;
        this.p_rel = p_rel;
        this.cont = cont;
        this.add_i = add_i;
    }*/
    public long getTimeInMillis() {
        return mTimestamp;
    }

    public String getPost_by_id() {
        return post_by_id;
    }

    public void setPost_by_id(String post_by_id) {
        this.post_by_id = post_by_id;
    }

    public String getRemainings() {
        return remainings;
    }

    public void setRemainings(String remainings) {
        this.remainings = remainings;
    }

    public String getPost_by_image() {
        return post_by_image;
    }

    public void setPost_by_image(String post_by_image) {
        this.post_by_image = post_by_image;
    }

    public String getP_naem() {
        return p_naem;
    }

    public String getPush_c() {
        return push_c;
    }

    public void setPush_c(String push_c) {
        this.push_c = push_c;
    }

    public void setP_naem(String p_naem) {
        this.p_naem = p_naem;
    }

    public String getP_group() {
        return p_group;
    }

    public void setP_group(String p_group) {
        this.p_group = p_group;
    }

    public String getP_units() {
        return p_units;
    }

    public void setP_units(String p_units) {
        this.p_units = p_units;
    }

    public String getP_urgency() {
        return p_urgency;
    }

    public void setP_urgency(String p_urgency) {
        this.p_urgency = p_urgency;
    }

    public String getP_country() {
        return p_country;
    }

    public void setP_country(String p_country) {
        this.p_country = p_country;
    }

    public String getP_state() {
        return p_state;
    }

    public void setP_state(String p_state) {
        this.p_state = p_state;
    }

    public String getP_city() {
        return p_city;
    }

    public void setP_city(String p_city) {
        this.p_city = p_city;
    }

    public String getP_hos() {
        return p_hos;
    }

    public void setP_hos(String p_hos) {
        this.p_hos = p_hos;
    }

    public String getP_rel() {
        return p_rel;
    }

    public void setP_rel(String p_rel) {
        this.p_rel = p_rel;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getAdd_i() {
        return add_i;
    }

    public void setAdd_i(String add_i) {
        this.add_i = add_i;
    }
}
