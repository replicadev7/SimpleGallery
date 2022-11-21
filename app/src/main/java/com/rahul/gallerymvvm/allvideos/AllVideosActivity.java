package com.rahul.gallerymvvm.allvideos;

import static android.os.Build.VERSION.SDK_INT;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rahul.gallerymvvm.R;
import com.rahul.gallerymvvm.folderwise.FolderVideo;
import com.rahul.gallerymvvm.util.TheUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AllVideosActivity extends AppCompatActivity {

    Toolbar toolbar2;
    RecyclerView recyclerView;
    public ArrayList<FolderVideo> allVideoList;
    AllVideoAdapter videoAdapter;

    private String[] VIDEO_COLUMNS = {"_id", "_display_name", "title", "date_added", "duration", "resolution", "_size", "_data", "mime_type"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);

        toobar_data();


        new AsyncLoadVideos().execute("date_added DESC");
    }


    private void setAdap() {
        videoAdapter = new AllVideoAdapter(AllVideosActivity.this, this, allVideoList);
        this.recyclerView.setAdapter(videoAdapter);
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(AllVideosActivity.this, 1, false));
    }


    private void toobar_data() {
        recyclerView = findViewById(R.id.all_recyclerView);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
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
//
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
                List<FolderVideo> filterlist = filter(allVideoList, newText);
                if (filterlist.size() > 0) {
                    videoAdapter.setFilter(filterlist);
                    return true;
                }
                videoAdapter.setFilter(filterlist);
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

    public void showMenuItemDetails(View view, int position, FolderVideo video) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            ArrayList<Uri> list = new ArrayList<>();
            Log.e("wse", "onClick-----: " + video.getData());
            list.add(getUriFromPath(video.getData(), AllVideosActivity.this));
            try {
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    startIntentSenderForResult(MediaStore.createDeleteRequest(getContentResolver(), list).getIntentSender(), 10000, null, 0, 0, 0, null);
                }
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }

        } else {
            new AlertDialog.Builder(AllVideosActivity.this)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this Video?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            File file = new File(video.getData());
                            Log.i("dcx", "onMenuItemClick-----: " + file);
                            scanDeletedMedia(AllVideosActivity.this, file);
                            allVideoList.clear();
                            new AsyncLoadVideos().execute("date_added DESC");
                            Toast.makeText(AllVideosActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        }

    }


    private class AsyncLoadVideos extends AsyncTask<String, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(AllVideosActivity.this);
            pd.setMessage("loading");
            pd.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_COLUMNS, null, null, strings[0]);
            ArrayList arrayList = new ArrayList();
            while (cursor.moveToNext()) {
                try {
                    if (new File(cursor.getString(cursor.getColumnIndexOrThrow("_data"))).exists()) {
                        FolderVideo video = new FolderVideo();
                        video.set_ID(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("_id"))));
                        video.setName(cursor.getString(cursor.getColumnIndexOrThrow("_display_name")));
                        video.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                        int video_column_index = cursor.getColumnIndex("_data");
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("date_added"));
                        video.setDateAdded(string);
                        video.setDuration(TheUtility.parseTimeFromMilliseconds(cursor.getString(cursor.getColumnIndexOrThrow("duration"))));
                        video.setResolution(cursor.getString(cursor.getColumnIndexOrThrow("resolution")));
                        video.setSize(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("_size"))));
                        video.setSizeReadable(TheUtility.humanReadableByteCount(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow("_size"))), false));
                        video.setData(cursor.getString(cursor.getColumnIndexOrThrow("_data")));
                        video.setMime(cursor.getString(cursor.getColumnIndexOrThrow("mime_type")));

                        arrayList.add(video);
                    }

                } catch (Exception unused) {
                }
            }
            allVideoList = arrayList;
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            pd.dismiss();
            setAdap();

        }
    }


    public Uri getUriFromPath(String pathMain, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String str = "_id";
        Cursor cursor1 = contentResolver.query(uri, new String[]{str}, "_data=? ", new String[]{pathMain}, null);
        if (cursor1 == null || !cursor1.moveToFirst()) {
            return null;
        }
        int id = cursor1.getInt(cursor1.getColumnIndex(str));
        cursor1.close();
        Uri uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(id);
        return Uri.withAppendedPath(uri2, sb.toString());
    }

    public void scanDeletedMedia(Context context, File file) {
        if (SDK_INT >= 19) {
            context.getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MediaStore.Video.Media.DATA + "= ?", new String[]{file.getAbsolutePath()});
        } else {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == -1) {
            allVideoList.clear();
            new AsyncLoadVideos().execute("date_added DESC");
            Toast.makeText(this, "Delete successfully", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}