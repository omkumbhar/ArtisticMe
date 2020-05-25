package com.code_crawler.artisticme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.code_crawler.artisticme.Activity.HomeActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerAuthActivity extends AppCompatActivity {

    private static final String TAG =  FingerAuthActivity.class.getName();
    BiometricPrompt.PromptInfo promptInfo= null;
    Intent intent ;
    BiometricPrompt myBiometricPrompt;
    FragmentActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_auth);

        Executor newExecutor = Executors.newSingleThreadExecutor();

        intent = new Intent(this, HomeActivity.class);
        myBiometricPrompt = new BiometricPrompt(activity, newExecutor
                , new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if( myBiometricPrompt.ERROR_NEGATIVE_BUTTON == errorCode) finish();

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

            }
        });






         promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("ArtisticMe")
                .setSubtitle("Authenticate yourself")
                .setNegativeButtonText("Cancel")
                .build();





    }


    @Override
    protected void onResume() {
        super.onResume();
        myBiometricPrompt.authenticate(promptInfo);

    }



}
