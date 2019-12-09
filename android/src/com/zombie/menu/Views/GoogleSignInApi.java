package com.zombie.menu.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.zombie.menu.R;

public class GoogleSignInApi extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;

    private SignInButton signIn;
    private Button signOut;
    private TextView personEmail;
    private TextView personName;
    private TextView personId;
    private ImageView personPhoto;
    private FullScreen fullScreen = new FullScreen();
    private int RC_SIGN_IN = 0;
    private GoogleSignInOptions gso;
    private static boolean loggedIn = false;
    private static String stringPersonEmail;
    private static String stringPersonName;
    private static String stringPersonId;
    private static Uri photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in_api);

        initialize();

        if(loggedIn == true){
            updateUI(true);
        }

        this.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(false);
                    }
                });

    }

    private void initialize() {
        this.signIn = findViewById(R.id.sign_in_button);
        this.personEmail = findViewById(R.id.text_view_email);
        this.personName = findViewById(R.id.text_view_name);
        this.personId = findViewById(R.id.text_view_id);
        this.personPhoto = findViewById(R.id.image_view_photo);
        this.signOut = findViewById(R.id.btnSignOut);
    }

    @Override
    public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
        super.onActivityResult(_requestCode, _resultCode, _data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (_requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(_data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> _completedTask) {
        try {
            GoogleSignInAccount account = _completedTask.getResult(ApiException.class);


            // Signed in successfully, show authenticated UI.
            //Retrieve data from user's google account
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                this.loggedIn =true;
                stringPersonEmail = acct.getEmail();
                stringPersonName = acct.getDisplayName();
                stringPersonId = acct.getId();
                photo = acct.getPhotoUrl();

                updateUI(true);
            }else{
                updateUI(false);
            }


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void updateUI(boolean _logIn) {
        if(_logIn){
            this.personEmail.setText(stringPersonEmail);
            this.personName.setText(stringPersonName);
            this.personId.setText(stringPersonId);
            Glide.with(this)
                    .load(String.valueOf(personPhoto))
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(this.personPhoto);
            this.personEmail.setVisibility(View.VISIBLE);
            this.personName.setVisibility(View.VISIBLE);
            this.personId.setVisibility(View.VISIBLE);
            this.personPhoto.setVisibility(View.VISIBLE);
            this.signOut.setVisibility(View.VISIBLE);
            this.signIn.setVisibility(View.INVISIBLE);
        }else{
            this.personEmail.setVisibility(View.INVISIBLE);
            this.personName.setVisibility(View.INVISIBLE);
            this.personId.setVisibility(View.INVISIBLE);
            this.personPhoto.setVisibility(View.INVISIBLE);
            this.signOut.setVisibility(View.INVISIBLE);
            this.signIn.setVisibility(View.VISIBLE);
        }
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        this.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
