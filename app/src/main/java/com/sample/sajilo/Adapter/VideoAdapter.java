package com.sample.sajilo.Adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
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
    public Context context;

    public VideoAdapter(List<VideoResponse> videoItems) {
        mVideoItems = videoItems;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout,parent,false);
        context= parent.getContext();
        return new VideoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setVideoData(mVideoItems.get(position));
        holder.header_title.setText(""+mVideoItems.get(position).getTitle());
        holder.tag_state_description.setText(""+mVideoItems.get(position).getDescription());

        holder.image.setOnClickListener(v -> {
            if(holder.image.isSelected()){
                holder.image.setSelected(false);
            }
            else {
                // if not selected only
                // then show animation.
                holder.image.setSelected(true);
                holder.image.likeAnimation();
            }

        });

        holder.shareImage.setOnClickListener(v -> {
            if(mVideoItems.size()>0){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sajilo App");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "\n1.Vedio url " + "http://adminapp.tech/Sajilo/"+mVideoItems.get(position).getVedio() + "\n";
                shareMessage = shareMessage + "\nLet's play!";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                context.startActivity(Intent.createChooser(shareIntent,"choose one"));
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
        ImageView shareImage;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            header_title = itemView.findViewById(R.id.header_title);
            tag_state_description = itemView.findViewById(R.id.tag_state_description);
            progress_circular = itemView.findViewById(R.id.progress_circular);
            image = itemView.findViewById(R.id.image);
            shareImage = itemView.findViewById(R.id.shareImage);
        }

        void setVideoData(VideoResponse videoItem){
//            txtTitle.setText(videoItem.videoTitle);
//            txtDesc.setText(videoItem.videoDesc);
            videoView.setVideoPath("http://adminapp.tech/Sajilo/"+videoItem.getVedio());
            videoView.setOnPreparedListener(mp -> {
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
            });
            videoView.setOnCompletionListener(mp -> mp.start());

            videoView.setOnTouchListener((v, event) -> {
                if(videoView.isPlaying())
                {
                    videoView.pause();
                    return false;
                }
                else {
                    videoView.start();
                    return false;
                }
            });
        }
    }

}