package server;

import java.io.Serializable;

public class Course implements Serializable {
    private String code ;
    private String name ;
    private String session ;

    public Course(String code, String name, String session) {
        this.code = code;
        this.name = name;
        this.session = session;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
