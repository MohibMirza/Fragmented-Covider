package edu.mohibmir.covider.redis;

import org.redisson.api.RLiveObjectService;
import java.util.ArrayList;
import java.util.List;

import edu.mohibmir.covider.redis.RClass.Building;
import edu.mohibmir.covider.redis.RClass.Status;
import edu.mohibmir.covider.redis.RClass.User;
import edu.mohibmir.covider.redis.RClass.Visit;

public class RedisDatabase {

    public static RLiveObjectService rlo = RedisClient.getInstance().redisson.getLiveObjectService();

    public static Building getOrCreateBuilding(String buildingId) {
        if(rlo == null) { System.err.println("Redis Live Object Service Null!"); }
        Building building = rlo.get(Building.class, buildingId);
        if(building == null) {
            building = new Building(buildingId);
            rlo.persist(building);
            building = rlo.get(Building.class, buildingId);
        }

        return building;
    }

    public static User getOrCreateUser(String userId) {
        if(rlo == null) { System.err.println("Redis Live Object Service Null!"); }
        User user = rlo.get(User.class, userId);
        if(user == null) {
            user = new User(userId);
            rlo.persist(user);
            user = rlo.get(User.class, userId);
        }

        return user;
    }

    public static void deleteBuilding(String buildingId) {
        Building building = rlo.get(Building.class, buildingId);
        if(building != null) {
            building.getVisits().clear();
            rlo.delete(building);
        }
    }

    public static void deleteUser(String userId)  {
        User user = rlo.get(User.class, userId);
        if(user != null) {
            user.getVisitedBuildings().clear();
            user.getEnrolledClasses().clear();
            rlo.delete(user);
        }
    }

    public static void calculateRiskScore(String buildingId) {
        Building building = getOrCreateBuilding(buildingId);
        List<Visit> visits = new ArrayList<>(building.getVisits());

        int score = 0;
        int numberOfVisits = 0;
        for(Visit visit : visits) {
            User user = rlo.get(User.class, visit.getUserId());
            if(user == null) continue;

            Status status = user.getCovidStatus().getStatus();

            if(status == Status.healthy) {
                score += 0;
            }else if(status == Status.symptomatic) {
                score += 1;
            }else if(status == Status.infected) {
                score += 2;
            }
            numberOfVisits++;
        }

        double avg = score / numberOfVisits;

        building.setRiskScore(avg);

    }
}
