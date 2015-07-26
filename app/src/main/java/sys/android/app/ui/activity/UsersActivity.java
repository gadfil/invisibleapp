package sys.android.app.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.widgets.Dialog;
import com.github.florent37.materialviewpager.MaterialViewPager;

import butterknife.InjectView;
import sys.android.app.R;
import sys.android.app.model.User;
import sys.android.app.ui.fragment.RecyclerViewFragment;
import sys.android.app.ui.fragment.UserListFragment;
import sys.android.app.ui.view.MyDialog;

public class UsersActivity extends BaseActivity  implements View.OnClickListener{

    @InjectView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    @InjectView(R.id.btAdd)
    ButtonFloat btAdd;

    String []walpers = new String [] {
            "file:///android_asset/img/mosc.jpg",
            "file:///android_asset/img/newyorkcity-650x4872.jpg",
            "file:///android_asset/img/ny.jpg",
            "file:///android_asset/img/ny1.jpg",
            "file:///android_asset/img/peknight.jpg",
            "file:///android_asset/img/shanh4.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        btAdd.setOnClickListener(this);
        setTitle("");
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            int oldPosition = -1;

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:

                        return UserListFragment.newInstance(1);
                    case 1:
                        return UserListFragment.newInstance(2);
                    //case 2:
                    //    return ListViewFragment.newInstance();
                    //case 3:
                    //    return WebViewFragment.newInstance();
                    default:
                        return RecyclerViewFragment.newInstance();
                }
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);

                //only if position changed
                if (position == oldPosition)
                    return;
                oldPosition = position;

                int color = 0;
                String imageUrl = "";
                switch (position) {
                    case 0:
                        btAdd.setVisibility(View.GONE);
                        color = getResources().getColor(R.color.primary);

                        break;
                    case 1:
                        btAdd.setBackgroundColor(getResources().getColor(R.color.accent_color));
                        btAdd.setVisibility(View.VISIBLE);
                        color = getResources().getColor(R.color.accent_color);
                        break;
                    case 2:
                        color = getResources().getColor(R.color.cyan);
                        break;
                    case 3:
                        color = getResources().getColor(R.color.red);
                        break;
                }

                final int fadeDuration = 400;
                imageUrl = walpers[ (int)(Math.random() * walpers.length)];
                mViewPager.setImageUrl(imageUrl, fadeDuration);
                mViewPager.setColor(color, fadeDuration);

            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Online";
                    case 1:
                        return "Friends";
                    case 2:
                        return "Profile";
//                    case 3:
//                        return "Divertissement";
                }
                return "";
            }
        });
        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_view:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra(CommentActivity.EXTRA_EMAIL, ((User) v.getTag()).getEmail());
                Log.e("log", "email onClick " + ((User) v.getTag()).getEmail());
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                intent.putExtra(CommentActivity.ARG_DRAWING_START_LOCATION, startingLocation[1]);
                launch(this, intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.btAdd:
                MyDialog dialog = new MyDialog(this, "Input email or code", "Input email or code");

                dialog.show();
                break;
        }

    }
    public interface UserMessege{
        public void messege(View v);

    }
}
