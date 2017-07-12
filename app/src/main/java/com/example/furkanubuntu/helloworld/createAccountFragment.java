package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by furkanubuntu on 7/12/17.
 */

public class createAccountFragment extends Fragment {

    DbHelper helperInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_account_fragment,container, false);

        helperInstance = new DbHelper(getActivity());
        Button registerButton = (Button) view.findViewById(R.id.registerButton);
        final EditText usernameText = (EditText) view.findViewById(R.id.newUserUsername);
        final EditText passwordText = (EditText) view.findViewById(R.id.newUserPassword);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                if(username.length() > 5 && password.length() > 5) {
                    if (helperInstance.addCredentials(username, password)) {
                        Toast toast = Toast.makeText(getActivity(), "Successfully created new account.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getActivity(), "Username already exists.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(), "Both username and password needs to be at least 6 character long", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        return view;
    }
}
