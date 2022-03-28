package edu.mohibmir.covider.redis.RClass;

import org.redisson.api.RAtomicDouble;
import org.redisson.api.RedissonClient;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.mohibmir.covider.redis.RedisClient;

public class Building implements Serializable {

    private String name;

    private static RedissonClient redisson;

    public Building(String name) {

        this.name = "building." + name.toLowerCase();

        if (redisson == null) { redisson = RedisClient.getInstance().redisson; }

        if(!redisson.getAtomicDouble(this.name + ".riskscore").isExists()) {
            redisson.getAtomicDouble(this.name + ".riskscore").set(1000.0);
            redisson.getMap(this.name + ".lastVisited");
        }

    }

    public void setRiskScore(double score){
        redisson.getAtomicDouble(name + ".riskscore").set(score);
    }

    public void decrementRiskScore() {
        redisson.getAtomicDouble(name + ".riskscore").decrementAndGet();
    }

    public void decrementRiskScore(int amount) {
        RAtomicDouble riskscore = redisson.getAtomicDouble(name + ".riskscore");
        for(int i = 0; i < amount; i++)
            riskscore.decrementAndGet();
    }

    public void addVisit(String userId) {
        userId = userId.toLowerCase();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(date);

        redisson.getMap(name + ".lastVisited").put(userId, strDate);
    }

    // format as yyyy-MM-dd
    public void addLastVisit(String userId, String strDate) {
        userId = userId.toLowerCase();

        redisson.getMap(name + ".lastVisited").put(userId, strDate);
    }

    public String getName() {
        return name;
    }

    public double getRiskScore() {
        return (double) redisson.getAtomicDouble(name + ".riskscore").get();
    }

    public boolean checkIfVisitedWithin10Days(String userId) {
        userId = userId.toLowerCase();
        Calendar cal = Calendar.getInstance();

        int rollbackTime = 10;
        for(int i = 0; i < 10; i++) {
            cal.roll(Calendar.DATE, false);
        }
        Date pastDate = cal.getTime();
        for(int i = 0; i < 10; i++) {
            cal.roll(Calendar.DATE, true);
        }

        String strDate = (String) redisson.getMap(name + ".lastVisited").get(userId);
        if(strDate == null){
            System.err.println("strDate is null");
            return false;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date parsedDate = formatter.parse(strDate);
            if(parsedDate.after(pastDate)){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public void delete() {
        redisson.getAtomicDouble(name + ".riskscore").delete();

        redisson.getMap(name + ".lastVisited").clear();
        redisson.getMap(name + ".lastVisited").delete();
    }

    private static boolean checkIfExists(String buildingName) {
        return (redisson.getAtomicDouble("building." + buildingName.toLowerCase() + ".riskscore").isExists());
    }
}