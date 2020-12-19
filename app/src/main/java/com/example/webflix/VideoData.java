package com.example.webflix;

public class VideoData {
    String video_title;
    String video_image;



    public VideoData(String video_title, String video_image) {
        video_title = video_title;
        video_image = video_image;
    }

    public VideoData(){

    }

    public String getVideo_title() {
        return video_title;
    }

    public String getVideo_image() {
        return video_image;
    }


}
