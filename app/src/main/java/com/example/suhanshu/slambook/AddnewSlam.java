package com.example.suhanshu.slambook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suhanshu.slambook.model.Common;
import com.example.suhanshu.slambook.model.SlamModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddnewSlam extends AppCompatActivity {

    @BindView(R.id.your_name)
    EditText yourName;
    @BindView(R.id.nickname_suits_my_personality)
    EditText nickNameSuitsPersonality;
    @BindView(R.id.my_name_in_phone)
    EditText myNameInPhone;
    @BindView(R.id.words_about_me)
    EditText wordsAboutMe;
    @BindView(R.id.got_a_chance)
    EditText gotAChance;
    @BindView(R.id.bravest_thing)
    EditText bravestThing;
    @BindView(R.id.describe_one_word)
    EditText describeOneWord;
    @BindView(R.id.describe_love_is)
    EditText describeLove;
    @BindView(R.id.how_you_feel_when_youtalk)
    EditText howYoufeel;
    @BindView(R.id.reaction_first_look)
    EditText reactioinFirstLook;
    @BindView(R.id.relation_between_you_me)
    EditText relationBetweenUandMe;
    @BindView(R.id.three_wishes)
    EditText threeWishes;
    @BindView(R.id.like_to_call_me)
    EditText likeTocallMe;
    @BindView(R.id.one_thing_u_love)
    EditText oneThingUlove;
    String syourName, snickNameSuitsPersonality, smyNameInPhone, swordsAboutMe, sgotAChance, sbravestThing, sdescribeOneWord, sdescribeLove, showYoufeel, sreactioinFirstLook, srelationBetweenUandMe, sthreeWishes, slikeTocallMe, soneThingUlove;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void Add_Slam(View view) {
        count = 0;
        syourName = yourName.getText().toString();
        snickNameSuitsPersonality = nickNameSuitsPersonality.getText().toString();
        smyNameInPhone = myNameInPhone.getText().toString();
        swordsAboutMe = wordsAboutMe.getText().toString();
        sgotAChance = gotAChance.getText().toString();
        sbravestThing = bravestThing.getText().toString();
        sdescribeOneWord = describeOneWord.getText().toString();
        sdescribeLove = describeLove.getText().toString();
        showYoufeel = howYoufeel.getText().toString();
        sreactioinFirstLook = reactioinFirstLook.getText().toString();
        srelationBetweenUandMe = relationBetweenUandMe.getText().toString();
        sthreeWishes = threeWishes.getText().toString();
        slikeTocallMe = likeTocallMe.getText().toString();
        soneThingUlove = oneThingUlove.getText().toString();

        if (TextUtils.isEmpty(syourName)) {
            Toast.makeText(this, "Please fill Your Name field", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.isEmpty(snickNameSuitsPersonality)) {
            count++;
        }
        if (!TextUtils.isEmpty(smyNameInPhone)) {
            count++;
        }
        if (!TextUtils.isEmpty(swordsAboutMe)) {
            count++;
        }
        if (!TextUtils.isEmpty(sgotAChance)) {
            count++;
        }
        if (!TextUtils.isEmpty(sbravestThing)) {
            count++;
        }
        if (!TextUtils.isEmpty(sdescribeOneWord)) {
            count++;
        }
        if (!TextUtils.isEmpty(sdescribeLove)) {
            count++;
        }
        if (!TextUtils.isEmpty(showYoufeel)) {
            count++;
        }
        if (!TextUtils.isEmpty(sreactioinFirstLook)) {
            count++;
        }
        if (!TextUtils.isEmpty(srelationBetweenUandMe)) {
            count++;
        }
        if (!TextUtils.isEmpty(sthreeWishes)) {
            count++;
        }
        if (!TextUtils.isEmpty(slikeTocallMe)) {
            count++;
        }
        if (!TextUtils.isEmpty(soneThingUlove)) {
            count++;
        }
        if (count < 2) {
            Toast.makeText(this, "Please fill atleast 5 boxes", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseDatabase.getInstance().getReference()
                .child(Common.currentUser.getUserName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(syourName).exists()) {
                            Toast.makeText(AddnewSlam.this, "Name already exists", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            SlamModel slamModel = new SlamModel(syourName, snickNameSuitsPersonality, smyNameInPhone, swordsAboutMe, sgotAChance, sbravestThing
                                    , sdescribeOneWord, sdescribeLove, showYoufeel, sreactioinFirstLook, srelationBetweenUandMe, sthreeWishes, slikeTocallMe, soneThingUlove);
                            FirebaseDatabase.getInstance().getReference()
                                    .child(Common.currentUser.getUserName())
                                    .child(syourName)
                                    .setValue(slamModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(AddnewSlam.this, "Your response saved", Toast.LENGTH_SHORT).show();
                                            clear_data();
                                            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddnewSlam.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AddnewSlam.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void clear_view(View view) {
        clear_data();
    }

    public void clear_data() {
        nickNameSuitsPersonality.setText(null);
        yourName.setText(null);
        myNameInPhone.setText(null);
        wordsAboutMe.setText(null);
        gotAChance.setText(null);
        bravestThing.setText(null);
        describeOneWord.setText(null);
        describeLove.setText(null);
        howYoufeel.setText(null);
        reactioinFirstLook.setText(null);
        relationBetweenUandMe.setText(null);
        threeWishes.setText(null);
        likeTocallMe.setText(null);
        oneThingUlove.setText(null);

    }
}
