package com.jemberdeveloper.youtubeapi.network;

import com.jemberdeveloper.youtubeapi.models.ModelChannel;
import com.jemberdeveloper.youtubeapi.models.ModelHome;
import com.jemberdeveloper.youtubeapi.models.ModelPlaylist;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class YoutubeAPI {

    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public static final String CHANNEL_ID = "UCkXmLjEr95LVtGuIm3l2dPg";
    public static final String KEY = "key=AIzaSyAruGZgRlFGDFMwSLIQajRARk0wRYjYeOc";
    public static final String sch = "search?";
    public static final String chid = "&channelId=" + CHANNEL_ID;
    public static final String mx = "&maxResults=10";
    public static final String ord = "&order=date";
    public static final String part = "&part=snippet";
    public static final String NPT = "&pageToken=";

    public static final String ply = "playlists?";
    public static final String part_ply = "&part=snippet,contentDetails";

    public static final String query = "&q=";
    public static final String type = "&type=video";

    public static final String CH = "channels?";
    public static final String IDC = "&id=" + CHANNEL_ID;
    public static final String CH_PART = "&part=snippet,statistics,brandingSettings";


    public interface Video {
        @GET
        Call<ModelHome> getHomeVideo(@Url String url);

        @GET
        Call<ModelPlaylist> getPlaylist(@Url String url);

        @GET
        Call<ModelChannel> getChannel(@Url String url);
    }

    private static Video video = null;

    public static Video getVideo(){
        if (video == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            video = retrofit.create(Video.class);
        }
        return video;
    }

}
