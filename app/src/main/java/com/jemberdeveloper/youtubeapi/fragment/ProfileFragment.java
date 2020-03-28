package com.jemberdeveloper.youtubeapi.fragment;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jemberdeveloper.youtubeapi.R;
import com.jemberdeveloper.youtubeapi.models.ChannelList;
import com.jemberdeveloper.youtubeapi.models.ModelChannel;
import com.jemberdeveloper.youtubeapi.network.YoutubeAPI;
import com.jemberdeveloper.youtubeapi.utils.ChangeTo;
import com.squareup.picasso.Picasso;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ImageView banner,logo;
    private TextView channelName, subcriber, description, videoCount, viewsCount, publistAt;
    private ShimmerFrameLayout loading;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        loading = view.findViewById(R.id.shimmer1);
        banner = view.findViewById(R.id.banner);
        logo = view.findViewById(R.id.logo);
        channelName = view.findViewById(R.id.channel_name);
        subcriber = view.findViewById(R.id.subscriber_count);
        description = view.findViewById(R.id.description);
        videoCount = view.findViewById(R.id.video_count);
        viewsCount = view.findViewById(R.id.views_count);
        publistAt = view.findViewById(R.id.published_at);

        getJsonAPI();

        return view;
    }

    private void getJsonAPI() {
        loading.setVisibility(View.VISIBLE);
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.CH + YoutubeAPI.KEY +
                YoutubeAPI.IDC + YoutubeAPI.CH_PART;
        Call<ModelChannel> yt = YoutubeAPI.getVideo().getChannel(url);
        yt.enqueue(new Callback<ModelChannel>() {
            @Override
            public void onResponse(Call<ModelChannel> call, Response<ModelChannel> response) {
                if (response.errorBody() != null){
                    Log.e(TAG, "onResponse: " );
                    loading.setVisibility(View.GONE);
                } else {
                    if (response.body() != null){
                        ModelChannel mc = response.body();
                        setData(mc.getItems().get(0));
                        loading.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelChannel> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                loading.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData(ChannelList c) {
        channelName.setText(c.getSnippet().getTitle());
        subcriber.setText(ChangeTo.formatedNumber(c.getStatistic().getSubscriberCount())+" subscriber");
        description.setText(c.getSnippet().getDescription());
        viewsCount.setText(ChangeTo.formatedNumber(c.getStatistic().getViewCount())+" views");
        videoCount.setText(ChangeTo.formatedNumber(c.getStatistic().getVideoCount())+" videos");
        publistAt.setText(ChangeTo.dateFormated(c.getSnippet().getPublishedAt()));

        Picasso.get()
                .load(c.getSnippet().getThumbnails().getMedium().getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(logo, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            logo.setClipToOutline(true);
        }

        Picasso.get()
                .load(c.getChannelBranding().getImageBranding().getBannerImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(banner, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "onSuccess: ");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });

    }

}
