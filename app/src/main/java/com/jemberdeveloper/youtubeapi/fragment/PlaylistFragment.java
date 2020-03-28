package com.jemberdeveloper.youtubeapi.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jemberdeveloper.youtubeapi.R;
import com.jemberdeveloper.youtubeapi.adapter.AdapterPlaylist;
import com.jemberdeveloper.youtubeapi.models.ModelPlaylist;
import com.jemberdeveloper.youtubeapi.models.PlaylistItems;
import com.jemberdeveloper.youtubeapi.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistFragment extends Fragment {

    private AdapterPlaylist adapter;
    private LinearLayoutManager manager;
    private List<PlaylistItems> videoList = new ArrayList<>();
    private ShimmerFrameLayout loading1,loading2;
    private boolean isScroll = false;
    private int currentItem, totalItem, scrollOutItem;
    private String nextPageToken = "";

    public PlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        // Inflate the layout for this fragment
        loading1 = view.findViewById(R.id.shimmer1);
        loading2 = view.findViewById(R.id.shimmer2);
        RecyclerView rv = view.findViewById(R.id.recycler_playlist);
        adapter = new AdapterPlaylist(getContext(), videoList);
        manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScroll = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = manager.getChildCount();
                totalItem = manager.getItemCount();
                scrollOutItem = manager.findFirstVisibleItemPosition();
                if (isScroll && (currentItem + scrollOutItem == totalItem)){
                    isScroll = false;
                    getJson();
                }
            }
        });

        if (videoList.size() == 0){
            getJson();
        }
        return view;
    }

    private void getJson() {
        loading1.setVisibility(View.VISIBLE);
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.ply + YoutubeAPI.KEY + YoutubeAPI.part_ply + YoutubeAPI.chid;

        if (nextPageToken != ""){
            url = url + YoutubeAPI.NPT + nextPageToken;
            loading1.setVisibility(View.GONE);
            loading2.setVisibility(View.VISIBLE);
        }
        if (nextPageToken == null){
            return;
        }

        Call<ModelPlaylist> data = YoutubeAPI.getVideo().getPlaylist(url);
        data.enqueue(new Callback<ModelPlaylist>() {
            @Override
            public void onResponse(Call<ModelPlaylist> call, Response<ModelPlaylist> response) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse: "+ response.errorBody() );
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                } else {
                    ModelPlaylist mp = response.body();
                    videoList.addAll(mp.getItems());
                    adapter.notifyDataSetChanged();
                    loading1.setVisibility(View.GONE);
                    loading2.setVisibility(View.GONE);
                    if (mp.getNextPageToken() != null){
                        nextPageToken = mp.getNextPageToken();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelPlaylist> call, Throwable t) {
                Log.e(TAG, "onFailure playlist: ", t);
                loading1.setVisibility(View.GONE);
                loading2.setVisibility(View.GONE);
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
