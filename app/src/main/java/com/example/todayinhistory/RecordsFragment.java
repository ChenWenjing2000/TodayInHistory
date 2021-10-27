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
import android.widget.TextView;

public class RecordsFragment extends Fragment implements AdapterView.OnItemClickListener{

    ListView recordslist;
    ProgressBar progressBar;
    private AddViewModel mViewModel;
    TextView nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.records_fragment, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recordslist = view.findViewById(R.id.recordslist);
        nodata = view.findViewById(R.id.no_data);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        DBManager dbManager = new DBManager(getContext());
        MyAdapter adapter = new MyAdapter(getContext(), android.R.layout.simple_list_item_1,dbManager.listrAll());
        recordslist.setAdapter(adapter);
        recordslist.setEmptyView(nodata);
        progressBar.setVisibility(View.GONE);
        recordslist.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = recordslist.getItemAtPosition(position);
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