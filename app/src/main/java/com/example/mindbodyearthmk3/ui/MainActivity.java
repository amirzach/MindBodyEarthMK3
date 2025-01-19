package com.example.mindbodyearthmk3.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to navigate to Login Page
        findViewById(R.id.btnLogin).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, LoginActivity.class))
        );

        // Button to navigate to Register Page
        findViewById(R.id.btnRegister).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, RegisterActivity.class))
        );
    }
}
