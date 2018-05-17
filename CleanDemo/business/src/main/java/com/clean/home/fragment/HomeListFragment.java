package com.clean.home.fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.clean.R;
import com.clean.businesscommon.router.UIRouter;
import com.open.appbase.adapter.recyclerview.RecyclerItemClickListener;
import com.open.appbase.adapter.recyclerview.RecyclerViewArrayAdapter;
import com.open.appbase.fragment.BaseLazyFragment;
import com.orhanobut.logger.Logger;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.clean.businesscommon.common.Const.TAG_APP;

/**
 * Home List Fragment
 */
public class HomeListFragment extends BaseLazyFragment {
    private Reference<FragmentActivity> mActivityRef;

    private Unbinder unbinder;

    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(android.R.id.title)
    TextView resultTV;

    @BindArray(R.array.home)
    String[] homeList;


    public static HomeListFragment newInstance() {
        return new HomeListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_list;
    }

    @Override
    protected void initViews(View view) {
        if (getActivity() == null) {
            return;
        }

        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivityRef.get()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(
                mActivityRef.get(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        mActivityRef = new WeakReference<>(getActivity());
    }

    @Override
    protected void loadData() {
        recyclerView.setAdapter(new RecyclerViewArrayAdapter(mActivityRef.get(), homeList));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mActivityRef.get(),
                recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UIRouter.gotoFragment(mActivityRef.get(), position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        safeDestroy();
    }

    private void safeDestroy() {
        try {
            unbinder.unbind();
        } catch (Exception ex) {
            Logger.w("safeDestroy ex", ex);
        }
    }

}
