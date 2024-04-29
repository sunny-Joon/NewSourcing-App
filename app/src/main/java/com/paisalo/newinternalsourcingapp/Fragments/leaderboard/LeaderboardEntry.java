package com.paisalo.newinternalsourcingapp.Fragments.leaderboard;

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

    public void setSno(String sno) {
        this.sno = sno;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
