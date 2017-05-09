package com.sorcererxw.demo.gridwebview;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/3/5
 */

public class WebData {

    /**
     * code : 200
     * message : Successful operation
     * id : udid12345612121
     * name : 安卓电视9
     * location : B车间
     * screenSize : 24
     * screenNum : 1
     * styleVO : {"code":"200","message":"Successful operation","id":41,"name":"无","speed":"0","mode":"无","description":"无动作"}
     * description : 备注:安卓电视
     * urls : [{"code":"200","message":"Successful operation","id":4,"content":"http://www.baidu.com","description":"百度"},{"code":"200","message":"Successful operation","id":2,"content":"https://www.douban.com","description":"豆瓣"},{"code":"200","message":"Successful operation","id":5,"content":"http://www.zhihu.com","description":"知乎"},{"code":"200","message":"Successful operation","id":3,"content":"http://www.qq.com","description":"qq"},{"code":"200","message":"Successful operation","id":6,"content":"http://news.sohu.com/","description":"搜狐新闻"},{"code":"200","message":"Successful operation","id":9,"content":"https://leetcode.com/","description":"算法"}]
     */

    @SerializedName("code")
    private String mCode;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("screenSize")
    private int mScreenSize;
    @SerializedName("screenNum")
    private int mScreenNum;
    @SerializedName("styleVO")
    private StyleVOBean mStyleVO;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("urls")
    private List<UrlsBean> mUrls;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public int getScreenSize() {
        return mScreenSize;
    }

    public void setScreenSize(int screenSize) {
        mScreenSize = screenSize;
    }

    public int getScreenNum() {
        return mScreenNum;
    }

    public void setScreenNum(int screenNum) {
        mScreenNum = screenNum;
    }

    public StyleVOBean getStyleVO() {
        return mStyleVO;
    }

    public void setStyleVO(StyleVOBean styleVO) {
        mStyleVO = styleVO;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<UrlsBean> getUrls() {
        return mUrls;
    }

    public void setUrls(List<UrlsBean> urls) {
        mUrls = urls;
    }

    public static class StyleVOBean {
        /**
         * code : 200
         * message : Successful operation
         * id : 41
         * name : 无
         * speed : 0
         * mode : 无
         * description : 无动作
         */

        @SerializedName("code")
        private String mCode;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("id")
        private int mId;
        @SerializedName("name")
        private String mName;
        @SerializedName("speed")
        private int mSpeed;
        @SerializedName("mode")
        private String mMode;
        @SerializedName("description")
        private String mDescription;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public int getSpeed() {
            return mSpeed;
        }

        public void setSpeed(int speed) {
            mSpeed = speed;
        }

        public String getMode() {
            return mMode;
        }

        public void setMode(String mode) {
            mMode = mode;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }
    }

    public static class UrlsBean {
        /**
         * code : 200
         * message : Successful operation
         * id : 4
         * content : http://www.baidu.com
         * description : 百度
         */

        @SerializedName("code")
        private String mCode;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("id")
        private int mId;
        @SerializedName("content")
        private String mContent;
        @SerializedName("description")
        private String mDescription;

        public String getCode() {
            return mCode;
        }

        public void setCode(String code) {
            mCode = code;
        }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }
    }

    public static WebData sample() {
        WebData webData = new WebData();
        UrlsBean urlsBean = new UrlsBean();
        urlsBean.mContent = "http://www.baidu.com";
        webData.setUrls(Collections.singletonList(urlsBean));
        webData.mScreenNum = 1;
        return webData;
    }
}
