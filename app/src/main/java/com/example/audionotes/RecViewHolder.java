package com.example.audionotes;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public LinearLayout linearLayout;

    public RecViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textName);
        linearLayout = itemView.findViewById((R.id.recycler_savings));


    }
}
