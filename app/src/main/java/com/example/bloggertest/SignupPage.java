package com.example.bloggertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupPage extends AppCompatActivity {
    private LoadingDialog loadingDialog;
    private FirebaseAuth author;

    EditText entername, enterpasssignup, enterpass2signup, enteremailsignup, enterphone;
    ProgressBar progressBar2;

    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        author=FirebaseAuth.getInstance();
        entername=findViewById(R.id.entername);
        enterpasssignup=findViewById(R.id.enterpasswordsignup);
        enterpass2signup=findViewById(R.id.enterpasword2signup);
        enteremailsignup=findViewById(R.id.enteremailsignup);
        enterphone=findViewById(R.id.enterphonenumber);

        progressBar2=findViewById(R.id.progressBar3);

        progressBar2.setVisibility(View.INVISIBLE);

        signup=findViewById(R.id.signupbutton);


        Intent signupcomplete= new Intent(SignupPage.this, homescreen.class);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar2.setVisibility(View.VISIBLE);
                signup.setVisibility(View.INVISIBLE);

                if
                (entername.getText().toString().trim().isEmpty() ||
                        enterpasssignup.getText().toString().isEmpty()||
                        enterpass2signup.getText().toString().trim().isEmpty()||
                        enteremailsignup.getText().toString().trim().isEmpty()||
                        enterphone.getText().toString().trim().isEmpty()

                ){

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupPage.this);

                    builder.setTitle("Not Entered");

                    builder.setMessage("Please Enter Your Information In Above Section ");
                    builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            progressBar2.setVisibility(View.INVISIBLE);
                            signup.setVisibility(View.VISIBLE);

                        }
                    });
                    builder.setCancelable(false);
                    builder.show();



                }
                else{

                    if(enterpasssignup.getText().toString().trim().equals(enterpass2signup.getText().toString().trim())){



                            String Email=enteremailsignup.getText().toString().trim();
                            String Password=enterpass2signup.getText().toString().trim();
                            String name=entername.getText().toString().trim();
                            String phonenumber=enterphone.getText().toString().trim();

                            author.createUserWithEmailAndPassword(Email , Password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if(task.isSuccessful()){

                                                showLoadingAnimation();

                                                new Handler().postDelayed(() -> {

                                                startActivity(signupcomplete);
                                                Toast.makeText(SignupPage.this, "SignUp successfull", Toast.LENGTH_SHORT).show();
                                                finish();

//                                            ed.putString("email",Email);
//                                            ed.putString("phonenumber",phonenumber);
//                                            ed.putString("name",name);
//                                            ed.putString("loginstatus", "1");
//                                            ed.apply();
//
//                                            Map<String, Object> userMap = new HashMap<>();
//                                            userMap.put("email", Email);
//                                            userMap.put("name", name);
//                                            userMap.put("phone", phonenumber);
//                                            userMap.put("img","");

//                                            db.collection("Users")
//                                                    .document(Email)
//                                                    .set(userMap)
//                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            if (task.isSuccessful()) {
//
//                                                                startActivity(signupcomplete);
//                                                                Toast.makeText(SignupPage.this, "SignUp succesfull", Toast.LENGTH_SHORT).show();
//
//                                                                finish();
//
//                                                            } else {
//                                                                Toast.makeText(SignupPage.this,
//                                                                        "Not Uploaded to database" , Toast.LENGTH_SHORT).show();
//
//                                                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupPage.this);
//                                                                builder.setTitle("Password  Mismatch");
//                                                                builder.setMessage("Password You entered is Not matching  ");
//                                                                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
//                                                                    @Override
//                                                                    public void onClick(DialogInterface dialog, int which) {
//                                                                        dialog.dismiss();
//                                                                        progressBar2.setVisibility(View.INVISIBLE);
//                                                                        signup.setVisibility(View.VISIBLE);
//                                                                    }
//                                                                });
//                                                                builder.setCancelable(false);
//                                                                builder.show();
//                                                            }
//                                                        }
//                                                    });

                                                hideLoadingAnimation();
                                            }, 5000);

                                            }
                                            else{

                                                String errormsg=task.getException().getLocalizedMessage();
                                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupPage.this);
                                                builder.setTitle("Error");
                                                builder.setMessage(errormsg);
                                                builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        progressBar2.setVisibility(View.INVISIBLE);
                                                        signup.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                                builder.setCancelable(false);
                                                builder.show();
                                            }

                                        }
                                    });



                    }
                    else{


                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupPage.this);
                        builder.setTitle("Password  Mismatch");
                        builder.setMessage("Password You entered is Not matching  ");
                        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                progressBar2.setVisibility(View.INVISIBLE);
                                signup.setVisibility(View.VISIBLE);
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();


                    }
                }


            }
        });

    }


private void showLoadingAnimation() {
    if (loadingDialog == null) {
        loadingDialog = new LoadingDialog(this);
    }
    loadingDialog.show();
}

    private void hideLoadingAnimation() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}