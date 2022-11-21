package com.rahul.gallerymvvm.allvideos;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rahul.gallerymvvm.R;
import com.rahul.gallerymvvm.folderwise.FolderVideo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AllVideoAdapter extends RecyclerView.Adapter<AllVideoAdapter.MyViewHolder>{
    public  ArrayList<FolderVideo> videoArrayList;
    private Context mContext;
    AllVideosActivity allVideosActivity;

    public AllVideoAdapter(AllVideosActivity activity, Context context, ArrayList<FolderVideo> arrayList) {
        this.mContext = context;
        videoArrayList = arrayList;
        allVideosActivity = activity;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            FolderVideo video = videoArrayList.get(position);
            File file1 = new File(videoArrayList.get(position).getData());
            holder.tvTitle.setText(video.getName());
            holder.tvDuration.setText(video.getDuration());
            holder.tv_size.setText(getFileSizeMegaBytes(file1));
            Glide.with(mContext).load(video.getData()).placeholder(R.drawable.ic_launcher_background).into(holder.ivVideoThumbnail);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    allVideosActivity.showMenuItemDetails(view,position,video);
                }
            });

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
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivVideoThumbnail;
        LinearLayout rlytVideoItemLayout;
        TextView tvDuration;
        TextView tvTitle;
        TextView tv_size;

        public MyViewHolder(@NonNull View view) {
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
        videoArrayList = arrayList;
        arrayList.addAll(songList);
        notifyDataSetChanged();
    }




}
