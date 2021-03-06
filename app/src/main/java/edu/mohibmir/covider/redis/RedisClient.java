package edu.mohibmir.covider.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RedisClient {

    private static RedisClient instance;

    public RedissonClient redisson;


    private String address;
    private Config config;


    public RedisClient(String address) {
        this.address = address;
        config = new Config();
        config.setCodec(new SerializationCodec());
        config.useSingleServer().setAddress(address);
        instance = this;
        System.out.println("Instantiated Redis Client");
    }

    public void start() {
        redisson = Redisson.create(config);
        System.out.println("Redis Client Starting Up");
    }

    public void shutdown() {
        if(redisson.isShutdown()){
            System.out.println("Redis Client is already shut down...");
            return;
        }
        redisson.shutdown();
        System.out.println("Client Shutting Down");
    }

    public boolean isRunning() {
        return !(redisson.isShutdown());
    }

    public void loadConfig(String fileName) throws IOException {
        String content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
        config = Config.fromJSON(content);
        config.useSingleServer().setAddress(address);
    }

    public static RedisClient getInstance() {
        return instance;
    }

//    public void writeOutConfig(String fileName) throws IOException {
//        com.kingfrozo.inv.Main.writeToFile("com.kingfrozo.inv.config.yml", com.kingfrozo.inv.config.toJSON());
//    }







}