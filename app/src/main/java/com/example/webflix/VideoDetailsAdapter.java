package com.example.webflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoDetailsAdapter extends RecyclerView.Adapter<VideoDetailsAdapter.ViewHolder>{
    Context context;
    VideoDetailsModel myepisodes;

    public VideoDetailsAdapter(Context context, VideoDetailsModel episodes) {
        this.context = context;
        this.myepisodes = episodes;
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
        String mylink = myepisodes.getVideo_links().get(position);

       // for (int i =0 ;i<myepisodes.getVideo_links().size();i++){
            Picasso.get().load(myepisodes.getThumb_nail()).fit().into(holder.episode_thumbnail);
           holder.episode_title.setText(mylink);
      //  }

        //Picasso.get().load(episodes.getThumb_nail()).fit().into(holder.episode_thumbnail);
       //holder.episode_title.setText(episodes.getVideo_links().get(0));


    }

    @Override
    public int getItemCount() {
        return myepisodes.getVideo_links().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView episode_thumbnail;
        TextView episode_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            episode_thumbnail = itemView.findViewById(R.id.episode_thumbnail);
            episode_title = itemView.findViewById(R.id.episode_textview);
        }
    }
}
