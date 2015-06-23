package io.gitcafe.zhanjiashu.common.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/6/17.
 */
public abstract class BaseRcvAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseRcyAdaper";

    protected final Context mContext;
    protected final LayoutInflater mLayoutInflater;
    protected List<T> mData;

    @LayoutRes
    private int mHeaderViewLayoutRes;
    @LayoutRes
    private int mFooterViewLayoutRes;

    private boolean mHasHeaderView;
    private boolean mHasFooterView;

    private OnItemClickListener<T> mItemClickListener;
    private OnItemLongClickListener<T> mItemLongClickListener;

    public BaseRcvAdapter(Context context, List<T> data) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        mData = data != null ? data : new ArrayList<T>();
    }

    class VIEW_TYPES {
        static final int HEADER = 11;
        static final int FOOTER = 12;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_TYPES.HEADER == viewType && mHasHeaderView) {
            return new SimpleViewHolder(mLayoutInflater.inflate(mHeaderViewLayoutRes, parent, false));
        } else if (VIEW_TYPES.FOOTER == viewType && mHasFooterView) {
            return new SimpleViewHolder(mLayoutInflater.inflate(mFooterViewLayoutRes, parent, false));
        }
        return onCreateAdapterViewHolder(mLayoutInflater, parent, viewType);
    }

    protected abstract RecyclerView.ViewHolder onCreateAdapterViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int newPosition;

        if (mHasHeaderView && position > 0) {
            newPosition = position - 1;
        } else {
            newPosition = position;
        }

        if ((!mHasHeaderView || position != 0) &&
                (!mHasFooterView || position != getItemCount() - 1)) {

            final int finalNewPosition = newPosition;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(holder.itemView, finalNewPosition, mData.get(finalNewPosition));
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   if (mItemLongClickListener != null) {
                       return mItemLongClickListener.onItemLongClick(holder.itemView, finalNewPosition, mData.get(finalNewPosition));
                   }
                    return true;
                }
            });

            onBindAdapterViewHolder(holder, newPosition, mData.get(newPosition));
        }
    }

    protected abstract void onBindAdapterViewHolder(RecyclerView.ViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {

        int otherCount = 0;

        if (mHasHeaderView) {
            otherCount++;
        }

        if (mHasFooterView) {
            otherCount++;
        }

        return otherCount + mData.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mHasFooterView && position == getItemCount() - 1) {
            return VIEW_TYPES.FOOTER;
        }

        if (mHasHeaderView) {
            if (position == 0) {
                return VIEW_TYPES.HEADER;
            } else {
                return getAdapterItemViewType(position - 1, mData.get(position - 1));
            }
        }
        return getAdapterItemViewType(position, mData.get(position));
    }

    protected abstract int getAdapterItemViewType(int position, T t);

    public void addHeaderView(@LayoutRes int layoutRes) {
        mHeaderViewLayoutRes = layoutRes;
        mHasHeaderView = true;
    }

    public void addFooterView(@LayoutRes int layoutRes) {
        mFooterViewLayoutRes = layoutRes;
        mHasFooterView = true;
    }

    public void addAll(List<T> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void replace(List<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The adapter data can not be null");
        }
        mData.clear();
        mData = data;
        notifyDataSetChanged();
    }

    public T getLastItem() {
        return mData.get(mData.size() - 1);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        mItemLongClickListener = listener;
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<E> {
        void onItemClick(View view, int position, E e);
    }

    public interface OnItemLongClickListener<E> {
        boolean onItemLongClick(View view, int position, E e);
    }
}
