package service;

import java.io.File;
import java.util.Set;

public class BatchContext {
    private Set<File> fileList;
    private Set<String> nameList;
    private Set<String> uploadedNameList;
    private Set<String> uploadFailedList;
    private String status;
    private String xmlStr;

    public Set<File> getFileList() {
        return fileList;
    }

    public void setFileList(Set<File> fileList) {
        this.fileList = fileList;
    }

    public Set<String> getNameList() {
        return nameList;
    }

    public void setNameList(Set<String> nameList) {
        this.nameList = nameList;
    }

    public Set<String> getUploadedNameList() {
        return uploadedNameList;
    }

    public void setUploadedNameList(Set<String> uploadedNameList) {
        this.uploadedNameList = uploadedNameList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getXmlStr() {
        return xmlStr;
    }

    public void setXmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
    }

    public Set<String> getUploadFailedList() {
        return uploadFailedList;
    }

    public void setUploadFailedList(Set<String> uploadFailedList) {
        this.uploadFailedList = uploadFailedList;
    }
}
