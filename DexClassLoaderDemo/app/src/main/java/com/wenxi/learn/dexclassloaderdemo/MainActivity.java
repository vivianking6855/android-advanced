package com.wenxi.learn.dexclassloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.open.utislib.file.PathUtils;
import com.wenxi.learn.plugin.IChange;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DexDemo";

    private TextView mResult;
    private Button mBtnLoad;

    private final static String PLUGIN_NAME = "demo.jar";
    private final static String PLUGIN_CLASS = "com.wenxi.learn.demoplugin.DataChange";
    private Class<?> mDexClazz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResult = (TextView) findViewById(R.id.tv_result);
        mBtnLoad = (Button) findViewById(R.id.btn_load);
        mBtnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPlugin();
            }
        });

        loadPlugin();
    }

    private void loadPlugin() {
        // get plugin aar that include class.dex
        final File plugin =
                new File(PathUtils.getDiskCacheDir(this) + File.separator + PLUGIN_NAME);

        // make sure you have permission for target path
        Log.d(TAG, "plugin.canRead = " + plugin.canRead());

        if (!plugin.exists()) {
            Log.w(TAG, PLUGIN_NAME + " not exists");
            return;
        }

        Log.w(TAG, "dexPath = " +  plugin.getAbsolutePath());
        Log.w(TAG, "optimizedDirectory = " +  getExternalCacheDir().getAbsolutePath());

        DexClassLoader dexClassLoader =
                new DexClassLoader(plugin.getAbsolutePath(), getExternalCacheDir().getAbsolutePath(), null, getClassLoader());
        try {
            // load DataChange
            mDexClazz = dexClassLoader.loadClass(PLUGIN_CLASS);
            // change to target IChange, please make sure it must has same package name withe aar
            //IChange change = (IChange) clazz.newInstance();
           // mResult.setText(change.start());
            //Log.d(TAG, "result = " + change.start());
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "ClassNotFoundException ex", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception ex", e);
        }
    }
}
