package com.example.mindbodyearthmk3.ui;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mindbodyearthmk3.R;
import com.example.mindbodyearthmk3.database.AppDatabase;
import com.example.mindbodyearthmk3.database.User;

public class ResetPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        findViewById(R.id.btnResetPassword).setOnClickListener(view -> {
            String email = ((EditText) findViewById(R.id.emailTextInputLayout)).getText().toString();
            String newPassword = ((EditText) findViewById(R.id.newPasswordTextInputLayout)).getText().toString();

            // Validate inputs
            if (email.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(this);
                User user = db.userDao().findByEmail(email);

                if (user != null) {
                    db.userDao().resetPassword(email, newPassword);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(this, "Email not found!", Toast.LENGTH_SHORT).show());
                }
            }).start();
        });
    }
}
