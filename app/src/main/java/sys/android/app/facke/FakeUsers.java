package sys.android.app.facke;

import android.util.Log;

import sys.android.app.R;
import sys.android.app.model.Sex;
import sys.android.app.model.User;

/**
 * Created by gadfil on 05.05.2015.
 */
public class FakeUsers {
    public User superman;
    public User macho;
    public User beauty;
    public User mrs;
//    public User male;
    public User maleMale;
    public User deutschSamuraj;
    public User[]users;

    public FakeUsers() {
        superman = new User();
        superman.setSex(Sex.MAN);
        superman.setLogoId(R.drawable.superman);
        superman.setLogo("file:///android_asset/avatar/SuperMan.jpg");
        superman.setLogin("Superman");
        superman.setEmail("superman@gmail.com");
        superman.setFakePassowrd("123");
        superman.setDescription("Superman description ...");
        superman.setKeys(new String[]{"super", "superman"});


        macho = new User();
        macho.setSex(Sex.MAN);
        macho.setLogoId(R.drawable.joker);
        macho.setLogo("file:///android_asset/avatar/joker.jpg");
        macho.setLogin("Joker");
        macho.setEmail("joker@gmail.com");
        macho.setFakePassowrd("123");
        macho.setDescription("Joker ...");
        macho.setKeys(new String[]{"joker"});

        beauty = new User();
        beauty.setSex(Sex.WOMAN);
        beauty.setLogoId(R.drawable.haus_frau);
        beauty.setLogo("file:///android_asset/avatar/haus_frau.jpg");
        beauty.setLogin("Haus Frau");
        beauty.setEmail("haus_frau@gmail.com");
        beauty.setFakePassowrd("123");
        beauty.setDescription("Haus frau");
        beauty.setKeys(new String[]{"haus frau", "frau"});

        mrs = new User();
        mrs.setSex(Sex.WOMAN);
        mrs.setLogoId(R.drawable.lady);
        mrs.setLogo("file:///android_asset/avatar/lady.jpeg");
        mrs.setLogin("Lady");
        mrs.setEmail("lady@gmail.com");
        mrs.setFakePassowrd("123");
        mrs.setDescription("Lady description.....");
        mrs.setKeys(new String[]{"lady"});

//        male = new User();
//        male.setSex(Sex.WOMAN);
//        male.setLogoId(R.drawable.lady_boss);
//        male.setLogo("file:///android_asset/avatar/lady_boss.png");
//        male.setLogin("Lady Boss");
//        male.setEmail("lady_boss.png@gmail.com");
//        male.setFakePassowrd("123");
//        male.setDescription("Lady Boss description ...");
//        male.setKeys(new String[]{"lady boss", "lady"});

        maleMale = new User();
        maleMale.setSex(Sex.MAN);
        maleMale.setLogoId(R.drawable.courage_face);
        maleMale.setLogo("file:///android_asset/avatar/courage_face.jpg");
        maleMale.setLogin("Courage Face");
        maleMale.setEmail("courage_face@gmail.com");
        maleMale.setFakePassowrd("123");
        maleMale.setDescription("Courage Face description ....");
        maleMale.setKeys(new String[]{"courage face", "courage"});


        deutschSamuraj = new User();
        deutschSamuraj.setSex(Sex.MAN);
        deutschSamuraj.setLogoId(R.drawable.deutsch_samuraj);
        deutschSamuraj.setLogo("file:///android_asset/avatar/deutsch_samuraj.jpg");
        deutschSamuraj.setLogin("Deutsch Samuraj");
        deutschSamuraj.setEmail("deutsch_samuraj@gmail.com");
        deutschSamuraj.setFakePassowrd("123");
        deutschSamuraj.setDescription("Deutsch Samuraj  description ....");
        deutschSamuraj.setKeys(new String[]{"deutsch samuraj", "samuraj"});

        users= new User[]{mrs, superman, beauty, macho, maleMale, deutschSamuraj};

    }

    public User getUserByEmail(String email){
        switch (email) {
            case "joker@gmail.com":
                return macho;
            case "superman@gmail.com":
                return superman;
            case "haus_frau@gmail.com":
                return beauty;
            case "lady@gmail.com":
                return mrs;
//            case "male@gmail.com":
//                return male;

            case "courage_face@gmail.com":
                return maleMale;

            case "deutsch_samuraj@gmail.com":
                return deutschSamuraj;


        }
        Log.e("log", "email " + email + " not found");
        return null;
    }
}
