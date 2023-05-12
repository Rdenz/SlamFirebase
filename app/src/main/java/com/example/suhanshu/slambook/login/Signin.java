package com.example.suhanshu.slambook.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.suhanshu.slambook.model.Common;
import com.example.suhanshu.slambook.FirstActivity;
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

public class Signin extends AppCompatActivity {

    private MaterialEditText editUser;
    private MaterialEditText editPassword;
    private Button btnSignUp, btnSignIn;
    private DatabaseReference users;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .orderByChild("email")
                            .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        Common.currentUser = dataSnapshot1.getValue(User.class);
                                        Toast.makeText(Signin.this, "" + Common.currentUser.getUserName(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Signin.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                    Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };

        btnSignIn = findViewById(R.id.sign_in);
        btnSignUp = findViewById(R.id.sign_up);
        editUser = findViewById(R.id.Username);
        editPassword = findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference("Users");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editUser.getText().toString();
                final String pass = editPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Signin.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }
                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(editUser.getText().toString()).exists()) {
                            final User user = dataSnapshot.child(editUser.getText().toString()).getValue(User.class);
                            String email = user.getEmail();
                            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                                    Common.currentUser = user;
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signin.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else
                            Toast.makeText(Signin.this, "Username not exists", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

    @Override
    public void onBackPressed() {
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this)
                //set icon
                //set title
                .setTitle(R.string.exit)
                //set message
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        Signin.super.onBackPressed();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
