package sys.android.app.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.ui.view.RevealBackgroundView;

/**
 * Created by gadfil on 14.04.2015.
 */
public abstract class BaseActivity extends ActionBarActivity implements
        RevealBackgroundView.OnStateChangeListener{

    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @Optional
    @InjectView(R.id.root)
    RelativeLayout rlRoot;
    @Optional
    @InjectView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;


    public static final String EXTRA_ARRAY_START_REVEAL_BACKGROUND ="start_reveal_background";
    @Override
    protected void onStop() {
        super.onStop();

//        finish();
//        Log.d("log", "onStop " + this.getLocalClassName());

    }

    @Override
    protected void onPause() {
        super.onPause();
//        finish();
//        Log.d("log", "onPause() " + this.getLocalClassName());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_navigation_menu);
        }

        if(vRevealBackground!=null){
            vRevealBackground.setOnStateChangeListener(this);
            int array[] = getIntent().getIntArrayExtra(EXTRA_ARRAY_START_REVEAL_BACKGROUND);
            if(array==null){
                array =new int[]{0, vRevealBackground.getHeight()};
            }
            final int[] finalArray = array;
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(finalArray);
                    return true;
                }


            });
        }
        getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number = ?", new String[]{DataUtil.getRunnerPassword(this)});
        getContentResolver().delete(CallLog.Calls.CONTENT_URI, "number = ?", new String[]{DataUtil.DEFAULT_PASS});
    }
    public static void launch(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }
  public static void launch(Context context,Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    @Override
    public void onStateChange(int state) {
        if(rlRoot!=null&&vRevealBackground!=null) {
            if (RevealBackgroundView.STATE_FINISHED == state) {
                rlRoot.setVisibility(View.VISIBLE);
                vRevealBackground.setVisibility(View.GONE);
            } else {
                rlRoot.setVisibility(View.INVISIBLE);
                vRevealBackground.setVisibility(View.VISIBLE);
            }
        }
    }

}
