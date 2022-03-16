package edu.mohibmir.covider.redis.RClass;

import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@REntity
public class User implements Serializable {

    @RId
    private String userId;
    private String password;
    private String firstName;
    private String lastName;

    private List<String> visitedBuildings;
    private List<String> enrolledClasses;
    private boolean isInstructor;

    private CovidStatus covidStatus;

    protected User() { }

    public User(String userId) {
        this.userId = userId;
        this.password = "";
        this.firstName = "John";
        this.lastName = "Doe";
        this.visitedBuildings = new ArrayList<String>();
        this.enrolledClasses = new ArrayList<String>();
        this.isInstructor = false;
        this.covidStatus = new CovidStatus(Status.healthy, new Date());
    }

    public String getUserId(){
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CovidStatus getCovidStatus() {
        return covidStatus;
    }

    public List<String> getVisitedBuildings() {
        return visitedBuildings;
    }

    public List<String> getEnrolledClasses() {
        return enrolledClasses;
    }

    public boolean getIsInstructor() {
        return isInstructor;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIsInstructor(boolean bool) {
        isInstructor = bool;
    }

    public void setCovidStatus(CovidStatus covidStatus) {
        this.covidStatus = covidStatus;
    }

}
