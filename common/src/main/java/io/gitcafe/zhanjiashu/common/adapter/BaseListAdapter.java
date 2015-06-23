package io.gitcafe.zhanjiashu.common.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiashu on 2015/4/15.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mData;
    private int mItemResource;

    public BaseListAdapter(Context context, List<T> data) {
        mContext = context;
        mData = (data == null ? new ArrayList<T>() : data);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemResource(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return getItemView(position, convertView, holder);
    }

    protected abstract View getItemView(int position, View convertView, ViewHolder holder);

    protected abstract int getItemResource();

    public void addAll(List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mData.remove(index);
    }

    public void remove(T item) {
        mData.remove(item);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private SparseArray<View> viewArray = new SparseArray<View>();
        private View view;

        ViewHolder(View view) {
            this.view = view;
        }

        public <T extends View> T getView(int resId) {
            View v = viewArray.get(resId);
            if (v == null) {
                v = view.findViewById(resId);
                viewArray.put(resId, v);
            }
            return (T) v;
        }
    }
}
