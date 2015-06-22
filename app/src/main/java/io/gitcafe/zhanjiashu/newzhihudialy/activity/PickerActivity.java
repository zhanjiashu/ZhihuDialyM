package io.gitcafe.zhanjiashu.newzhihudialy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.gitcafe.zhanjiashu.newzhihudialy.R;
import io.gitcafe.zhanjiashu.newzhihudialy.fragment.DialyFragment;
import io.gitcafe.zhanjiashu.newzhihudialy.util.DateUtil;

public class PickerActivity extends BaseActivity {

    private static final String EXTRA_DATE = "extra_date";
    private static final String EXTRA_MILLISECONDS = "extra_milliseconds";
    private static final String TAG = "PickerActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra(EXTRA_DATE));
        }

        if (savedInstanceState == null) {
            if (findViewById(R.id.fl_container) != null) {
                String beforeDate = DateUtil.getBeforeDate(getIntent().getLongExtra(EXTRA_MILLISECONDS, 0));
                DialyFragment fragment = DialyFragment.newInstance(beforeDate);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_container, fragment)
                        .commit();
            }
        }
    }

    public static void startBy(Context context, String date,  long millisenconds) {
        Intent intent = new Intent(context, PickerActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        intent.putExtra(EXTRA_MILLISECONDS, millisenconds);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
