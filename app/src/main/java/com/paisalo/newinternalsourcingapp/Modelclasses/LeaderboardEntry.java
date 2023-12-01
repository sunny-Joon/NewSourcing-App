package com.paisalo.newinternalsourcingapp.Modelclasses;

public class LeaderboardEntry {
    private String sno;
    private String userName;
    private String points;

    public LeaderboardEntry(String sno, String userName, String points) {
        this.sno = sno;
        this.userName = userName;
        this.points = points;
    }

    public String getsno() {
        return sno;
    }

    public String getuserName() {
        return userName;
    }

    public String getpoints() {
        return points;
    }
}
