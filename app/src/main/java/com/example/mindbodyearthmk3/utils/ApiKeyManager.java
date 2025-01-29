package com.example.mindbodyearthmk3.utils;

import android.content.Context;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.mindbodyearthmk3.BuildConfig;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ApiKeyManager {
    private static final String ENCRYPTED_API_KEY_PREF = "encrypted_api_key";
    private final Context context;
    private final EncryptedSharedPreferences encryptedPrefs;

    public ApiKeyManager(Context context) throws GeneralSecurityException, IOException {
        this.context = context;

        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        encryptedPrefs = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                context,
                "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public void saveApiKey() {
        String apiKey = BuildConfig.OPENAI_API_KEY;
        encryptedPrefs.edit()
                .putString(ENCRYPTED_API_KEY_PREF, apiKey)
                .apply();
    }

    public String getApiKey() {
        return encryptedPrefs.getString(ENCRYPTED_API_KEY_PREF, null);
    }
}
