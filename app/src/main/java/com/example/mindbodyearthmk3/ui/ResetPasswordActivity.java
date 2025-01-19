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
            String email = ((EditText) findViewById(R.id.etEmail)).getText().toString();
            String newPassword = ((EditText) findViewById(R.id.etNewPassword)).getText().toString();

            AppDatabase db = AppDatabase.getInstance(this);
            User user = db.userDao().findByEmail(email);

            if (user != null) {
                db.userDao().resetPassword(email, newPassword);
                Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Email not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

