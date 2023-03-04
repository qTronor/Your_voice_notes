package com.example.audionotes.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audionotes.OnSelectListener;
import com.example.audionotes.R;
import com.example.audionotes.RecViewHolder;

import java.io.File;
import java.util.List;

public class SavAdapters extends RecyclerView.Adapter<RecViewHolder> {
    private Context context;
    private List<File> fileList;
    private OnSelectListener listener;

    public SavAdapters(Context context, List<File> fileList, OnSelectListener listener) {
        this.context = context;
        this.fileList = fileList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecViewHolder holder, int position) {
        holder.textView.setText(fileList.get(position).getName());
        holder.textView.setSelected(true);
        final int pos = position;
        if (holder.linearLayout != null) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnSelected(fileList.get(pos));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
