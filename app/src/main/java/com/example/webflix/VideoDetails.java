package com.example.webflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class VideoDetails extends AppCompatActivity{

    PlayerView playerView;
    SimpleExoPlayer player;
    String videoURL = ""; //"https://firebasestorage.googleapis.com/v0/b/webflix-298110.appspot.com/o/sample.mkv?alt=media&token=812bd9a8-919a-4382-ab86-39017b0c48d8";
    private long playBackPosition = 10000;
    private int playerWindow = 0;
    ProgressBar loadingBar;
    RecyclerView episodeRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseRef;
    VideoDetailsModel myepisodes;
    Context context;
    ArrayList<String> videoList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);

        playerView = findViewById(R.id.player_view);
        loadingBar = findViewById(R.id.loading_bar);
        episodeRecycler = findViewById(R.id.episode_recyclerview);











        Intent intent = getIntent();
        Integer position = intent.getIntExtra("Position",100);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(linearLayoutManager);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("Data");


        myepisodes = new VideoDetailsModel(null,null);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //myepisodes = snapshot.child("Details").getValue(VideoDetailsModel.class);
                 int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (count == position){
                        myepisodes.setThumb_nail(ds.child("Details").child("thumb_nail").getValue().toString());
                        videoList = new ArrayList<>();
                        for (DataSnapshot dss : ds.child("Details").child("video_links").getChildren()){
                            videoList.add(dss.getKey());
                        }
                        myepisodes.setVideo_links(videoList);

                       // Log.i("Thumbnail : ",myepisodes.getThumb_nail());
                        //Log.i("VideoList : ",myepisodes.getVideo_links().get(0));
                        break;
                    }
                    else {
                        count++;
                    }

                }



                VideoDetailsAdapter adapter = new VideoDetailsAdapter(getApplicationContext(), myepisodes);
                episodeRecycler.setAdapter(adapter);
            }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VideoDetails.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });



    }



    @Override
    protected void onStart() {
        super.onStart();


        player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);

// Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Webflix"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoURL));



        player.prepare(videoSource);
        player.seekTo(playBackPosition);
        player.setPlayWhenReady(true);
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState){
                    case ExoPlayer.STATE_READY:
                        loadingBar.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        loadingBar.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        playBackPosition = player.getCurrentPosition();
        playerView.setPlayer(null);
        player.release();




    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.seekTo(playBackPosition);
        player.setPlayWhenReady(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player = null;
    }




}

