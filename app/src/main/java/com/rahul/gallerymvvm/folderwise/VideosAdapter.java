package com.rahul.gallerymvvm.folderwise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rahul.gallerymvvm.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    public Context context;
    List<FolderVideo> videoList;

    public VideosAdapter(Context context2, List<FolderVideo> list) {
        this.context = context2;
        this.videoList = list;

    }


    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public void onBindViewHolder(VideoViewHolder videoViewHolder, int i) {
        try {
            FolderVideo video = videoList.get(i);
            File file1 = new File(videoList.get(i).getData());
            videoViewHolder.tvTitle.setText(video.getName());
            videoViewHolder.tvDuration.setText(video.getDuration());
            videoViewHolder.tv_size.setText(getFileSizeMegaBytes(file1));
            Glide.with(this.context).load(video.getData()).placeholder(R.drawable.ic_launcher_background).into(videoViewHolder.ivVideoThumbnail);

        } catch (Exception unused) {

        }
    }

    public String getFileSizeMegaBytes(File file) {
        double length = (double) file.length();
        Double.isNaN(length);
        Double.isNaN(length);
        String format = String.format("%.2f", Double.valueOf(length / 1048576.0d));
        return format + " MB";
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new VideoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false));
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivVideoThumbnail;
        LinearLayout rlytVideoItemLayout;
        TextView tvDuration;
        TextView tvTitle;
        TextView tv_size;

        public VideoViewHolder(View view) {
            super(view);
            this.rlytVideoItemLayout = (LinearLayout) view.findViewById(R.id.video_item);
            this.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.tvDuration = (TextView) view.findViewById(R.id.tv_duration);
            this.ivVideoThumbnail = (ImageView) view.findViewById(R.id.iv_video_thumb);
            this.tv_size = (TextView) view.findViewById(R.id.tv_size);
        }

    }

    public void setFilter(List<FolderVideo> songList) {
        ArrayList arrayList = new ArrayList();
        videoList = arrayList;
        arrayList.addAll(songList);
        notifyDataSetChanged();
    }




}
