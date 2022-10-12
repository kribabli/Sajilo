package com.sample.sajilo.Adapter;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.sajilo.Model.VideoResponse;
import com.sample.sajilo.R;

import java.util.List;

import xyz.hanks.library.bang.SmallBangView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{
    private List<VideoResponse> mVideoItems;

    public VideoAdapter(List<VideoResponse> videoItems) {
        mVideoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(mVideoItems.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.image.isSelected()){
                    holder.image.setSelected(false);
                }
                else {
                    // if not selected only
                    // then show animation.
                    holder.image.setSelected(true);
                    holder.image.likeAnimation();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoItems.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder{
        VideoView videoView;
        TextView header_title,tag_state_description;
        ProgressBar progress_circular;
        SmallBangView image;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            header_title = itemView.findViewById(R.id.header_title);
            tag_state_description = itemView.findViewById(R.id.tag_state_description);
            progress_circular = itemView.findViewById(R.id.progress_circular);
            image = itemView.findViewById(R.id.image);
        }



        void setVideoData(VideoResponse videoItem){
//            txtTitle.setText(videoItem.videoTitle);
//            txtDesc.setText(videoItem.videoDesc);
            videoView.setVideoPath("http://adminapp.tech/Sajilo/"+videoItem.getVedio());
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progress_circular.setVisibility(View.GONE);
                    mp.start();

                    float videoRatio = mp.getVideoWidth() / (float)mp.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float)videoView.getHeight();
                    float scale  = videoRatio / screenRatio;
                    if (scale >= 1f){
                        videoView.setScaleX(scale);
                    }else {
                        videoView.setScaleY(1f / scale);
                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(videoView.isPlaying())
                    {
                        videoView.pause();
                        return false;
                    }
                    else {
                        videoView.start();
                        return false;
                    }
                }
            });
        }
    }
}