package com.example.myapplication9;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;
import com.truecaller.android.sdk.TrueError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Truecaller SDK Initialization
        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl("https://truecaller-callback-server.glitch.me/privacy-policy")
                .termsOfServiceUrl("https://truecaller-callback-server.glitch.me/terms-of-service")
                .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
                .build();

        TruecallerSDK.init(trueScope);

        // Button action
        Button authenticateButton = findViewById(R.id.btnAuthenticate);
        authenticateButton.setOnClickListener(v -> {
            if (TruecallerSDK.getInstance().isUsable()) {
                TruecallerSDK.getInstance().getUserProfile((FragmentActivity) this);
            } else {
                Toast.makeText(MainActivity.this, "Truecaller app not usable!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private final ITrueCallback sdkCallback = new ITrueCallback() {
        @Override
        public void onSuccessProfileShared(TrueProfile trueProfile) {
            Toast.makeText(MainActivity.this, "Welcome " + trueProfile.firstName, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailureProfileShared(TrueError trueError) {
            Toast.makeText(MainActivity.this, "Error: " + trueError.getErrorType(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onVerificationRequired(TrueError trueError) {
            Toast.makeText(MainActivity.this, "Verification required", Toast.LENGTH_LONG).show();
        }
    };
}
