package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class fourth extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

TextView textViewUsername;
ImageView logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        auth=FirebaseAuth.getInstance();
        textViewUsername=findViewById(R.id.textViewUsername);
        logOut=findViewById(R.id.logOut);

        user = auth.getCurrentUser();
        if (user==null){
            Intent intent = new Intent(getApplicationContext(),second.class);
            startActivity(intent);
            finish();
        }
        else {
            textViewUsername.setText(user.getDisplayName());
        }

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),second.class);
                startActivity(intent);
                finish();
            }
        });
    }
}