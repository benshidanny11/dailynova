package com.example.dailynova;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dailynova.items.UserItem;
import com.example.dailynova.utils.UserKeyUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {
   MaterialButton btnSignIn;

    private static final int CALL_PERMISSION_REQUEST_CODE = 102;
    //-----for firebase authentication--------------
    private FirebaseAuth mAuth;
    private CollectionReference mDb;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //---------------------------------------------

    //-----signIn declaration------------
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG="Login Activity";
    private String mUserEmail;
    private String mUserIdentifier;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnSignIn=(MaterialButton)findViewById(R.id.btn_sign_to_daily_nova);

        checkIfUserExists();
        mDb= FirebaseFirestore.getInstance().collection("users");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mAuth=FirebaseAuth.getInstance();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(WelcomeActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(final FirebaseUser currentUser) {

        if (currentUser!=null)
        {
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            final String user_token= FirebaseInstanceId.getInstance().getToken();
            DocumentReference documentReference= mDb.document(UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail()));

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        Map<String,Object> tokenMap=new HashMap<>();
                        tokenMap.put("driver_token",user_token);
                        FirebaseFirestore.getInstance().collection("drivers").document(UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail()))
                                .update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                                intent.putExtra("userKey",UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail()));
                                intent.putExtra("displayName",currentUser.getDisplayName());
                                intent.putExtra("imgUrl",currentUser.getPhotoUrl());
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        });

                    }else {
                        UserItem item=new UserItem(currentUser.getDisplayName(),currentUser.getPhotoUrl().toString(),UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail()),user_token);
                        mDb.document(UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail())).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                    progressDialog.dismiss();
                                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                                intent.putExtra("userKey",UserKeyUtil.getUserKeyFromEmail(currentUser.getEmail()));
                                intent.putExtra("displayName",currentUser.getDisplayName());
                                intent.putExtra("imgUrl",currentUser.getPhotoUrl());
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                            }
                        });

                    }
                }
            });

        }
    }
    private void checkIfUserExists(){
        if (FirebaseAuth.getInstance().getCurrentUser()==null){
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }else{
            Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra("userKey",UserKeyUtil.getUserKeyFromEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            intent.putExtra("displayName",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            intent.putExtra("imgUrl",FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
            startActivity(intent);
            finish();
        }
    }
}
