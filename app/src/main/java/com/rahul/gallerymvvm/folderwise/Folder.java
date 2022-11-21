package com.rahul.gallerymvvm.folderwise;

public class Folder {
    private Boolean isNewFolder;
    private String name;
    private String path;
    private long totalSize;
    private long totalVideos;
    private String dateAdded;


    public Folder() {
        this.isNewFolder = false;
        this.name = null;
        this.totalVideos = 0;
        this.totalSize = 0;
    }

    public Folder(String str, long j) {
        this.isNewFolder = false;
        this.name = str;
        this.totalVideos = j;
    }


    public String getDateAdded() {
        return this.dateAdded;
    }

    public void setDateAdded(String str) {
        this.dateAdded = str;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long j) {
        this.totalSize = j;
    }

    public long getTotalVideos() {
        return this.totalVideos;
    }

    public void setTotalVideos(long j) {
        this.totalVideos = j;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void videosPP() {
        this.totalVideos++;
    }

    public void sizePP(long j) {
        this.totalSize += j;
    }

    public boolean equals(Object obj) {

        if (obj == null || !Folder.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        Folder folder = (Folder) obj;
        String str = this.name;
        if (str != null) {
            return str.equals(folder.name);
        }
        if (folder.name == null) {
            return true;
        }
        return false;
    }

    public Boolean getNewFolder() {
        return this.isNewFolder;
    }

    public void setNewFolder(Boolean bool) {
        this.isNewFolder = bool;
    }
}
