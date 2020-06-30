package com.example.gaojichonci01_3.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gaojichonci01_3.R;
import com.example.gaojichonci01_3.adapter.Rv2Adapter;
import com.example.gaojichonci01_3.bean.DatasBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import dbutils.DbUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements OnRefreshLoadMoreListener, Rv2Adapter.onLongClickSelect {

    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.sml2)
    SmartRefreshLayout sml2;
    private Rv2Adapter rv2Adapter;
    private int sum = 0;

    private boolean isViewCreated;
    private boolean isViisbleToUser;
    private boolean isDataLoaded;
    private DatasBean datasBean2;
    private TextView textView;
    private EditText ev;
    private Button btn1;
    private Button btn2;
    private int pos;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        final List<DatasBean> datasBeans = DbUtils.queryAll();

        rv2Adapter.setList(datasBeans);
        rv2Adapter.notifyDataSetChanged();

    }

    private void initView(View view) {
        rv2 = view.findViewById(R.id.rv2);
        sml2 = view.findViewById(R.id.sml2);
        rv2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv2Adapter = new Rv2Adapter(getActivity());
        setHasOptionsMenu(true);
        registerForContextMenu(rv2);
        rv2.setAdapter(rv2Adapter);
        rv2Adapter.setOnLongClickSelect(this);
        rv2Adapter.setOnClickSelect(new Rv2Adapter.onClickSelect() {
            @Override
            public void onClick(int position) {
                final DatasBean datasBean = rv2Adapter.list.get(position);
                DbUtils.delete(datasBean);
                rv2Adapter.setDelete(datasBean);

            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ( isVisibleToUser) {
            initData();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    //  长按
    @Override
    public void onLongClickSelect(int position) {
        pos = position;
        datasBean2 = rv2Adapter.list.get(position);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 1, 0, "删除");
        menu.add(0, 2, 0, "修改");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                DbUtils.delete(datasBean2);
                rv2Adapter.setDelete(datasBean2);
                break;

            case 2:


                final View view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_popw, null);
                final PopupWindow popupWindow = new PopupWindow(view,
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textView = view.findViewById(R.id.textView);
                ev = view.findViewById(R.id.ev);
                btn1 = view.findViewById(R.id.btn1);
                btn2 = view.findViewById(R.id.btn2);
                textView.setText(rv2Adapter.list.get(pos).getTitle());
                ev.setText(datasBean2.getTitle());
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        final String ev2 = ev.getText().toString();
                        rv2Adapter.list.get(pos).setTitle(ev2);
                        rv2Adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }
}
