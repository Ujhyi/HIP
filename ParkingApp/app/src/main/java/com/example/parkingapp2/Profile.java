package com.example.parkingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    ImageView backBtn;
    TextView Username, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Username = findViewById(R.id.textView15);
        Email = findViewById(R.id.textView16);

        backBtn = findViewById(R.id.profileBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, MainActivity.class));
                finish();
            }
        });

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        Username.setText(username);
        Email.setText(email);

    }
}