package com.jsonparse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duke on 16-8-20.
 */
public class TabData {
    private String mCHTitle;
    private String mEngTitle;

    // json 中字段是"_id"，与类中字段命名不一样，可以通过注解来解决
    // @SerializedName(value = "mId", alternate = {"_id", "id"})， 如果有多个名字，可以这样解决。多种字段名同时出现是，以最后一个为准
    @SerializedName("_id")
    private String mId;

    public TabData() {
        mCHTitle = "default_ch_title";
        mEngTitle = "default_eng_title";
        mId = "default_id";
    }

    @Override
    public String toString() {
        return "TabData{" +
                "mCHTitle='" + mCHTitle + '\'' +
                ", mEngTitle='" + mEngTitle + '\'' +
                ", mId='" + mId + '\'' +
                '}';
    }
}