package com.vv.cache.utils;

import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;

/**
 * Created by vivian on 2017/11/14.
 * DiskLruCache utils (https://github.com/JakeWharton/DiskLruCache)
 * add new support following object save and get
 * Bitmap bitmap, byte[] value, String value, JSONObject jsonObject, JSONArray jsonArray,
 * Serializable , Drawable
 * Attention: you need setDiskLruCache before you call any method here
 */

public final class DiskLruCacheUtils {
    private static final String TAG = "DiskLruCacheUtils";
    // DiskLruCache object, set from outside
    private static DiskLruCache mDiskLruCache;

    /**
     * Sets disk lru cache.
     * you need setDiskLruCache before you call any method here
     *
     * @param cache the cache
     */
    public static void setDiskLruCache(DiskLruCache cache) {
        mDiskLruCache = cache;
    }

    /**
     * Set String value
     *
     * @param key   the key
     * @param value the value
     */
    public static void set(String key, String value) {
        DiskLruCache.Editor editor = getEditor(key);
        try {
            if (editor != null) {
                editor.set(0, value);
                // write ,CLEAN
                editor.commit();
            }
        } catch (IOException e) {
            Log.w(TAG, "set() ex ", e);
            abortEditor(editor);
        }
    }

    /**
     * Gets string value
     *
     * @param key the key
     * @return the string
     */
    public static String getString(String key) {
        DiskLruCache.Snapshot snapShot = getSnapshot(key);
        try {
            if (snapShot != null) {
                return snapShot.getString(0);
            }
        } catch (IOException e) {
            Log.w(TAG, "getString() ex ", e);
        }
        return "";
    }

//
//
//    public void put(String key, JSONObject jsonObject) {
//        put(key, jsonObject.toString());
//    }
//
//    public JSONObject getAsJson(String key) {
//        String val = getAsString(key);
//        try {
//            if (val != null)
//                return new JSONObject(val);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // =======================================
//    // ============ JSONArray 数据 读写 =============
//    // =======================================
//
//    public void put(String key, JSONArray jsonArray) {
//        put(key, jsonArray.toString());
//    }
//
//    public JSONArray getAsJSONArray(String key) {
//        String JSONString = getAsString(key);
//        try {
//            JSONArray obj = new JSONArray(JSONString);
//            return obj;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // =======================================
//    // ============== byte 数据 读写 =============
//    // =======================================
//
//    /**
//     * 保存 byte数据 到 缓存中
//     *
//     * @param key   保存的key
//     * @param value 保存的数据
//     */
//    public void put(String key, byte[] value) {
//        OutputStream out = null;
//        DiskLruCache.Editor editor = null;
//        try {
//            editor = editor(key);
//            if (editor == null) {
//                return;
//            }
//            out = editor.newOutputStream(0);
//            out.write(value);
//            out.flush();
//            editor.commit();//write CLEAN
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                editor.abort();//write REMOVE
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//        } finally {
//            if (out != null) {
//                try {
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    public byte[] getAsBytes(String key) {
//        byte[] res = null;
//        InputStream is = get(key);
//        if (is == null) return null;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            byte[] buf = new byte[256];
//            int len = 0;
//            while ((len = is.read(buf)) != -1) {
//                baos.write(buf, 0, len);
//            }
//            res = baos.toByteArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//
//    // =======================================
//    // ============== 序列化 数据 读写 =============
//    // =======================================
//    public void put(String key, Serializable value) {
//        DiskLruCache.Editor editor = editor(key);
//        ObjectOutputStream oos = null;
//        if (editor == null) return;
//        try {
//            OutputStream os = editor.newOutputStream(0);
//            oos = new ObjectOutputStream(os);
//            oos.writeObject(value);
//            oos.flush();
//            editor.commit();
//        } catch (IOException e) {
//            e.printStackTrace();
//            try {
//                editor.abort();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } finally {
//            try {
//                if (oos != null)
//                    oos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public <T> T getAsSerializable(String key) {
//        T t = null;
//        InputStream is = get(key);
//        ObjectInputStream ois = null;
//        if (is == null) return null;
//        try {
//            ois = new ObjectInputStream(is);
//            t = (T) ois.readObject();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (ois != null)
//                    ois.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return t;
//    }
//
//    // =======================================
//    // ============== bitmap 数据 读写 =============
//    // =======================================
//    public void put(String key, Bitmap bitmap) {
//        put(key, Utils.bitmap2Bytes(bitmap));
//    }
//
//    public Bitmap getAsBitmap(String key) {
//        byte[] bytes = getAsBytes(key);
//        if (bytes == null) return null;
//        return Utils.bytes2Bitmap(bytes);
//    }
//
//    // =======================================
//    // ============= drawable 数据 读写 =============
//    // =======================================
//    public void put(String key, Drawable value) {
//        put(key, Utils.drawable2Bitmap(value));
//    }
//
//    public Drawable getAsDrawable(String key) {
//        byte[] bytes = getAsBytes(key);
//        if (bytes == null) {
//            return null;
//        }
//        return Utils.bitmap2Drawable(Utils.bytes2Bitmap(bytes));
//    }
//
//    // =======================================
//    // ============= other methods =============
//    // =======================================
//    public boolean remove(String key) {
//        try {
//            key = Utils.hashKeyForDisk(key);
//            return mDiskLruCache.remove(key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public void close() throws IOException {
//        mDiskLruCache.close();
//    }
//
//    public void delete() throws IOException {
//        mDiskLruCache.delete();
//    }
//
//    public void flush() throws IOException {
//        mDiskLruCache.flush();
//    }
//
//    public boolean isClosed() {
//        return mDiskLruCache.isClosed();
//    }
//
//    public long size() {
//        return mDiskLruCache.size();
//    }
//
//    public void setMaxSize(long maxSize) {
//        mDiskLruCache.setMaxSize(maxSize);
//    }
//
//    public File getDirectory() {
//        return mDiskLruCache.getDirectory();
//    }
//
//    public long getMaxSize() {
//        return mDiskLruCache.getMaxSize();
//    }
//
//
//    // =======================================
//    // ===遇到文件比较大的，可以直接通过流读写 =====
//    // =======================================
//    //basic editor
//    public DiskLruCache.Editor editor(String key) {
//        try {
//            key = Utils.hashKeyForDisk(key);
//            //wirte DIRTY
//            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
//            //edit maybe null :the entry is editing
//            if (edit == null) {
//                Log.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
//            }
//            return edit;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//
//    //basic get
//    public InputStream get(String key) {
//        try {
//            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(Utils.hashKeyForDisk(key));
//            if (snapshot == null) //not find entry , or entry.readable = false
//            {
//                Log.e(TAG, "not find entry , or entry.readable = false");
//                return null;
//            }
//            //write READ
//            return snapshot.getInputStream(0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//
//
//    // =======================================
//    // ============== 序列化 数据 读写 =============
//    // =======================================
//
//    private File getDiskCacheDir(Context context, String uniqueName) {
//        String cachePath;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                || !Environment.isExternalStorageRemovable()) {
//            cachePath = context.getExternalCacheDir().getPath();
//        } else {
//            cachePath = context.getCacheDir().getPath();
//        }
//        return new File(cachePath + File.separator + uniqueName);
//    }

    private static DiskLruCache.Editor getEditor(String key) {
        if (mDiskLruCache == null) {
            Log.e(TAG, "you need call setDiskLruCache() to set mDiskLruCache first.");
            return null;
        }
        try {
            return mDiskLruCache.edit(key);
        } catch (IOException e) {
            Log.w(TAG, "getEditor() ex ", e);
        }

        return null;
    }

    private static DiskLruCache.Snapshot getSnapshot(String key) {
        if (mDiskLruCache == null) {
            Log.e(TAG, "you need call setDiskLruCache() to set mDiskLruCache first.");
            return null;
        }
        try {
            return mDiskLruCache.get(key);
        } catch (IOException e) {
            Log.w(TAG, "getEditor() ex ", e);
        }

        return null;
    }

    private static void abortEditor(DiskLruCache.Editor editor) {
        try {
            // write fail, REMOVE
            editor.abort();
        } catch (IOException e) {
            Log.w(TAG, "set() editor.abort ex ", e);
        }
    }
}
