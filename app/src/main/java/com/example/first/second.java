package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class second extends AppCompatActivity {
    EditText editTextPersonName;
    EditText editTextLoginPassword;
    TextView forgetPassword;
    ImageView facebookLink;
    ImageView googleLink;
    TextView signUp;
    Button loginBtn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(second.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(second.this, fourth.class);
            startActivity(intent);
            finish();
        }
    }
    public static class NetworkUtils{
        public static boolean isNetworkAvailable(Context context){
            ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager!=null){
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
            }
            return false;
        }
    }

    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        signUp=findViewById(R.id.signUp);
        forgetPassword = findViewById(R.id.forgetPassword);
        facebookLink=findViewById(R.id.fackbookLink);
        googleLink=findViewById(R.id.googleLink);
        loginBtn=findViewById(R.id.loginBtn);
        editTextPersonName=findViewById(R.id.editTextPersonName);
        editTextLoginPassword=findViewById(R.id.editTextLoginPassword);

        if (!NetworkUtils.isNetworkAvailable(this)){
            showNoInternetDialog();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(second.this, third.class);
                startActivity(intent);
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(second.this, "It's Working", Toast.LENGTH_SHORT).show();
            }
        });
        facebookLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(second.this, "Facbook Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        googleLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(second.this, "Google Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email, password;
                    email = String.valueOf(editTextPersonName.getText());
                    password = String.valueOf(editTextLoginPassword.getText());

                    if (TextUtils.isEmpty(email)){
                        Toast.makeText(second.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)){
                        Toast.makeText(second.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(second.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), fourth.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(second.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
        });
    }
    private void showNoInternetDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_internet_issue);
        Button okay = dialog.findViewById(R.id.okay);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });
        dialog.setCancelable(false);

        dialog.show();
    }
}