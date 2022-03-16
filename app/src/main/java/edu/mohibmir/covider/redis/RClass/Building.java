package edu.mohibmir.covider.redis.RClass;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@REntity
public class Building implements Serializable {
    @RId
    private String name;

    private double riskScore;
    private List<Visit> visits;

    public Building(String name) {
        this.name = name;
        this.riskScore = 0.0;
        this.visits = new ArrayList<>();
    }

    protected Building() { }

    public void setRiskScore(double score){
        this.riskScore = score;
    }

    public String getName() {
        return name;
    }

    public double getRiskScore() {
        return riskScore;
    }

    public List<Visit> getVisits() {
        return visits;
    }
}
