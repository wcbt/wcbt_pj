package com.casualapp.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.casualapp.android.config.LanguageManager;
import com.casualapp.android.model.LoginRequest;
import com.casualapp.android.model.User;
import com.casualapp.android.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private TextView btnWorker;
    private TextView btnEmployer;
    private EditText etPhone;
    private EditText etPassword;
    private AppCompatButton btnLogin;

    private boolean isWorker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnWorker = findViewById(R.id.btnWorker);
        btnEmployer = findViewById(R.id.btnEmployer);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnWorker.setOnClickListener(v -> setRole(true));
        btnEmployer.setOnClickListener(v -> setRole(false));

        btnLogin.setOnClickListener(v -> attemptLogin());

        findViewById(R.id.tvForgotPassword).setOnClickListener(v ->
                Toast.makeText(
                        this,
                        getString(R.string.password_recovery_not_implemented),
                        Toast.LENGTH_SHORT
                ).show()
        );

        findViewById(R.id.tvAppTitle).setOnLongClickListener(v -> {
            Intent settingsIntent = new Intent(LoginActivity.this, LanguageSettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        });

        setRole(true);
    }

    private void setRole(boolean worker) {
        isWorker = worker;

        if (worker) {
            btnWorker.setBackground(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.bg_segment_selected
                    )
            );
            btnWorker.setTextColor(getColor(R.color.primary));
            btnWorker.setTypeface(null, Typeface.BOLD);
            btnWorker.setElevation(4f);

            btnEmployer.setBackground(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.bg_segment_unselected
                    )
            );
            btnEmployer.setTextColor(
                    getColor(R.color.on_surface_variant)
            );
            btnEmployer.setTypeface(null, Typeface.NORMAL);
            btnEmployer.setElevation(0f);

        } else {
            btnEmployer.setBackground(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.bg_segment_selected
                    )
            );
            btnEmployer.setTextColor(getColor(R.color.primary));
            btnEmployer.setTypeface(null, Typeface.BOLD);
            btnEmployer.setElevation(4f);

            btnWorker.setBackground(
                    ContextCompat.getDrawable(
                            this,
                            R.drawable.bg_segment_unselected
                    )
            );
            btnWorker.setTextColor(
                    getColor(R.color.on_surface_variant)
            );
            btnWorker.setTypeface(null, Typeface.NORMAL);
            btnWorker.setElevation(0f);
        }
    }

    private void attemptLogin() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.phone_required));
            etPhone.requestFocus();
            return;
        }

        setLoginLoading(true);

        LoginRequest request = new LoginRequest(phone, password);

        RetrofitClient.getApiService()
                .login(request)
                .enqueue(new Callback<>() {

                    @Override
                    public void onResponse(
                            Call<User> call,
                            Response<User> response
                    ) {
                        setLoginLoading(false);

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            showLoginError(response);
                            return;
                        }

                        User user = response.body();

                        String selectedRole = isWorker
                                ? "WORKER"
                                : "COORDINATOR";

                        String actualRole = user.getRole() == null
                                ? ""
                                : user.getRole().name();

                        if (!selectedRole.equals(actualRole)) {
                            UserSession.clear();

                            Toast.makeText(
                                    LoginActivity.this,
                                    getString(R.string.role_mismatch, actualRole),
                                    Toast.LENGTH_LONG
                            ).show();

                            return;
                        }

                        // Save the user only after successful role validation.
                        UserSession.setCurrentUser(user);

                        Toast.makeText(
                                LoginActivity.this,
                                getString(R.string.login_success, user.getName()),
                                Toast.LENGTH_SHORT
                        ).show();

                        Intent destination;

                        if (user.isCoordinator()) {
                            destination = new Intent(
                                    LoginActivity.this,
                                    CoordinatorHomeActivity.class
                            );
                        } else {
                            destination = new Intent(
                                    LoginActivity.this,
                                    RegionSelectionActivity.class
                            );
                        }

                        startActivity(destination);
                        finish();
                    }

                    @Override
                    public void onFailure(
                            Call<User> call,
                            Throwable throwable
                    ) {
                        setLoginLoading(false);

                        Toast.makeText(
                                LoginActivity.this,
                                getString(R.string.network_error, throwable.getMessage()),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

    private void setLoginLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        btnLogin.setText(loading ? getString(R.string.sign_in_loading) : getString(R.string.sign_in));
    }

    private void showLoginError(Response<User> response) {
        try {
            String error = response.errorBody() != null
                    ? response.errorBody().string()
                    : "Login failed";

            Toast.makeText(
                    this,
                    error,
                    Toast.LENGTH_LONG
            ).show();

        } catch (Exception exception) {
            Toast.makeText(
                    this,
                    "Login error: " + response.code(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}