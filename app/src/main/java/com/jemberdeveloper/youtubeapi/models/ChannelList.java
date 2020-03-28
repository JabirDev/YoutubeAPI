package com.jemberdeveloper.youtubeapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChannelList {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("snippet")
    @Expose
    private SnippetYT snippet;

    @SerializedName("statistics")
    @Expose
    private ChannelStatistic statistic;

    @SerializedName("brandingSettings")
    @Expose
    private ChannelBranding channelBranding;

    public ChannelList() {
    }

    public ChannelList(String id, SnippetYT snippet, ChannelStatistic statistic, ChannelBranding channelBranding) {
        this.id = id;
        this.snippet = snippet;
        this.statistic = statistic;
        this.channelBranding = channelBranding;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SnippetYT getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetYT snippet) {
        this.snippet = snippet;
    }

    public ChannelStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(ChannelStatistic statistic) {
        this.statistic = statistic;
    }

    public ChannelBranding getChannelBranding() {
        return channelBranding;
    }

    public void setChannelBranding(ChannelBranding channelBranding) {
        this.channelBranding = channelBranding;
    }
}
