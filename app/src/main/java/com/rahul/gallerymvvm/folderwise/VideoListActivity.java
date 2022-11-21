package com.rahul.gallerymvvm.folderwise;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rahul.gallerymvvm.R;
import com.rahul.gallerymvvm.util.Constants;
import com.rahul.gallerymvvm.util.VideosAndFoldersLoader;

import java.util.ArrayList;
import java.util.List;


public class VideoListActivity extends AppCompatActivity {
    private String folderPath = "";
    private String foldername = "";
    private int folderposition = 0;
    public RecyclerView rvVideos;
    public static List<FolderVideo> videos = new ArrayList();
    public VideosAdapter videosAdapter;



    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_list);


        videoData();

        toolbar_Data();

        setUpRecyclerview();

    }

    private void videoData() {
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.FOLDER_PATH)) {
            this.folderPath = getIntent().getExtras().getString(Constants.FOLDER_PATH);
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.FOLDER_NAME)) {
            this.foldername = getIntent().getExtras().getString(Constants.FOLDER_NAME);
        }
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.FOLDER_POSITION)) {
            this.folderposition = getIntent().getExtras().getInt(Constants.FOLDER_POSITION);
        }
    }

    private void toolbar_Data() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (foldername.matches("0")) {
            supportActionBar.setTitle("Internal Storage");
        } else {
            supportActionBar.setTitle(this.foldername);
        }

        supportActionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                VideoListActivity.this.finish();
            }
        });
    }

    private void setUpRecyclerview() {
        Log.e("fvf", "setUpRecyclerview------: " + folderPath);
        videos = new VideosAndFoldersLoader(this).fetchVideosByFolder(this.folderPath);
        rvVideos = (RecyclerView) findViewById(R.id.rv_videos);
        videosAdapter = new VideosAdapter( this, this.videos);
        rvVideos.setAdapter(this.videosAdapter);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));

    }



    SearchView searchView;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        SearchView searchView2 = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.video_search));
        searchView2.setBackground(getResources().getDrawable(R.drawable.search_round_bg));
        this.searchView = searchView2;
        this.searchView.setQueryHint("Search Video");
//        ImageView closeButtonImage = searchView.findViewById(R.id.search_close_btn);
//        closeButtonImage.setImageResource(R.drawable.ic_baseline_close_24);
//        EditText searchEditText = (EditText) this.searchView.findViewById(R.id.search_src_text);
//        searchEditText.setTextColor(getResources().getColor(R.color.black));
//        searchEditText.setHintTextColor(getResources().getColor(R.color.gray));
//        closeButtonImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchView.clearFocus();
//                searchView.setQuery("", false);
//                hideKeyboard();
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<FolderVideo> filterlist = filter(videos, newText);
                if (filterlist.size() > 0) {
                    videosAdapter.setFilter(filterlist);
                    return true;
                }
                videosAdapter.setFilter(filterlist);
                return false;
            }
        });

        return true;
    }

    public List<FolderVideo> filter(List<FolderVideo> songList, String query) {
        String query2 = query.toLowerCase().trim();
        List<FolderVideo> filtersonglist = new ArrayList<>();
        for (FolderVideo video : songList) {
            if (video.getName().toLowerCase().trim().contains(query2)) {
                filtersonglist.add(video);
            }
        }
        return filtersonglist;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}
