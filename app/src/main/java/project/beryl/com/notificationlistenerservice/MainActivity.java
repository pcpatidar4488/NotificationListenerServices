package project.beryl.com.notificationlistenerservice;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class MainActivity extends Activity {

    protected MyReceiver mReceiver = new MyReceiver();
    public static String INTENT_ACTION_NOTIFICATION = "it.gmariotti.notification";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Map> list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_main);

        //Retrieve ui elements
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        list = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new NotificationListAdapter(MainActivity.this, list);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_autorize:
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReceiver == null) mReceiver = new MyReceiver();
        registerReceiver(mReceiver, new IntentFilter(INTENT_ACTION_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                Bundle extras = intent.getExtras();
                String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
                int notificationIcon = extras.getInt(Notification.EXTRA_SMALL_ICON);
                Bitmap notificationLargeIcon = ((Bitmap) extras.getParcelable(Notification.EXTRA_LARGE_ICON));
                CharSequence notificationText = extras.getCharSequence(Notification.EXTRA_TEXT);
                CharSequence notificationSubText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT);

                Map map = new HashMap();
                if (!notificationTitle.equals("Change keyboard")){
                    map.put("title",notificationTitle);
                    map.put("text",notificationText);
                    map.put("subtext",notificationSubText);
                    map.put("largeIcon",notificationLargeIcon);
                    list.add(map);
                }

               /* if (notificationLargeIcon != null) {
                    map.put("largeIcon",notificationLargeIcon);
                }else {
                    map.put("largeIcon","");
                }*/
                mAdapter.notifyDataSetChanged();
            }

        }
    }
}