package main.java.server.models;

import java.io.Serializable;

public class Course implements Serializable {

    private String name;
    private String code;
    private String session;

    public Course(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSession() {
        return session;
    }

    @Override
    public String toString() {
        return name + "\t" + code;
    }
}
