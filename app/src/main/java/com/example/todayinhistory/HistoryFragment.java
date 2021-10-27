package com.example.todayinhistory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryFragment extends Fragment implements View.OnClickListener , DatePickerDialog.OnDateSetListener , AdapterView.OnItemClickListener{

    private String TAG = "HistoryFragment";

    Button today;
    Button prior;
    Button next;
    Calendar calendar;
    String desc;
    Date max;
    Date min;
    SimpleDateFormat simpleDateFormat;
    Handler handler;
    int day;
    SharedPreferences sp;
    ArrayList<Item> listItem;
    MyAdapter adapter;
    ListView listEvent;
    ProgressBar progressBar;
    private AddViewModel mViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main5, container, false);

        today = view.findViewById(R.id.today);
        listEvent = view.findViewById(R.id.listEvent);
        progressBar = view.findViewById(R.id.progressBar);
        prior = view.findViewById(R.id.prior);
        next = view.findViewById(R.id.next);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        calendar = Calendar.getInstance();
        calendar.set(java.util.Calendar.YEAR, 2020);
        day = calendar.get(Calendar.DAY_OF_YEAR);
        sp = getContext().getSharedPreferences("mydate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("day",day);
        editor.commit();
        Log.i(TAG, "onCreate: day"+day);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            max = simpleDateFormat.parse("2020-12-31");
            min = simpleDateFormat.parse("2020-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        desc = String.format("%d月%d日", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        today.setText(desc);

        today.setOnClickListener(this);
        prior.setOnClickListener(this);
        next.setOnClickListener(this);
        listEvent.setOnItemClickListener(this);

        extracted();
    }


    private synchronized void extracted() {
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 1) {
                    listItem = (ArrayList<Item>) msg.obj;
                    adapter = new MyAdapter(getContext(), android.R.layout.simple_list_item_1,listItem);
                    listEvent.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                }
                super.handleMessage(msg);
            }
        };

        Test test = new Test(sp);
        test.setHandler(handler);
        new Thread(test).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.today) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            DatePicker datepicker = dialog.getDatePicker();
            datepicker.setMinDate(min.getTime());   // 设置日期的下限日期
            datepicker.setMaxDate(max.getTime());   // 设置日期的上限日期
            dialog.show();

            DatePicker dp = findDatePicker((ViewGroup) dialog.getWindow().getDecorView());
            if (dp != null) {
                ((ViewGroup) (((ViewGroup) dp.getChildAt(0)).getChildAt(0))).getChildAt(2).setVisibility(View.GONE);
            }
        } else if (view.getId() == R.id.prior) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            day = calendar.get(Calendar.DAY_OF_YEAR);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("day",day);
            editor.commit();
            Log.i(TAG, "onClick: day"+day);
            extracted();
            desc = String.format("%d月%d日", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            today.setText(desc);
        } else if (view.getId() == R.id.next) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            day = calendar.get(Calendar.DAY_OF_YEAR);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("day",day);
            editor.commit();
            Log.i(TAG, "onClick: day"+day);
            extracted();
            desc = String.format("%d月%d日", calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            today.setText(desc);
        }
    }

    //DatePickerDialog内部结构
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    //点击日期对话框上的确定按钮，触发监听器的onDateSet方法
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        //获取日期对话框设定的年月份
        desc = String.format("%d月%d日", i1 + 1, i2);
        today.setText(desc);
        calendar.set(java.util.Calendar.MONTH, i1);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, i2);
        day = calendar.get(Calendar.DAY_OF_YEAR);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("day",day);
        editor.commit();
        Log.i(TAG, "onClick: day"+day);
        extracted();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = listEvent.getItemAtPosition(position);
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