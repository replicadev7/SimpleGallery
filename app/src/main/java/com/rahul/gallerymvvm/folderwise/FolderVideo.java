package com.rahul.gallerymvvm.folderwise;

import java.io.Serializable;

public class FolderVideo implements Serializable {
    public long _ID;
    private String data;
    private String dateAdded;
    private String duration;
    private String mime;
    public String name;
    private String resolution;
    private long size;
    private String sizeReadable;
    public String title;

    public FolderVideo(long j, String str, String str2, String str3, String str4, String str5, long j2, String str6) {
        this._ID = j;
        this.name = str;
        this.title = str2;
        this.dateAdded = str3;
        this.duration = str4;
        this.resolution = str5;
        this.size = j2;
        this.data = str6;
    }

    public FolderVideo(String str, String str2, String str3, String str4, int i) {
        this.title = str2;
        this.data = str;
        this.resolution = str4;
        this.duration = str3;
    }

    public FolderVideo(String str,String str2) {
        this.data = str;
        this.name = str2;
    }


    public FolderVideo() {
    }

    public long get_ID() {
        return this._ID;
    }

    public void set_ID(long j) {
        this._ID = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(String str) {
        this.dateAdded = str;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String str) {
        this.resolution = str;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long j) {
        this.size = j;
    }

    public String getSizeReadable() {
        return this.sizeReadable;
    }

    public void setSizeReadable(String str) {
        this.sizeReadable = str;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String getMime() {
        return this.mime;
    }

    public void setMime(String str) {
        this.mime = str;
    }
}
