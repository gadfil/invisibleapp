package sys.android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by gadfil on 14.04.2015.
 */
public class DataUtil {
    public static final String RUNNER_PASSWORD = "runner_password";
    public static final String DEFAULT_PASS = "###123";

   public static final String TOKEN = "token";
   public static final String NAME = "name";
   public static final String EMAIL = "email";
   public static final String LOGIN = "login";
   public static final String PASSWORD = "password";
   public static final String DESCRIPTION = "description";

    public static String getSettingValue(Context context, String setting){
        SharedPreferences sharedPref = context.getSharedPreferences(setting, Context.MODE_PRIVATE);
        return sharedPref.getString(setting, "");
    }

    public static void setSettingValue(Context context, String setting, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(setting, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(setting, value);
        editor.commit();
    }

    public static String getRunnerPassword(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(RUNNER_PASSWORD, Context.MODE_PRIVATE);
        return sharedPref.getString(RUNNER_PASSWORD, DEFAULT_PASS);
    }

    public static void setRunnerPassword(Context context, String password){
        SharedPreferences sharedPref = context.getSharedPreferences(RUNNER_PASSWORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(RUNNER_PASSWORD, password);
        editor.commit();
    }

    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
}
