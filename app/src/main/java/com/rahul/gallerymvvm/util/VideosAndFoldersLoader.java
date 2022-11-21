package com.rahul.gallerymvvm.util;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.rahul.gallerymvvm.folderwise.Folder;
import com.rahul.gallerymvvm.folderwise.FolderVideo;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideosAndFoldersLoader {

    private String[] VIDEO_COLUMNS = {"_id", "_display_name", "title", "date_added", "duration", "resolution", "_size", "_data", "mime_type"};
    private Context context;
    private List<FolderVideo> videoList = new ArrayList();

    public VideosAndFoldersLoader(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public List<FolderVideo> fetchAllVideos() {
        Cursor query;
        query = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.VIDEO_COLUMNS, null, null, "date_added DESC");
        if (query != null) {
            try {
                this.videoList = getVideosFromCursor(query);
                query.close();
            } catch (Exception unused) {
            }
        }

        return this.videoList;
    }

    public List<FolderVideo> fetchVideosByFolder(String str) {
        Log.i("bhg", "fetchVideosByFolder---: "+str);
        Cursor query;
        query = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.VIDEO_COLUMNS, "_data Like ?", new String[]{str + "%"}, "date_added DESC");
        ArrayList arrayList = new ArrayList();
        while (query.moveToNext()) {
            if (new File(query.getString(query.getColumnIndexOrThrow("_data"))).getParent().equalsIgnoreCase(str)) {
                if (new File(query.getString(query.getColumnIndexOrThrow("_data"))).exists()) {
                    FolderVideo video = new FolderVideo();
                    video.set_ID(Long.parseLong(query.getString(query.getColumnIndexOrThrow("_id"))));
                    video.setName(query.getString(query.getColumnIndexOrThrow("_display_name")));
                    video.setTitle(query.getString(query.getColumnIndexOrThrow("title")));
                    String string = query.getString(query.getColumnIndexOrThrow("date_added"));
                    video.setDateAdded(string);
                    video.setDuration(TheUtility.parseTimeFromMilliseconds(query.getString(query.getColumnIndexOrThrow("duration"))));
                    video.setResolution(query.getString(query.getColumnIndexOrThrow("resolution")));
                    video.setSize(Long.parseLong(query.getString(query.getColumnIndexOrThrow("_size"))));
                    video.setSizeReadable(TheUtility.humanReadableByteCount(Long.parseLong(query.getString(query.getColumnIndexOrThrow("_size"))), false));
                    video.setData(query.getString(query.getColumnIndexOrThrow("_data")));
                    video.setMime(query.getString(query.getColumnIndexOrThrow("mime_type")));
                    arrayList.add(video);
                }

            }
        }
        return arrayList;
    }


    private List<FolderVideo> getVideosFromCursor(Cursor cursor) {
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
        return arrayList;
    }

    public void deleteVideos(List<Long> list) {
        for (Long l : list) {
            this.context.getContentResolver().delete(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, l.longValue()), null, null);
        }
    }

    public boolean deleteVideosByFolder(String str) {
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {

            String[] strArr = {str + "%"};

            Cursor query;
            query = this.context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data"}, "_data Like ?", strArr, "date_added DESC");

            while (query.moveToNext()) {
                if (new File(query.getString(query.getColumnIndexOrThrow("_data"))).getParent().equalsIgnoreCase(str)) {
                    this.context.getContentResolver().delete(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, Long.parseLong(query.getString(query.getColumnIndexOrThrow("_id")))), null, null);
                }
            }
        }
        return file.delete();
    }

    public List<Folder> fetchAllVideosFolders() {
        Log.e("mmmmmmmmm", "fetchAllFolders:+++++ ");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (FolderVideo video : this.videoList) {
            String parent = new File(video.getData()).getParent();
            String name = new File(parent).getName();
            Folder folder = new Folder();
            folder.setName(name);
            folder.setPath(parent);
            folder.setDateAdded(video.getDateAdded());
            folder.videosPP();
            folder.sizePP(video.getSize());

            if (arrayList2.contains(folder.getPath())) {
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        break;
                    } else if (((Folder) arrayList.get(i2)).getPath().equals(folder.getPath())) {
                        i = i2;
                        break;
                    } else {
                        i2++;
                    }
                }
                ((Folder) arrayList.get(i)).videosPP();
                ((Folder) arrayList.get(i)).sizePP(video.getSize());
                if (!((Folder) arrayList.get(i)).getNewFolder().booleanValue()) {
                    Context context2 = this.context;
                    if (!Preferences.getIsPalayVideo(context2, "" + video.get_ID())) {
                        ((Folder) arrayList.get(i)).setNewFolder(true);
                    }
                }
            } else {
                if (!folder.getNewFolder().booleanValue()) {
                    Context context3 = this.context;
                    if (!Preferences.getIsPalayVideo(context3, "" + video.get_ID())) {
                        folder.setNewFolder(true);
                    }
                }
                arrayList.add(folder);
                arrayList2.add(folder.getPath());

            }
        }
        return arrayList;

    }

    public void deleteFolders(List<String> list) {
        for (String str : list) {
            deleteVideosByFolder(str);
        }
    }
}
