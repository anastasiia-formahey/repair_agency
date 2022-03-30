package com.formahei.entity;
/**
 * Feedback entity
 * @author Anastasiia Formahei
 * */
public class Feedback extends Entity{
    int id;
    String description;
    String dateTime;
    Integer idRequest;
    String masterLogin;
    int stars;

    public Feedback(String description, String dateTime, Integer idRequest, String masterLogin, int stars) {
        this.description = description;
        this.dateTime = dateTime;
        this.idRequest = idRequest;
        this.masterLogin = masterLogin;
        this.stars = stars;
    }

    @Override
    public Entity setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public String getMasterLogin() {
        return masterLogin;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", idRequest=" + idRequest +
                ", masterLogin='" + masterLogin + '\'' +
                ", stars=" + stars +
                '}';
    }
}
