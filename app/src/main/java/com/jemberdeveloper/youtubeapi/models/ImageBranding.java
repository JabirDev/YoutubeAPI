package com.jemberdeveloper.youtubeapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageBranding {

    @SerializedName("bannerImageUrl")
    @Expose
    private String bannerImageUrl;

    public ImageBranding() {
    }

    public ImageBranding(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }
}
