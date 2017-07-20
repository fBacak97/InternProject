package com.example.furkanubuntu.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by furkanubuntu on 7/12/17.
 */

public class LoginActivity extends AppCompatActivity {

    DbHelper helperInstance;
    View fragmentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("ExitCommand", false)) {
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_activity);

        fragmentView = findViewById(R.id.createAccountFragment);
        fragmentView.setVisibility(View.INVISIBLE);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText usernameText = (EditText) findViewById(R.id.usernameText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        TextView createAccountText = (TextView) findViewById(R.id.createAccount);

        helperInstance = new DbHelper(getBaseContext());

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if(helperInstance.checkCredentials(username,password) != 0) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, username);
                    intent.putExtra(Intent.EXTRA_SUBJECT, password);
                    intent.putExtra(Intent.EXTRA_TITLE, helperInstance.checkCredentials(username,password));
                    startActivity(intent);
                }
                else{
                    Toast toast = Toast.makeText(getBaseContext(),"You have entered wrong credentials.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(fragmentView.getVisibility() == View.VISIBLE) {
                fragmentView.setVisibility(View.INVISIBLE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
