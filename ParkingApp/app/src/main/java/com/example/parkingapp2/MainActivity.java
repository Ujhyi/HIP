package com.example.parkingapp2;

import androidx.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    TextView text1, text2;
    ImageView circle1,circle2, map1, map2;
    private DatabaseReference mDatabase;


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        map1 = findViewById(R.id.map1);
        map2 = findViewById(R.id.map2);

        map1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                LatLng markerPosition = new LatLng(48.73361617204469, 21.24344258528177);
                intent.putExtra("markerPosition", markerPosition);
                intent.putExtra("markerName", "Vysokoškolská");
                startActivity(intent);
            }
        });

        map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                LatLng markerPosition = new LatLng(48.6973523329004, 21.23318003776324);
                intent.putExtra("markerPosition", markerPosition);
                intent.putExtra("markerName", "Jedlíková");
                startActivity(intent);
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = database.getReference("Parking1");
        DatabaseReference ref2 = database.getReference("Parking2");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                String text = text1.getText().toString();
                text = text.substring(0, text.length() - 1);
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object value = childSnapshot.getValue();
                    if (value instanceof Boolean && (Boolean) value) {
                        count++;
                    }
                }

                String s = Integer.toString(count);
                text = text + s;
                text1.setText(text);
                if (count < 1){
                    circle1.setImageResource(R.drawable.circle1);
                }else{
                    circle1.setImageResource(R.drawable.circle2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // ...
            }
        });

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;
                String text = text2.getText().toString();
                text = text.substring(0, text.length() - 1);
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Object value = childSnapshot.getValue();
                    if (value instanceof Boolean && (Boolean) value) {
                        count++;
                    }
                }

                String s = Integer.toString(count);
                text = text + s;
                text2.setText(text);
                if (count < 1){
                    circle2.setImageResource(R.drawable.circle1);
                }else{
                    circle2.setImageResource(R.drawable.circle2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.profile:{
                        startActivity(new Intent(MainActivity.this, Profile.class));
                        break;
                    }
                    case R.id.parking:{
                        Toast.makeText(MainActivity.this, "PARKING", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.logout:{
                        FirebaseAuth.getInstance().signOut();
                        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);
                        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    checkUser();
                                }
                                else {

                                }
                            }
                        });
                        break;
                    }
                    case R.id.support:{
                        startActivity(new Intent(MainActivity.this, ContactUs.class));
                        finish();
                        break;
                    }
                    case R.id.aboutus:{
                        startActivity(new Intent(MainActivity.this, AboutUs.class));
                        finish();
                        break;
                    }
                }
                return false;
            }
        });
    }


    private void checkUser(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(this, signin.class));
            finish();
        }else{
            String email = firebaseUser.getEmail();

        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}