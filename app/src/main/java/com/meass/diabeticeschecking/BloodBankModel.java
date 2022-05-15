package com.meass.diabeticeschecking;

public class BloodBankModel {
    String name,time,feeentry,feeprice,roomid,timeuuid,uuid;

    public BloodBankModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeeentry() {
        return feeentry;
    }

    public void setFeeentry(String feeentry) {
        this.feeentry = feeentry;
    }

    public String getFeeprice() {
        return feeprice;
    }

    public void setFeeprice(String feeprice) {
        this.feeprice = feeprice;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getTimeuuid() {
        return timeuuid;
    }

    public void setTimeuuid(String timeuuid) {
        this.timeuuid = timeuuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BloodBankModel(String name, String
            time, String feeentry, String feeprice, String roomid, String timeuuid, String uuid) {
        this.name = name;
        this.time = time;
        this.feeentry = feeentry;
        this.feeprice = feeprice;
        this.roomid = roomid;
        this.timeuuid = timeuuid;
        this.uuid = uuid;
    }
}
