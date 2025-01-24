package com.example.mindbodyearthmk3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.User;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout usernameTextInputLayout, passwordTextInputLayout;
    public static final int REGISTER_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppDatabase.initializeDatabase(this);

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

        findViewById(R.id.btnLogin).setOnClickListener(view -> {
            String username = ((EditText) usernameTextInputLayout.getEditText()).getText().toString();
            String password = ((EditText) passwordTextInputLayout.getEditText()).getText().toString();

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                User user = db.userDao().login(username, password);

                runOnUiThread(() -> {
                    if (user != null) {
                        startActivity(new Intent(this, HomePageActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        findViewById(R.id.tvResetPassword).setOnClickListener(view ->
                startActivity(new Intent(this, ResetPasswordActivity.class))
        );

        findViewById(R.id.btnRegister).setOnClickListener(view ->
                startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_REQUEST_CODE)
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
        }
    }
}
