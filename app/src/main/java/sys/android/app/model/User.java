package sys.android.app.model;

import java.util.Arrays;

/**
 * Created by gadfil on 05.05.2015.
 */
public class User {
    private int id;
    private String login;
    private String email;
    private String description;
    private String logoSrc;
    private int  logoId;
    private String   logo;
    private Sex sex;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", logoSrc='" + logoSrc + '\'' +
                ", logoId=" + logoId +
                ", sex=" + sex +
                ", fakePassowrd='" + fakePassowrd + '\'' +
                ", keys=" + Arrays.toString(keys) +
                '}';
    }

    private String fakePassowrd;
    private String[] keys;

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public String getFakePassowrd() {
        return fakePassowrd;
    }

    public void setFakePassowrd(String fakePassowrd) {
        this.fakePassowrd = fakePassowrd;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoSrc() {
        return logoSrc;
    }

    public void setLogoSrc(String logoSrc) {
        this.logoSrc = logoSrc;
    }
}
