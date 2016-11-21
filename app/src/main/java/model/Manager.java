package model;

/**
 * Created by Julien on 22/10/2016.
 */

public class Manager {

    private String email;
    private String password;
    private String id;



    public Manager(){
        this.email = null;
        this.password = null;

    }

    public Manager(String email, String password){
        this.email = email;
        this.password = password;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
