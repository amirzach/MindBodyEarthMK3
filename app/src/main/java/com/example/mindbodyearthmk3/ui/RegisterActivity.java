package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.User;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btnRegister).setOnClickListener(view -> {
            String username = ((EditText) findViewById(R.id.etUsername)).getText().toString();
            String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
            String password = ((EditText) findViewById(R.id.etPassword)).getText().toString();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                AppDatabase db = AppDatabase.getInstance(this);
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                db.userDao().insertUser(user);

                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

