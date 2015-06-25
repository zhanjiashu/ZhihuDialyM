package io.gitcafe.zhanjiashu.newzhihudialy.other;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created With Android Studio
 * User @47
 * Date 2014-07-27
 * Time 20:55
 * 显示原型图片的ImageLoader使用的显示器
 *
 */
public class CircleBitmapDisplayer implements BitmapDisplayer {

    protected  final int margin ;

    public CircleBitmapDisplayer() {
        this(0);
    }

    public CircleBitmapDisplayer(int margin) {
        this.margin = margin;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new CircleDrawable(bitmap, margin));
    }

    static class CircleDrawable extends Drawable {
        public static final String TAG = "CircleDrawable";

        protected final Paint paint;

        protected final int margin;
        protected final BitmapShader bitmapShader;
        protected float radius;
        protected Bitmap oBitmap;//原图
        public CircleDrawable(Bitmap bitmap){
            this(bitmap,0);
        }

        public CircleDrawable(Bitmap bitmap, int margin) {
            this.margin = margin;
            this.oBitmap = bitmap;
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            computeBitmapShaderSize();
            computeRadius();

        }

        @Override
        public void draw(Canvas canvas) {
            Rect bounds = getBounds();//画一个圆圈
            canvas.drawCircle(bounds.width() / 2F,bounds.height() / 2F,radius,paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }


        /**
         * 计算Bitmap shader 大小
         */
        public void computeBitmapShaderSize(){
            Rect bounds = getBounds();
            if(bounds == null) return;
            //选择缩放比较多的缩放，这样图片就不会有图片拉伸失衡
            Matrix matrix = new Matrix();
            float scaleX = bounds.width() / (float)oBitmap.getWidth();
            float scaleY = bounds.height() / (float)oBitmap.getHeight();
            float scale = scaleX > scaleY ? scaleX : scaleY;
            matrix.postScale(scale,scale);
            bitmapShader.setLocalMatrix(matrix);
        }

        /**
         * 计算半径的大小
         */
        public void computeRadius(){
            Rect bounds = getBounds();
            radius = bounds.width() < bounds.height() ?
                    bounds.width() /2F - margin:
                    bounds.height() / 2F - margin;
        }
    }
}