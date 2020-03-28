package com.jemberdeveloper.youtubeapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jemberdeveloper.youtubeapi.R;
import com.jemberdeveloper.youtubeapi.activity.YTPlayerActivity;
import com.jemberdeveloper.youtubeapi.models.VideoYT;
import com.jemberdeveloper.youtubeapi.utils.ChangeTo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoYT> videoList;

    public AdapterHome(Context context, List<VideoYT> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    class YoutubeHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView judul, tanggal;

        public YoutubeHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.iv_thumbnail);
            judul = itemView.findViewById(R.id.tv_judul);
            tanggal = itemView.findViewById(R.id.tv_tglUpdate);
        }

        public void setData(final VideoYT data) {
            final String getJudul = data.getSnippet().getTitle();
            String getTgl = data.getSnippet().getPublishedAt();
            String getThumb = data.getSnippet().getThumbnails().getMedium().getUrl();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, YTPlayerActivity.class);
                    i.putExtra("video_id", data.getId().getVideoId());
                    i.putExtra("video_title", getJudul);
                    context.startActivity(i);
                }
            });

            judul.setText(getJudul);
            tanggal.setText(ChangeTo.getTimeAgo(getTgl));
            Picasso.get()
                    .load(getThumb)
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(thumbnail, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Thumbnail berhasil ditampilkan");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "Thumbnail error: ", e);
                        }
                    });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item_home, parent, false);
        return new YoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoYT videoYT = videoList.get(position);
        YoutubeHolder yth = (YoutubeHolder) holder;
        yth.setData(videoYT);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
