package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.common.adapter.BaseRcvAdapter;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;

/**
 * Created by Jiashu on 2015/6/18.
 */
public class StoriesAdapter extends BaseRcvAdapter<StoryEntity> {

    private DisplayImageOptions mDisplayImageOptions;

    static class VIEW_TYPES {
        static final int TEXT = 1;
        static final int TEXT_AND_IMAGE = 2;
    }

    public StoriesAdapter(Context context, List<StoryEntity> data) {
        super(context, data);

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.push_icon)
                .showImageOnFail(R.drawable.push_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateAdapterViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.TEXT_AND_IMAGE) {
            return new TextAndImageViewHolder(mLayoutInflater.inflate(R.layout.item_card_story, parent, false));
        }
        return new TextViewHolder(mLayoutInflater.inflate(R.layout.item_card_story_no_img, parent, false));
    }

    @Override
    protected void onBindAdapterViewHolder(RecyclerView.ViewHolder holder, int position, StoryEntity entity) {
        if (holder instanceof TextAndImageViewHolder) {

            ((TextAndImageViewHolder) holder).mTitleView.setText(entity.getTitle());

            ImageLoader.getInstance().displayImage(
                    entity.getImages().get(0),
                    ((TextAndImageViewHolder)holder).mImageView,
                    mDisplayImageOptions);

        } else if (holder instanceof TextViewHolder) {

            ((TextViewHolder) holder).mTitleView.setText(entity.getTitle());

        }
    }

    @Override
    protected int getAdapterItemViewType(int position, StoryEntity storyEntity) {
        List<String> iamges = storyEntity.getImages();
        if (iamges != null && iamges.size() > 0) {
            return VIEW_TYPES.TEXT_AND_IMAGE;
        }
        return VIEW_TYPES.TEXT;
    }

    static class TextAndImageViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.tv_story_title)
        TextView mTitleView;

        @InjectView(R.id.iv_story_image)
        ImageView mImageView;

        public TextAndImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitleView;

        public TextViewHolder(View itemView) {
            super(itemView);

            mTitleView = (TextView) itemView.findViewById(R.id.tv_story_title);
        }

    }
}
