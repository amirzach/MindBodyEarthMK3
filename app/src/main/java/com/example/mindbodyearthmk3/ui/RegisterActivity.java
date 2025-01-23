package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.User;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btnRegister).setOnClickListener(view -> {
            String username = ((TextInputLayout) findViewById(R.id.registerUsernameTextInputLayout)).getEditText().getText().toString();
            String email = ((TextInputLayout) findViewById(R.id.registerEmailTextInputLayout)).getEditText().getText().toString();
            String password = ((TextInputLayout) findViewById(R.id.registerPasswordTextInputLayout)).getEditText().getText().toString();

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                new Thread(() -> {
                    AppDatabase db = AppDatabase.getInstance(this);
                    User user = new User();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPassword(password);
                    db.userDao().insertUser(user);

                    // Checks if the email is taken

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }).start();
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
