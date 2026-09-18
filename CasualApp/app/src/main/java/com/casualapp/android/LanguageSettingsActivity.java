package com.casualapp.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.casualapp.android.config.LanguageManager;

public class LanguageSettingsActivity extends AppCompatActivity {

    private RadioGroup languageGroup;
    private AppCompatButton btnSave;
    private AppCompatButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_settings);

        languageGroup = findViewById(R.id.languageGroup);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        String currentLanguage = LanguageManager.getSelectedLanguage(this);
        if ("zh".equals(currentLanguage)) {
            languageGroup.check(R.id.radioChinese);
        } else {
            languageGroup.check(R.id.radioEnglish);
        }

        btnSave.setOnClickListener(v -> saveLanguage());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveLanguage() {
        int selectedId = languageGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radioChinese) {
            LanguageManager.setLanguage(this, "zh");
        } else {
            LanguageManager.setLanguage(this, "en");
        }

        Toast.makeText(this, getString(R.string.language_changed), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
