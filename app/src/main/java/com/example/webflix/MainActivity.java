package com.example.webflix;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    View view;
    Context context;
    ViewFlipper flipper;
    ArrayList<VideoData> videoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer = findViewById(R.id.drawer_layout) ;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle) ;
        toggle.syncState() ;

        NavigationView navigationView = findViewById(R.id. nav_view );
        navigationView.setNavigationItemSelectedListener(this);

        /////////////////////////// content_main code ///////////////////////////////////
        flipper = findViewById(R.id.flipper);

        int slideshowArray[] = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};

        for (int i = 0; i < slideshowArray.length; i++)
            slideShow(slideshowArray[i]);

        VideoData();

        RecyclerView recyclerView = findViewById(R.id.rclrview);
        HomeAdapter homeAdapter = new HomeAdapter(this,videoData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(homeAdapter);

    }

    public void slideShow(int img) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setInAnimation(this, android.R.anim.slide_out_right);


    }

    public void VideoData(){
        videoData = new ArrayList<VideoData>();
        videoData.add(new VideoData("Title 1",R.drawable.minus_one));
        videoData.add(new VideoData("Title 2",R.drawable.adulting));
        videoData.add(new VideoData("Title 3",R.drawable.operation_mbbs));
        videoData.add(new VideoData("Title 4",R.drawable.brochra));
        videoData.add(new VideoData("Title 5",R.drawable.minus_one));
        videoData.add(new VideoData("Title 6",R.drawable.adulting));
        videoData.add(new VideoData("Title 7",R.drawable.operation_mbbs));
        videoData.add(new VideoData("Title 8",R.drawable.brochra));
        videoData.add(new VideoData("Title 9",R.drawable.minus_one));
        videoData.add(new VideoData("Title 10",R.drawable.adulting));

    }



    @Override
    public void onBackPressed () {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}