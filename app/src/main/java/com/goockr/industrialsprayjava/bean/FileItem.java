package com.goockr.industrialsprayjava.bean;

public class FileItem {

    String name;
    String updateTime;
    boolean isCheck = false;
    String filePath;
    String fileSize;

    public String getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public FileItem(String name, String updateTime, String filePath, String fileSize) {

        this.name = name;
        this.updateTime = updateTime;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
