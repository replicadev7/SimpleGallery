package com.rahul.gallerymvvm.folderwise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.rahul.gallerymvvm.databinding.ActivityFolderBinding;
import com.rahul.gallerymvvm.util.VideosAndFoldersLoader;

import java.util.ArrayList;
import java.util.List;

public class FolderAct extends AppCompatActivity {

    private ActivityFolderBinding binding;
    private LinearLayoutManager layoutManager;
    private List<Folder> folderArrayList;
    private List<FolderVideo> folderVideos;
    private FolderAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFolderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        folderArrayList = new ArrayList<>();
        new AsyncLoadVideosVideosAndFolder().execute();
    }



    private class AsyncLoadVideosVideosAndFolder extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.prgsLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.e("dse", "loadVideosImagesAndFoldersList---doInBackground0000--: ");
                VideosAndFoldersLoader videosAndFoldersLoader = new VideosAndFoldersLoader(FolderAct.this);

                folderVideos = videosAndFoldersLoader.fetchAllVideos();
                folderArrayList = videosAndFoldersLoader.fetchAllVideosFolders();

                return null;

            } catch (Exception unused) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            binding.prgsLoading.setVisibility(View.GONE);
            initialization();
        }
    }


    private void initialization() {
        layoutManager = new LinearLayoutManager(FolderAct.this);
        binding.rvFolders.setLayoutManager(layoutManager);
        binding.rvFolders.setHasFixedSize(true);
        adapter = new FolderAdapter(FolderAct.this, folderArrayList);
        binding.rvFolders.setAdapter(adapter);
    }

}