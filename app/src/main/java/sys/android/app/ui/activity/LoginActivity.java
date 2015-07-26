package sys.android.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.InjectView;
import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.facke.FakeUsers;
import sys.android.app.model.User;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.etEmail)
    MaterialEditText etEmail;
    @InjectView(R.id.etPassowrd)
    MaterialEditText etPassword;
    @InjectView(R.id.btForgetPassword)
    ButtonFlat btForgetPassword;
    @InjectView(R.id.btSignUp)
    ButtonFlat btSignUp;
    @InjectView(R.id.btLogIn)
    ButtonRectangle btLogIn;

    FakeUsers users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");
        btForgetPassword.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        btLogIn.setOnClickListener(this);
        btSignUp.setOnClickListener(this);
        users = new FakeUsers();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id. btForgetPassword:
                Log.d("log", "isNetwork " + isNetworkAvailable());
                if(isNetworkAvailable(v)){

                }
                break;
            case R.id. btSignUp:
                launch(this, new Intent(this,  SignUpActivity.class) );
                break;
            case R.id. btLogIn:
                if(isNetworkAvailable(v)){
                    Log.d("log", "...btLogIn...   " );
                    String email = etEmail.getText().toString();
                    String pass = etPassword.getText().toString();
                    if(email.isEmpty()){
                        new SnackBar(this, "Enter email","", null).show();
                    }else  if(pass.isEmpty()){
                       new SnackBar(this, "Enter password","", null).show();
                    }else{
                        User user = users.getUserByEmail(email);
                        if(user!=null){
                            DataUtil.setSettingValue(this, DataUtil.DESCRIPTION, user.getDescription());
                            DataUtil.setSettingValue(this, DataUtil.LOGIN, user.getLogin());
                            DataUtil.setSettingValue(this, DataUtil.EMAIL, user.getEmail());
                            launch(this, new Intent(this, UsersActivity.class));
                        }else {
                            new SnackBar(this, "Wrong email or password ","", null).show();
                        }
                    }
                }
                break;
        }


    }


    private boolean isNetworkAvailable(final View view){
        boolean networkState = isNetworkAvailable();
        if(!networkState){
            SnackBar snackbar = new SnackBar(this, "No connection",
                    "repeat", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.this.onClick(view);
                }
            });
            snackbar.show();

        }
        Log.d("log", "return   " + networkState);
        return networkState;

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
