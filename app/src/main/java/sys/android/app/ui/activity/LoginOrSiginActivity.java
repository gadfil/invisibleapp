package sys.android.app.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.InjectView;
import sys.android.app.R;
import sys.android.app.ui.view.RevealBackgroundView;

public class LoginOrSiginActivity extends BaseActivity  implements RevealBackgroundView.OnStateChangeListener,  View.OnClickListener{

    @InjectView(R.id.vRevealBackground)
    RevealBackgroundView vRevealBackground;

    @InjectView(R.id.root)
    RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_sigin);

        vRevealBackground.setOnStateChangeListener(this);
        vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                vRevealBackground.startFromLocation(new int[]{0, vRevealBackground.getHeight()});
                return true;
            }


        });

    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            rlRoot.setVisibility(View.VISIBLE);
            vRevealBackground.setVisibility(View.GONE);
//            userPhotosAdapter = new UserProfileAdapter(this);
//            rvUserProfile.setAdapter(userPhotosAdapter);
        } else {
            rlRoot.setVisibility(View.INVISIBLE);
            vRevealBackground.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
