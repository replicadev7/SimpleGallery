package com.rahul.gallerymvvm.folderwise;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahul.gallerymvvm.databinding.ItemListBinding;
import com.rahul.gallerymvvm.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter  extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private Context context;
    List<Folder> folderArrayList;


    public FolderAdapter(Context context, List<Folder> folderArrayList) {
        this.context = context;
        this.folderArrayList = folderArrayList;
    }

    @NonNull
    @Override
    public FolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemListBinding itemBinding = ItemListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FolderAdapter.ViewHolder viewHolder, int i) {
        Folder folder = folderArrayList.get(i);
        viewHolder.binding.tvTitle.setText(folder.getName());
        viewHolder.binding.tvVideoSize.setText(folder.getTotalVideos() + " | " + "Videos");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoListActivity.class);
                intent.putExtra(Constants.FOLDER_PATH, folderArrayList.get(i).getPath());
                intent.putExtra(Constants.FOLDER_NAME, folderArrayList.get(i).getName());
                intent.putExtra(Constants.FOLDER_POSITION, i);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return folderArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemListBinding binding;
        public ViewHolder(ItemListBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;

        }
    }
}
