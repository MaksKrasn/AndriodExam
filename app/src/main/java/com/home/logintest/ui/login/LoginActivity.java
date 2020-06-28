package com.home.logintest.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.home.logintest.MainActivity;
import com.home.logintest.R;
import com.home.logintest.data.model.LoggedInUser;
import com.home.logintest.data.model.Photo;
import com.home.logintest.database.PeopleHandler;
import com.home.logintest.database.PhotoHandler;
import com.home.logintest.ui.login.LoginViewModel;
import com.home.logintest.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dbIni();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                // Завершите и уничтожьте вход в систему после успешного завершения
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), LoginActivity.this);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(), LoginActivity.this);
            }
        });
    }
// метод выполняется при успехе авторизации
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        // переход на основную логику
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void dbIni(){
        PeopleHandler peopleHandler = new PeopleHandler( this );
        PhotoHandler photoHandler = new PhotoHandler(this);
        peopleHandler.create( new LoggedInUser( 0, "user1@gmail.com", "123456" ) );
        photoHandler.create(new Photo(0, "photo1", "https://lh3.googleusercontent.com/proxy/G7iyt-QEhXrtwyRdKiRqxpP5HYMGhG2XT9sz4U8ByBhM-KunNwmwLNEc6u_XH10FSuut8W-HahW7-0y_MyfSF1YZNDXELQ", 1));
        photoHandler.create(new Photo(0, "photo2", "https://bipbap.ru/wp-content/uploads/2019/07/11-5-1-640x853.jpg", 1));
        photoHandler.create(new Photo(0, "photo3", "https://avatars.mds.yandex.net/get-zen_doc/1549204/pub_5cb076f20c706d00b3467e27_5cb0770ca4ee5b00b3d138a8/scale_1200", 2));
        photoHandler.create(new Photo(0, "photo4", "https://24smi.org/public/media/news/2015/02/02/1422896280_1.jpg", 2));
        photoHandler.create(new Photo(0, "photo5", "https://s0.rbk.ru/v6_top_pics/resized/1180xH/media/img/0/20/755913140286200.jpg", 3));
    }
}