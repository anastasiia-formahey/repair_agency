package com.formahei.entity;

import java.io.Serializable;
/**
 * Root of all entities that have identifier field
 * @author Anastasiia Formahei
 * */

public class Entity implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public Entity setId(int id) {
        this.id = id;
        return null;
    }
}
