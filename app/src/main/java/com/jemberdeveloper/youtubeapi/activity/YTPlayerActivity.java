package com.jemberdeveloper.youtubeapi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jemberdeveloper.youtubeapi.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class YTPlayerActivity extends AppCompatActivity {

    private YouTubePlayerView ytPlayer;
    private TextView videoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ytplayer);

        ytPlayer = findViewById(R.id.yt_player);
        videoTitle = findViewById(R.id.video_title);

        Intent data = getIntent();
        final String videoId = data.getStringExtra("video_id");
        String video_title = data.getStringExtra("video_title");

        ytPlayer.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId,0);
            }
        });

        videoTitle.setText(video_title);

    }
}
