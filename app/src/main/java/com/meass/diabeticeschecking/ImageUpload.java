package com.meass.diabeticeschecking;

public class ImageUpload {
    String username,phone,image,roomid,email,useruuid,uuid,time;

    public ImageUpload(String username,
                       String phone, String image, String roomid, String email, String useruuid, String uuid, String time) {
        this.username = username;
        this.phone = phone;
        this.image = image;
        this.roomid = roomid;
        this.email = email;
        this.useruuid = useruuid;
        this.uuid = uuid;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ImageUpload() {
    }
}
