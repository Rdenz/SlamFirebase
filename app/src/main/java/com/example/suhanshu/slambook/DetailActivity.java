package com.example.suhanshu.slambook;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.suhanshu.slambook.model.Common;
import com.example.suhanshu.slambook.model.SlamModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        if (getIntent().getExtras().getString("viewUser") != null) {
            String value = getIntent().getExtras().getString("viewUser");
            yourName.setText(value);
            FirebaseDatabase.getInstance().getReference()
                    .child(Common.currentUser.getUserName())
                    .child(value)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            SlamModel slamModel = dataSnapshot.getValue(SlamModel.class);
                            nickNameSuitsPersonality.setText(slamModel.getSnickNameSuitsPersonality());
                            yourName.setText(slamModel.getSyourName());
                            myNameInPhone.setText(slamModel.getSmyNameInPhone());
                            wordsAboutMe.setText(slamModel.getSwordsAboutMe());
                            gotAChance.setText(slamModel.getSgotAChance());
                            bravestThing.setText(slamModel.getSbravestThing());
                            describeOneWord.setText(slamModel.getSdescribeOneWord());
                            describeLove.setText(slamModel.getSdescribeLove());
                            howYoufeel.setText(slamModel.getShowYoufeel());
                            reactioinFirstLook.setText(slamModel.getSreactioinFirstLook());
                            relationBetweenUandMe.setText(slamModel.getSrelationBetweenUandMe());
                            threeWishes.setText(slamModel.getSthreeWishes());
                            likeTocallMe.setText(slamModel.getSlikeTocallMe());
                            oneThingUlove.setText(slamModel.getSoneThingUlove());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }
}
