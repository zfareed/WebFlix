package com.example.webflix;

import java.util.ArrayList;

public class VideoDetailsModel {
    String thumb_nail;
    ArrayList<String> video_links;

    public VideoDetailsModel(String thumb_nail, ArrayList<String> video_links) {
        this.thumb_nail = thumb_nail;
        this.video_links = video_links;
    }



    public String getThumb_nail() {
        return thumb_nail;
    }

    public void setThumb_nail(String thumb_nail) {
        this.thumb_nail = thumb_nail;
    }

    public ArrayList<String> getVideo_links() {
        return video_links;
    }

    public void setVideo_links(ArrayList<String> video_links) {
        this.video_links = video_links;
    }
}
