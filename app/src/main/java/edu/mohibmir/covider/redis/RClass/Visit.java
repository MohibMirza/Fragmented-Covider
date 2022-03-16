package edu.mohibmir.covider.redis.RClass;

import java.io.Serializable;
import java.util.Date;

public class Visit implements Serializable {

    private String userId;
    private Date date;

    protected Visit() { }

    public Visit(String userId) {
        this.userId = userId;
        this.date = new Date();
    }

    public Visit(String userId, Date date) {
        this.userId = userId;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public Date getDate() {
        return date;
    }

}
