package com.example.gaojichonci01_3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gaojichonci01_3.R;
import com.example.gaojichonci01_3.adapter.RvAdapter;
import com.example.gaojichonci01_3.bean.DatasBean;
import com.example.gaojichonci01_3.bean.RvBean;
import com.example.gaojichonci01_3.service.ApiService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import dbutils.MyApp;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnRefreshLoadMoreListener {

    private RecyclerView rv;
    private SmartRefreshLayout sml;
    private RvAdapter rvAdapter;
        private int sum = 0;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
            initView(view);
            initData();
        return view;
    }

    private void initData() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.mUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final ApiService apiService = retrofit.create(ApiService.class);
        final Observable<RvBean> rvbean = apiService.rvBean();
        rvbean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RvBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RvBean rvBean) {
                        final List<DatasBean> datas = rvBean.getData().getDatas();
                        rvAdapter.setList(datas);
                        sml.finishRefresh();
                        sml.finishLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
        sml = view.findViewById(R.id.sml);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter = new RvAdapter(getActivity());
        rv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rv.setAdapter(rvAdapter);
        rvAdapter.setOnClickSelect(new RvAdapter.onClickSelect() {
            @Override
            public void onClick(int position) {
                final DatasBean datasBean = rvAdapter.list.get(position);
                final long insert = MyApp.daoSession.insert(datasBean);
                if (insert>=0){
                    Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sml.setOnRefreshLoadMoreListener(this);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        sum++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        int sum = 0;
        if (rvAdapter.list!=null&&rvAdapter.list.size()>0){
            rvAdapter.list.clear();
            initData();
        }
    }
}
