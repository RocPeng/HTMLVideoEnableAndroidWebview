package com.roc.helloapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roc.helloapp.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "EVENT";
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        MyclickListener click=new MyclickListener() ;
        this.findViewById(R.id.myViewGroup).setOnClickListener(click);
        this.findViewById(R.id.myView).setOnClickListener(click);
    }
    class MyclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.myViewGroup:
                    Log.d(TAG,"click ViewGroup");
                    Toast.makeText(LoginActivity.this,"ViewGroup Click", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.myView:
                    Log.d(TAG,"click View");
                    Toast.makeText(LoginActivity.this, "View Click", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}

