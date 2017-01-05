package com.jzfq.fms.common.enums;

/**
 * Created by huyinglin on 2016/11/22.
 */
public enum PresentOrderSource {

    PC("pc"),

    WECHAT("微信"),

    APPLE_APP("桔子苹果APP"),

    ANDROID_APP("桔子安卓APP");

    String presentOrderSourceName;

    PresentOrderSource(String presentOrderSourceName){
        this.presentOrderSourceName=presentOrderSourceName;
    }

    public String getPresentOrderSourceName() {
        return presentOrderSourceName;
    }

    public void setPresentOrderSourceName(String presentOrderSourceName) {
        this.presentOrderSourceName = presentOrderSourceName;
    }
}
