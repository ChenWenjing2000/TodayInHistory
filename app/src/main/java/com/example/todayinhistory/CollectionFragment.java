package com.example.todayinhistory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class CollectionFragment extends Fragment implements AdapterView.OnItemClickListener{

    ListView collectionlist;
    ProgressBar progressBar;
    private AddViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main3, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        collectionlist = view.findViewById(R.id.collectionlist);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddViewModel.class);

        DBManager dbManager = new DBManager(getContext());
        MyAdapter adapter = new MyAdapter(getContext(), android.R.layout.simple_list_item_1,dbManager.listcAll());
        collectionlist.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        collectionlist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = collectionlist.getItemAtPosition(position);
        Item item = (Item) itemAtPosition;
        String text = item.getText();
        String title = item.getTitle();
        String href = item.getHref();

        Intent more = new Intent(getContext(),MainActivity2.class);
        more.putExtra("text",text);
        more.putExtra("title",title);
        more.putExtra("href",href);
        startActivity(more);

    }
}