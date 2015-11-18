package com.maxino.pandlk.htmlreportgenerator.requestdummyvalues;

/**
 * Created by pandlk on 11/16/2015.
 */
public class RequestAttachment {

    String attachmentId;
    String requestId;
    String name;
    String path;
    String type;
    String description;
    String status;
    String active;
    String fileType;
    String createdAt;

    public RequestAttachment(
            String attachmentId,
            String requestId,
            String name,
            String path,
            String type,
            String description,
            String status,
            String active,
            String fileType,
            String createdAt){

        this.attachmentId=attachmentId;
        this.requestId=requestId;
        this.name=name;
        this.path=path;
        this.type=type;
        this.description=description;
        this.status=status;
        this.active=active;
        this.fileType=fileType;
        this.createdAt=createdAt;

    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
