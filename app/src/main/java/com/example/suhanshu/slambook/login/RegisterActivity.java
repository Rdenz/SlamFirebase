package com.example.suhanshu.slambook.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.suhanshu.slambook.FirstActivity;
import com.example.suhanshu.slambook.model.Common;
import com.example.suhanshu.slambook.R;
import com.example.suhanshu.slambook.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegisterActivity extends AppCompatActivity {
    private MaterialEditText editNewUser;
    private MaterialEditText editNewPassword;
    private MaterialEditText editNewEmail;
    private MaterialEditText editNewRePassword;
    private DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editNewEmail = findViewById(R.id.newemail);
        editNewUser = findViewById(R.id.newUsername);
        editNewPassword = findViewById(R.id.newpassword);
        editNewRePassword = findViewById(R.id.newrepassword);
        users = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void signUp(View view) {
        final String userName = editNewUser.getText().toString();
        final String password = editNewPassword.getText().toString();
        final String email = editNewEmail.getText().toString();
        String rePassword = editNewRePassword.getText().toString();

        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword) || TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(rePassword)) {
            Toast.makeText(RegisterActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;
        }
        final User user = new User(editNewUser.getText().toString()
                , editNewPassword.getText().toString()
                , editNewEmail.getText().toString());

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUserName()).exists()) {
                    Toast.makeText(RegisterActivity.this, "UserName Not Available", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(RegisterActivity.this, "You're registered Successfully", Toast.LENGTH_LONG).show();
                                    users.child(user.getUserName())
                                            .setValue(user);
                                    Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                                    Common.currentUser=user;
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void signIn(View view) {
        Intent intent = new Intent(this, Signin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                //set title
                .setTitle(R.string.exit)
                //set message
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        RegisterActivity.super.onBackPressed();
                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
