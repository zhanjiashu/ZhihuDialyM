package io.gitcafe.zhanjiashu.newzhihudialy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeEntity;
import io.gitcafe.zhanjiashu.newzhihudialy.model.ThemeListEntity;

/**
 * Created by Jiashu on 2015/6/9.
 */
public class NavListAdapter extends SimpleBaseAdapter<ThemeEntity> {

    private DisplayImageOptions mDisplayImageOptions;

    public NavListAdapter(Context context, List<ThemeEntity> data) {
        super(context, data);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.push_icon)
                .showImageOnFail(R.drawable.push_icon)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    @Override
    protected View getItemView(int position, View convertView, ViewHolder holder) {
        TextView textView = holder.getView(R.id.tv_theme_name);
        textView.setText(getItem(position).getName());

        CircleImageView imageView = holder.getView(R.id.iv_theme_img);
        ImageLoader.getInstance().displayImage(getItem(position).getThumbnail(), imageView, mDisplayImageOptions);

        return convertView;
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_nav_menu;
    }
}
