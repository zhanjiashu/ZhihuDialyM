package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.model.StoryEntity;

/**
 * Created by Jiashu on 2015/6/2.
 */
public class StoriesRcvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_IMAGE = 10;
    private static final int TYPE_NO_IMAGE = 11;

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private DisplayImageOptions mDisplayImageOptions;

    private List<StoryEntity> mStories;

    private OnItemClickListener mOnItemClickListener;

    public StoriesRcvAdapter(Context context, List<StoryEntity> stories) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mStories = stories != null ? stories : new ArrayList<StoryEntity>();

        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.push_icon)
                .showImageOnFail(R.drawable.push_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IMAGE) {
            return new StoriesViewHolder(mLayoutInflater.inflate(R.layout.item_card_story, parent, false), mOnItemClickListener);
        }
        return new SingleViewHolder(mLayoutInflater.inflate(R.layout.item_card_story_no_img, parent, false), mOnItemClickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoryEntity entity = mStories.get(position);

        if (holder instanceof StoriesViewHolder) {

            ((StoriesViewHolder) holder).mTitleView.setText(entity.getTitle());

            ImageLoader.getInstance().displayImage(
                    entity.getImages().get(0),
                    ((StoriesViewHolder)holder).mImageView,
                    mDisplayImageOptions);
        } else if (holder instanceof SingleViewHolder) {

            ((SingleViewHolder) holder).mTitleView.setText(entity.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    @Override
    public int getItemViewType(int position) {
        List<String> iamges = mStories.get(position).getImages();
        if (iamges != null && iamges.size() > 0) {
            return TYPE_IMAGE;
        }
        return TYPE_NO_IMAGE;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public List<StoryEntity> getData() {
        return mStories;
    }

    public void replace(List<StoryEntity> stories) {
        mStories = stories;
        notifyDataSetChanged();
    }


    static class StoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @InjectView(R.id.tv_story_title)
        TextView mTitleView;

        @InjectView(R.id.iv_story_image)
        ImageView mImageView;

        private OnItemClickListener mClickListener;

        public StoriesViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mClickListener = listener;
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    static class SingleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener mClickListener;

        private TextView mTitleView;

        public SingleViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            mTitleView = (TextView) itemView.findViewById(R.id.tv_story_title);

            mClickListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view,int postion);
    }
}
