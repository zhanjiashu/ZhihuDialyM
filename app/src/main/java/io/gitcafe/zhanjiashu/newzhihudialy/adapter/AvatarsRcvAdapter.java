package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.common.CircleBitmapDisplayer;

/**
 * Created by Jiashu on 2015/6/5.
 */
public class AvatarsRcvAdapter extends RecyclerView.Adapter<AvatarsRcvAdapter.AvatarViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final DisplayImageOptions mDisplayImageOptions;

    private List<String> mAvatarList;

    public AvatarsRcvAdapter(Context context, List<String> avatarList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        mAvatarList = avatarList;

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.editor_profile_avatar)
                .showImageOnFail(R.drawable.editor_profile_avatar)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new CircleBitmapDisplayer())
                .build();
    }

    @Override
    public AvatarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AvatarViewHolder(mLayoutInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(AvatarViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(mAvatarList.get(position), holder.mAvatarView, mDisplayImageOptions);
    }

    @Override
    public int getItemCount() {
        return mAvatarList.size();
    }

    static class AvatarViewHolder extends RecyclerView.ViewHolder {

        ImageView mAvatarView;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            mAvatarView = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
