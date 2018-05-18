package com.clean.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.clean.R;
import com.clean.home.fragment.HomeListFragment;
import com.open.appbase.activity.BasePermissionActivity;

import butterknife.BindArray;
import butterknife.ButterKnife;

/**
 * HomeActivity
 * IApkDisplayer as V in MVP
 */
public class HomeActivity extends BasePermissionActivity {

    @BindArray(R.array.permission)
    String[] permissionStrs;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, HomeListFragment.newInstance())
                    .commitAllowingStateLoss();
        }

        ButterKnife.bind(this);
    }

    @Override
    protected void loadData() {
        setPermissionAlterDialog(permissionStrs);
    }

    /**
     * Gets calling intent.
     *
     * @param context the context
     * @return the calling intent
     */
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected String[] getPermissions() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    protected void permissionGranted() {

    }

    @Override
    protected void permissionDeny(String[] notGranted) {
        Toast.makeText(HomeActivity.this, "Photo will not work", Toast.LENGTH_SHORT).show();
    }
}
