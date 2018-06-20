package dev.ivandyagilev.bittestapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.ivandyagilev.bittestapp.R;



public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity_TAG";
    @BindView(R.id.loginSpinner)
    ImageView spinner;

    @OnClick(R.id.cancel_button)
    public void skipLogin() {
        goToMain();
    }

    @BindView(R.id.login_button)
    LoginButton loginButton;

    private CallbackManager callbackManager;
    private RotateAnimation rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(getString(R.string.email));

        loginButton.setOnClickListener(v -> animateView(spinner));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                rotate.cancel();
                goToMain();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
                Log.e(TAG, getString(R.string.autorization_error) + exception.toString());
            }
        });

        if (isLoggedIn()){
            goToMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void animateView(View view){
        rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        view.startAnimation(rotate);
    }

    private boolean isLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }
}

