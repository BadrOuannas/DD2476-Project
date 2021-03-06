24
https://raw.githubusercontent.com/giswangsj/RvParallaxImageView/master/app/src/main/java/per/wsj/rvparallaximageview/controller/GlideImageController.java
package per.wsj.rvparallaximageview.controller;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import per.wsj.lib.controller.BaseImageController;


/**
 * 使用Glide加载图片
 */

public class GlideImageController extends BaseImageController {

    private Context mContext;

    private String imageUrl;

    public GlideImageController(Context context, String imageUrl) {
        this.mContext = context;
        this.imageUrl = imageUrl;
    }

    @Override
    protected void loadImage(int viewWidth) {
        if (imageUrl.isEmpty()) {
            return;
        }

        // 利用Glide获取drawable
        Glide.with(mContext).load(imageUrl).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                // 处理drawable
                handleDrawable(viewWidth, resource);
            }
        });
    }
}
