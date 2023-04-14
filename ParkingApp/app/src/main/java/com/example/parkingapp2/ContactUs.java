package com.example.parkingapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ContactUs extends AppCompatActivity {

    ImageView backButton;
    EditText contactMessage, contactSubject;
    Button sentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        backButton = findViewById(R.id.backButton);
        contactMessage = findViewById(R.id.message);
        sentButton = findViewById(R.id.sent);
        contactSubject = findViewById(R.id.subject);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUs.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "david.ujh@gmail.com";
                String message = contactMessage.getText().toString();
                String subject = contactSubject.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                intent.setType("message/rfc822");
                startActivity(intent);
            }
        });

    }
}