package com.meass.diabeticeschecking;

public class Lottery_Model {
    String username,useremail,lottery_name,lottery_price,LotteryPrize,timem,email,userid,image;

    public Lottery_Model() {
    }

    public Lottery_Model(String username, String useremail, String lottery_name, String lottery_price,
                         String lotteryPrize, String timem, String email, String userid, String image) {
        this.username = username;
        this.useremail = useremail;
        this.lottery_name = lottery_name;
        this.lottery_price = lottery_price;
        LotteryPrize = lotteryPrize;
        this.timem = timem;
        this.email = email;
        this.userid = userid;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public String getLottery_price() {
        return lottery_price;
    }

    public void setLottery_price(String lottery_price) {
        this.lottery_price = lottery_price;
    }

    public String getLotteryPrize() {
        return LotteryPrize;
    }

    public void setLotteryPrize(String lotteryPrize) {
        LotteryPrize = lotteryPrize;
    }

    public String getTimem() {
        return timem;
    }

    public void setTimem(String timem) {
        this.timem = timem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
