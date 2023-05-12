package com.example.suhanshu.slambook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class holder extends RecyclerView.ViewHolder {

    OnclickListener onclickListener;

    public void setOnClicklistener(OnclickListener onClicklistener){
        this.onclickListener=onClicklistener;
    }

    TextView textView;
    public holder(View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.text_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickListener.onClick();
            }
        });
    }
}
