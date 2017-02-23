package com.vv.frescodemo.ui;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.vv.frescodemo.MainActivity;
import com.vv.frescodemo.R;
import com.vv.frescodemo.common.Utils;

public class ImageAdapter extends BaseAdapter {
    private final static String TAG = ImageAdapter.class.getSimpleName();
    private LayoutInflater mInflater;

    public ImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Utils.sUrList.size();
    }

    @Override
    public String getItem(int position) {
        return Utils.sUrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.image_list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (SimpleDraweeView) convertView.findViewById(R.id.img_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SimpleDraweeView imageView = holder.imageView;
        final String tag = (String) imageView.getTag();
        final String uri = getItem(position);
        if (Utils.sCanGetBitmapFromNetWork) {
            imageView.setTag(uri);
            setImage(imageView, uri, Utils.sImageWidth, Utils.sImageWidth);
        }
        return convertView;
    }

    private void setImage(SimpleDraweeView draweeView, String uri, int width, int height) {
        // set ProgressiveRendering
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
        // set listener
        DraweeController control = Fresco.newDraweeControllerBuilder()
                .setControllerListener(mImageListener)
                .setUri(uri)
                // other setters
                .build();
        draweeView.setController(control);
        // set uri
        draweeView.setImageURI(uri);
    }

    ControllerListener mImageListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                return;
            }
            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            FLog.d(TAG, "Final image received! " +
                            "Size %d x %d",
                    "Quality level %d, good enough: %s, full quality: %s",
                    imageInfo.getWidth(),
                    imageInfo.getHeight(),
                    qualityInfo.getQuality(),
                    qualityInfo.isOfGoodEnoughQuality(),
                    qualityInfo.isOfFullQuality());
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            FLog.d(TAG, "Intermediate image received");
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            FLog.e(getClass(), throwable, "Error loading %s", id);
        }
    };

    private static class ViewHolder {
        public SimpleDraweeView imageView;
    }

}
