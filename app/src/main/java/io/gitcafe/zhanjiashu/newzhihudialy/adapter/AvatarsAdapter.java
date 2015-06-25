package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.gitcafe.zhanjiashu.common.adapter.BaseRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.R;

/**
 * Created by Jiashu on 2015/6/18.
 */
public class AvatarsAdapter extends BaseRcvAdapter<String> {

    private final DisplayImageOptions mDisplayImageOptions;

    private boolean mIsNoPictureMode;

    public AvatarsAdapter(Context context, List<String> data, boolean isNoPictureMode) {
        super(context, data);

        mIsNoPictureMode = isNoPictureMode;

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.editor_profile_avatar)
                .showImageOnFail(R.drawable.editor_profile_avatar)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateAdapterViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new AvatarViewHolder(mLayoutInflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    protected void onBindAdapterViewHolder(RecyclerView.ViewHolder holder, int position, String str) {
        AvatarViewHolder avatarViewHolder = (AvatarViewHolder) holder;
        if (!mIsNoPictureMode) {
            ImageLoader.getInstance().displayImage(str, avatarViewHolder.mAvatarView, mDisplayImageOptions);
        }
    }

    @Override
    protected int getAdapterItemViewType(int position, String s) {
        return 0;
    }

    static class AvatarViewHolder extends RecyclerView.ViewHolder {

        CircleImageView mAvatarView;

        public AvatarViewHolder(View itemView) {
            super(itemView);
            mAvatarView = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}
