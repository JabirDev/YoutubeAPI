package com.jemberdeveloper.youtubeapi.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jemberdeveloper.youtubeapi.R;
import com.jemberdeveloper.youtubeapi.adapter.AdapterHome;
import com.jemberdeveloper.youtubeapi.models.ModelHome;
import com.jemberdeveloper.youtubeapi.models.VideoYT;
import com.jemberdeveloper.youtubeapi.network.YoutubeAPI;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText input_query;
    private Button btn_search;
    private AdapterHome adapter;
    private LinearLayoutManager manager;
    private List<VideoYT> videoList = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Inflate the layout for this fragment
        input_query = view.findViewById(R.id.input_query);
        btn_search = view.findViewById(R.id.btn_search);
        RecyclerView rv = view.findViewById(R.id.recycler_search);

        adapter = new AdapterHome(getContext(), videoList);
        manager = new LinearLayoutManager(getContext());
        rv.setAdapter(adapter);
        rv.setLayoutManager(manager);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input_query.getText().toString())){
                    getJson(input_query.getText().toString());
                } else {
                    Toast.makeText(getContext(), "You need to enter some text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void getJson(String query) {
        String url = YoutubeAPI.BASE_URL + YoutubeAPI.sch + YoutubeAPI.KEY + YoutubeAPI.chid + YoutubeAPI.mx + YoutubeAPI.ord
                + YoutubeAPI.part + YoutubeAPI.query + query + YoutubeAPI.type;
        Call<ModelHome> data = YoutubeAPI.getVideo().getHomeVideo(url);
        data.enqueue(new Callback<ModelHome>() {
            @Override
            public void onResponse(Call<ModelHome> call, Response<ModelHome> response) {
                if (response.errorBody() != null){
                    Log.w(TAG, "onResponse search : "+ response.errorBody().toString() );
                } else {
                    ModelHome mh = response.body();
                    if (mh.getItems().size() != 0){
                        videoList.addAll(mh.getItems());
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No video", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelHome> call, Throwable t) {
                Log.e(TAG, "onFailure search: ", t);
            }
        });
    }

}
