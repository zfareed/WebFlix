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

import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;


public class VideoDetails extends AppCompatActivity{

    String OriginalYouLink = "https://www.youtube.com/watch?v=xGSTLc7LVXw";
    String NewYouLink = null;
    PlayerView playerView;
    SimpleExoPlayer player;
    //String testURL =  "https://r1---sn-f5uxxo23-aixe.googlevideo.com/videoplayback?expire=1609846396&ei=HPrzX9ftBpzaxgLpjIzIDg&ip=110.39.124.58&id=o-AK7E8PGQwapQrZXEciFVQ7CozsLRhMxicyFrPSOOyLAY&itag=247&aitags=133%2C134%2C135%2C136%2C160%2C242%2C243%2C244%2C247%2C278&source=youtube&requiressl=yes&mh=Y4&mm=31%2C29&mn=sn-f5uxxo23-aixe%2Csn-2uja-3ipd&ms=au%2Crdu&mv=m&mvi=1&pcm2cms=yes&pl=22&gcr=pk&initcwndbps=160000&vprv=1&mime=video%2Fwebm&ns=9fzKFp_BnoASt5Qzb56iqOAF&gir=yes&clen=15892968&dur=264.440&lmt=1600615632340808&mt=1609824528&fvip=1&keepalive=yes&c=WEB&txp=5316222&n=D8R-lv9KppfK0g&sparams=expire%2Cei%2Cip%2Cid%2Caitags%2Csource%2Crequiressl%2Cgcr%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cdur%2Clmt&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpcm2cms%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhAK1kqiqZZmEq0Ge_ZmEEsaehi2VXA2SljTCitmb73a0DAiABlK_AsdO39Xvj0sWBJs9ihyDod74QgccTQNXCItzHFg%3D%3D&alr=yes&sig=AOq0QJ8wRgIhAMQNtMvSHjAdVjk2S7DdGsU5ht8BRhQ4ecfCnfD_2U8IAiEA7d3qSNm9JBsKlWjvPeVoQ6rsZVnK09ff1DcpctpEWqo%3D&cpn=AzUG0DUHb5uAZ61U&cver=2.20201220.08.00&rn=1";
    private long playBackPosition = 0;
    private int playerWindow = 0;
    ProgressBar loadingBar;
    RecyclerView episodeRecycler;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseRef;
    VideoDetailsModel myepisodes;
    ArrayList<String> videoList;
    ImageView playbutton;
    ImageView favBtn;
    ImageView sharBtn;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = findViewById(R.id.player_view);
        loadingBar = findViewById(R.id.loading_bar);
        episodeRecycler = findViewById(R.id.episode_recyclerview);
        favBtn = findViewById(R.id.favBtn);
        sharBtn = findViewById(R.id.sharBtn);
        playbutton = findViewById(R.id.playbutton);


        Intent intent = getIntent();
        Integer position = intent.getIntExtra("Position", 100);


        getYoutubeLink();
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getYoutubeLink();
                if (NewYouLink != null){
                    onStart();
                }
                else {
                    Toast.makeText(VideoDetails.this, "Response Error", Toast.LENGTH_SHORT).show();
                }
            }
        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(linearLayoutManager);

        //Decoration - Divider between items
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        episodeRecycler.addItemDecoration(itemDecoration);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference("Data");


        myepisodes = new VideoDetailsModel(null, null);
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (count == position) {
                        myepisodes.setThumb_nail(ds.child("Details").child("thumb_nail").getValue().toString());
                        videoList = new ArrayList<>();
                        for (DataSnapshot dss : ds.child("Details").child("video_links").getChildren()) {
                            videoList.add(dss.getKey());
                        }
                        myepisodes.setVideo_links(videoList);

                        break;
                    } else {
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

    //////////////////////////////////////////////////////////////////////

    public  void getYoutubeLink(){
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles != null) {
                    int itag = 22;
                    NewYouLink = ytFiles.get(itag).getUrl();
                }
            }
        }.extract(OriginalYouLink,false,false);

        Log.i("NewLink","link : "+NewYouLink);

        return;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (NewYouLink != null) {
            playbutton.setVisibility(View.GONE);

            player = ExoPlayerFactory.newSimpleInstance(this);
            playerView.setPlayer(player);

// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Webflix"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(NewYouLink));


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
            Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show();
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




}

