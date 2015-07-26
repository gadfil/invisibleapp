package sys.android.app.ui.activity;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.steamcrafted.loadtoast.LoadToast;

import butterknife.InjectView;
import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.facke.FakeUsers;
import sys.android.app.model.User;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.etEmail)
    MaterialEditText etEmail;
    @InjectView(R.id.etPassowrd)
    MaterialEditText etPassword;
    @InjectView(R.id.etDescription)
    MaterialEditText etDescription;
    @InjectView(R.id.etLogin)
    MaterialEditText etLogin;

    @InjectView(R.id.btForgetPassword)
    ButtonFlat btForgetPassword;
    @InjectView(R.id.btSignUp)
    ButtonRectangle btSignUp;
    @InjectView(R.id.btLogIn)
    ButtonFlat btLogIn;
    @InjectView(R.id.avatar)
    ImageView avatar;
    @InjectView(R.id.btOk)
    ButtonFloat btOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");

        btSignUp.setOnClickListener(this);
        btLogIn.setOnClickListener(this);
        btOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btOk:
                launch(SignUpActivity.this, UsersActivity.class);
                break;
            case R.id.btLogIn:
                launch(this, LoginActivity.class);
                break;
            case R.id.btSignUp:
                if (!etEmail.getText().toString().isEmpty() &&
                        !etPassword.getText().toString().isEmpty() &&
                        !etDescription.getText().toString().isEmpty() &&
                        !etLogin.getText().toString().isEmpty()) {
                    FakeUsers users = new FakeUsers();
                    final User user = users.getUserByEmail(etEmail.getText().toString());
                    if(user!=null) {
                        DataUtil.setSettingValue(this, DataUtil.DESCRIPTION, user.getDescription());
                        DataUtil.setSettingValue(this, DataUtil.LOGIN, user.getLogin());
                        DataUtil.setSettingValue(this, DataUtil.EMAIL, user.getEmail());
                        final LoadToast lt = new LoadToast(this);
                        lt.setText("Create avatar...");
                        lt.setTextColor(getResources().getColor(R.color.accent));
                        lt.setProgressColor(getResources().getColor(R.color.accent));
                        lt.show();
                        Picasso.with(this).load(user.getLogoId()).fetch();
                        Picasso.with(this).load("file:///android_asset/img/wallpaper_51.jpg").fetch(new Callback() {
                            @Override
                            public void onSuccess() {
                                lt.success();
                                avatar.setVisibility(View.VISIBLE);
                                btOk.setVisibility(View.VISIBLE);
                                Picasso.with(SignUpActivity.this).load(user.getLogoId()).into(avatar);
//                                try {
//                                    Thread.sleep(50000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }finally {
//                                    launch(SignUpActivity.this, UsersActivity.class);
//                                }
                            }

                            @Override
                            public void onError() {
                                lt.success();
                                avatar.setVisibility(View.VISIBLE);
                                btOk.setVisibility(View.VISIBLE);
                                Picasso.with(SignUpActivity.this).load(user.getLogoId()).into(avatar);
//                                try {
//                                    Thread.sleep(50000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }finally {
//                                    launch(SignUpActivity.this, UsersActivity.class);
//                                }
                            }
                        });
//                            avatar.setVisibility(View.VISIBLE);
//                            Picasso.with(this).load(user.getLogoId()).into(avatar);
//                            try {
//                                Thread.sleep(50000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }finally {
//                                launch(this, UsersActivity.class);
//                            }



                    }else{
                        new SnackBar(this, "data are not correct","", null).show();
                    }

                }else{
                    new SnackBar(this, "Enter all field ","", null).show();
                }
                break;
        }
    }
}
