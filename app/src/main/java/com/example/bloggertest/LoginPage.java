package com.example.bloggertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    EditText enteremail,enterpassword;
    private FirebaseAuth authsignin;
    private LoadingDialog loadingDialog;

    Button login;
    ProgressBar progressBar;
    TextView forgotpassw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        lottieAnimationView=findViewById(R.id.animation_viewlcontsignup);
        lottieAnimationView.setVisibility(View.VISIBLE);
        enteremail=findViewById(R.id.enteremail);
        enterpassword=findViewById(R.id.enterpass);
        login=findViewById(R.id.loginbutton);
        progressBar=findViewById(R.id.progressBar2);
        forgotpassw= findViewById(R.id.forgotpasstext);
        authsignin=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);

                if(enteremail.getText().toString().trim().isEmpty() || enterpassword.getText().toString().trim().isEmpty()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                    builder.setTitle("Not Entered");
                    builder.setMessage("Please Enter Your Information In Above Section ");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            progressBar.setVisibility(View.INVISIBLE);
                            login.setVisibility(View.VISIBLE);

                        }
                    });
                    builder.setCancelable(false);
                    builder.show();

                }
                else {

                    Intent homeactivity= new Intent(LoginPage.this,MainActivity.class);
                    String Email=enteremail.getText().toString().trim();
                    String password=enterpassword.getText().toString().trim();

//                    DocumentReference documentReference=db1.collection("Users").document(Email);


                        authsignin.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(task.isSuccessful()){

                                    showLoadingAnimation();
                                    new Handler().postDelayed(() -> {
                                    startActivity(homeactivity);
                                        hideLoadingAnimation();
                                    finish();

//                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            DocumentSnapshot document = task.getResult();
//                                            if (document.exists()) {
//                                                // Data found, display it
////                                                String name = document.getString("name");
////                                                String phone = document.getString("phone");
////                                                String img=document.getString("img");
////
////                                                ed.putString("email",Email);
////                                                ed.putString("phonenumber",phone);
////                                                ed.putString("name",name);
////                                                ed.putString("img",img);
////                                                ed.putString("loginstatus", "1");
////                                                ed.apply();
//                                                startActivity(homeactivity);
//                                                finish();
//
//
//                                            } else {
//                                                // No such document
//                                            }
//                                        } else {
//                                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
//
//                                            builder.setTitle("Sign In Failure");
//
//                                            builder.setMessage("Retry, Again later");
//                                            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                    dialog.dismiss();
//
//                                                    progressBar.setVisibility(View.INVISIBLE);
//                                                    login.setVisibility(View.VISIBLE);
//
//                                                }
//                                            });
//                                            builder.setCancelable(false);
//                                            builder.show();
//                                            // Handle errors
//
//                                        }
//                                    }
//                                });
                                }, 6000);

                                }
                                else{
                                    String error=task.getException().getLocalizedMessage();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(error);
                                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            login.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    builder.setCancelable(false);
                                    builder.show();
                                }

                            }
                        });




                }
            }
        });

    }

    private void hideLoadingAnimation() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void showLoadingAnimation() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }
}

//    private LoadingDialog loadingDialog;

//
//    showLoadingAnimation();
//
//// Simulate some background task
//        new Handler().postDelayed(() -> {
//                // Example: Hide loading animation after a delay
//                hideLoadingAnimation();
//                }, 3000);
//private void showLoadingAnimation() {
//    if (loadingDialog == null) {
//        loadingDialog = new LoadingDialog(this);
//    }
//    loadingDialog.show();
//}
//
//    private void hideLoadingAnimation() {
//        if (loadingDialog != null && loadingDialog.isShowing()) {
//            loadingDialog.dismiss();
//        }
//    }