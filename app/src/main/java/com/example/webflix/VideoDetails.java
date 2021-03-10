package com.example.webflix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
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
import com.google.android.exoplayer2.ui.StyledPlayerView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class VideoDetails extends AppCompatActivity implements VideoDetailsAdapter.EpisodeLinkSender {

    String videoURL = null;
    PlayerView playerView;
    SimpleExoPlayer player;
    private long playBackPosition = 10;
    private int playerWindow = 0;
    ProgressBar loadingBar;
    RecyclerView episodeRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseRef;
    VideoDetailsModel myepisodes;
    ArrayList<String> myVideoList;
    ArrayList<String> myTitleList;
    ImageView playbutton;
    ImageView favBtn;
    ImageView sharBtn;
    ImageView playerThumbnail;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = findViewById(R.id.player_view);
        loadingBar = findViewById(R.id.loading_bar);
        episodeRecycler = findViewById(R.id.episode_recyclerview);
        playbutton = findViewById(R.id.playbutton);
        playerThumbnail = findViewById(R.id.player_thumbnail);








        Intent intent = getIntent();
        Integer position = intent.getIntExtra("Position", 100);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(linearLayoutManager);

        //Decoration - Divider between items
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        episodeRecycler.addItemDecoration(itemDecoration);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("Data");


        myepisodes = new VideoDetailsModel(null, null, null);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (count == position) {
                        myepisodes.setThumb_nail(ds.child("Details").child("thumb_nail").getValue().toString());
                        myVideoList = new ArrayList<>();
                        myTitleList = new ArrayList<>();
                        for (DataSnapshot dss : ds.child("Details").child("video_links").getChildren()) {
                            myTitleList.add(dss.getKey());
                            myVideoList.add(dss.getValue().toString());
                        }
                        myepisodes.setEpisode_video_list(myVideoList);
                        myepisodes.setEpisode_title_list(myTitleList);

                        break;
                    } else {
                        count++;
                    }

                }


                VideoDetailsAdapter adapter = new VideoDetailsAdapter(getApplicationContext(), myepisodes, VideoDetails.this);
                episodeRecycler.setAdapter(adapter);

                videoURL = myepisodes.getEpisode_video_list().get(0);
                Picasso.get().load(myepisodes.getThumb_nail()).fit().into(playerThumbnail);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VideoDetails.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });




        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playbutton.setVisibility(View.GONE);
                onStart();
            }
        });

    }





    @Override
    protected void onStart() {
        super.onStart();
        if (videoURL != null) {
            playerThumbnail.setVisibility(View.GONE);

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
                    switch (playbackState) {
                        case ExoPlayer.STATE_READY:
                            loadingBar.setVisibility(View.GONE);
                            break;
                        case ExoPlayer.STATE_BUFFERING:
                            loadingBar.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            });

        }else
        {
            player = ExoPlayerFactory.newSimpleInstance(this);
            playerView.setPlayer(player);

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
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
        player.setPlayWhenReady(false);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player = null;
    }

    public void releasePlayer(){
        player.setPlayWhenReady(false);
        player.release();
    }


    @Override
    public void onEpisodeClick(int position) {
        releasePlayer();
        videoURL = myepisodes.getEpisode_video_list().get(position);
        playbutton.setVisibility(View.GONE);
        onStart();

    }
}

