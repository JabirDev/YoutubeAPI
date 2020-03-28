package com.jemberdeveloper.youtubeapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelBranding {

    @SerializedName("image")
    @Expose
    private ImageBranding imageBranding;

    public ChannelBranding() {
    }

    public ChannelBranding(ImageBranding imageBranding) {
        this.imageBranding = imageBranding;
    }

    public ImageBranding getImageBranding() {
        return imageBranding;
    }

    public void setImageBranding(ImageBranding imageBranding) {
        this.imageBranding = imageBranding;
    }
}
