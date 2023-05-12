package com.example.suhanshu.slambook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suhanshu.slambook.model.Common;
import com.example.suhanshu.slambook.model.SlamModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class View_Slam extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter<SlamModel, holder> adapter;
    TextView emptyView;
    EditText editText;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__slam);
        recyclerView = findViewById(R.id.recycler_view);
        editText = findViewById(R.id.edit_text);
        SimplerDividerItemDecoration decoration = new SimplerDividerItemDecoration(getApplicationContext());
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        emptyView = findViewById(R.id.empty_view);
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Common.currentUser.getUserName());
        query(databaseReference);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Query databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child(Common.currentUser.getUserName())
                        .orderByChild("syourName")
                        .startAt(String.valueOf(s))
                        .endAt(s + "\uf8ff");
                query(databaseReference);
            }
        });
    }

    public void query(final Query databaseReference) {
        FirebaseDatabase.getInstance().getReference().child(Common.currentUser.getUserName())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            adapter = new FirebaseRecyclerAdapter<SlamModel, holder>(
                                    SlamModel.class,
                                    R.layout.recycler_view_slam,
                                    holder.class,
                                    databaseReference
                            ) {
                                @Override
                                protected void populateViewHolder(holder viewHolder, final SlamModel model, int position) {
                                    viewHolder.textView.setText(model.getSyourName());
                                    viewHolder.setOnClicklistener(new OnclickListener() {
                                        @Override
                                        public void onClick() {
                                            Intent scoreDetail = new Intent(getApplicationContext(), DetailActivity.class);
                                            scoreDetail.putExtra("viewUser", model.getSyourName());
                                            scoreDetail.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(scoreDetail);
                                        }
                                    });
                                }
                            };
                            recyclerView.setAdapter(adapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.INVISIBLE);

                        } else {
                            recyclerView.setVisibility(View.INVISIBLE);
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
