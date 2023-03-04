package com.example.audionotes.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audionotes.Adapters.SavAdapters;
import com.example.audionotes.OnSelectListener;
import com.example.audionotes.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class SavingsFrag extends Fragment implements OnSelectListener {
    private View view;
    private RecyclerView recyclerView;
    private List<File> fileList;
    private SavAdapters savAdapters;
    private File path;


    //Inflates the fragment's layout and initializes the recycler view.
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_savings, container, false);
        path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Your_Record");
        initRecyclerView();
        return view;
    }


    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_savings);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        fileList = new ArrayList<>();
        fileList.addAll(findFile(path));
        savAdapters = new SavAdapters(getContext(), fileList, this);
        recyclerView.setAdapter(savAdapters);
    }

    // Searches for files with the specified file name extension in the given path.
    private ArrayList<File> findFile(File file){
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles(new FilenameFilter() {
            // Filter by file name
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".amr");
            }
        });
        arrayList.addAll(Arrays.asList(files));
        return arrayList;
    }

    //Starts an activity to view the selected file
    @Override
    public void OnSelected(File file) {
        Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() +
                ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "audio/x-wav");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getContext().startActivity(intent);
    }

    //Refreshes the recycler view when the fragment is visible.
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
            initRecyclerView();
    }
}