package com.jemberdeveloper.youtubeapi.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jemberdeveloper.youtubeapi.R;
import com.jemberdeveloper.youtubeapi.fragment.HomeFragment;
import com.jemberdeveloper.youtubeapi.fragment.PlaylistFragment;
import com.jemberdeveloper.youtubeapi.fragment.ProfileFragment;
import com.jemberdeveloper.youtubeapi.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment = new HomeFragment();
    private PlaylistFragment playlistFragment = new PlaylistFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private ProfileFragment profileFragment = new ProfileFragment();

    private BottomNavigationView menuBawah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBawah = findViewById(R.id.menu_bawah);

        setFragment(homeFragment);
        menuBawah.setSelectedItemId(R.id.menu_home);
        menuBawah.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.isChecked()){
                    return true;
                } else {
                    switch (menuItem.getItemId()){
                        case R.id.menu_home:
                            setFragment(homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                        case R.id.menu_playlist:
                            setFragment(playlistFragment);
                            getSupportActionBar().setTitle("Playlist");
                            return true;
                        case R.id.menu_search:
                            setFragment(searchFragment);
                            getSupportActionBar().setTitle("Search");
                            return true;
                        case R.id.menu_profile:
                            setFragment(profileFragment);
                            getSupportActionBar().setTitle("Profile");
                            return true;
                        default:
                            setFragment(homeFragment);
                            getSupportActionBar().setTitle("Home");
                            return true;
                    }
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.commit();
    }
}
