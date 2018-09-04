package com.shiyuji.db;

import org.litepal.crud.LitePalSupport;

public class personalInformation extends LitePalSupport {
    private int id;
    private String accountNumber;
    private String password;
    private String headPortraitURL;
    private String nickname;
    private String briefIntroduction;
    private String registerTime;
    private int concernNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortraitURL() {
        return headPortraitURL;
    }

    public void setHeadPortraitURL(String headPortraitURL) {
        this.headPortraitURL = headPortraitURL;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public int getConcernNum() {
        return concernNum;
    }

    public void setConcernNum(int concernNum) {
        this.concernNum = concernNum;
    }
}
