package sys.android.app.model;

import android.graphics.Bitmap;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by gadfil on 07.05.2015.
 */
public class Comment {
    public static final String PHOTO_TAG = "[photo]";
    User user;
    String comment;
    Date date;
    Bitmap photo;
    public User getUser() {
        return user;
    }

    public Comment() {
        date = new Date();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
