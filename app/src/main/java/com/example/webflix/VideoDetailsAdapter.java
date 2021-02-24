package com.example.webflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoDetailsAdapter extends RecyclerView.Adapter<VideoDetailsAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    VideoDetailsModel myepisodes;
    EpisodeLinkSender myepisodeLinkSender;

    public VideoDetailsAdapter(Context context, VideoDetailsModel episodes, EpisodeLinkSender myepisodeLinkSender) {
        this.context = context;
        this.myepisodes = episodes;
        this.myepisodeLinkSender = myepisodeLinkSender;
    }

    @NonNull
    @Override
    public VideoDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_episode,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoDetailsAdapter.ViewHolder holder, int position) {
        String mytitle = myepisodes.getEpisode_title_list().get(position);
        Picasso.get().load(myepisodes.getThumb_nail()).fit().into(holder.episode_thumbnail);
        holder.episode_title.setText(mytitle);





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "position : "+position, Toast.LENGTH_SHORT).show();
                myepisodeLinkSender.onEpisodeClick(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return myepisodes.getEpisode_title_list().size();
    }

    @Override
    public void onClick(View v) {
        //////////////////////////

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView episode_thumbnail;
        TextView episode_title;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            episode_thumbnail = itemView.findViewById(R.id.episode_thumbnail);
            episode_title = itemView.findViewById(R.id.episode_textview);


        }
    }


    public interface EpisodeLinkSender{
        void onEpisodeClick(int position);
    }
}
