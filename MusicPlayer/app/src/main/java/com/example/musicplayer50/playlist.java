package com.example.musicplayer50;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * playlist播放列表
 *
 * 1、主体结构和localmusicactivity一样，只不过localmusicactivity是读取手机的全部音乐，而playlist是读取自己新建的表格的歌曲。
 *
 * 2、在playlist里新增有长按弹出选项，删除功能。也有一个清空播放列表的功能。
 */
public class playlist extends AppCompatActivity {
    private int count;
    private MusicSqlHelper dbHelper;
    private ArrayAdapter adapter;
    private MusicService musicService;
    private ListView listView;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private List<Music> musics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        dbHelper = new MusicSqlHelper(this,"login.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("login",null,null,null,null,null,null);
        musics = new ArrayList<Music>();
        musics.clear();
        count = cursor.getCount();
        for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String artist = cursor.getString(cursor.getColumnIndex("artist"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                Music music = new Music();
                music.setTitle(title);
                music.setArtist(artist);
                music.setUrl(url);
                musics.add(music);
                Log.e("dai", "music adds succeedly");
        }
        cursor.close();

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.start();
            }
        });

        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("login",null,null);
                Log.e("dai","count = "+count);
                for (int i = 1; i <= count; i++) {
                     musics.remove(0);
                }
                adapter.notifyDataSetChanged();
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }

        adapter = new MusicAdapter(playlist.this, R.layout.musicitem, musics); //新建想对应的适配器
      // adapter = new ArrayAdapter<String>(playlist.this,android.R.layout.simple_list_item_1,list);     //用字符串适配器试验
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);

        this.registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Music music = musics.get(position);
                String url = music.getUrl();
                String title = music.getTitle();
                String artist = music.getArtist();

                Intent intent = new Intent("startnew");
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.putExtra("artist", artist);

                final Intent eintent = new Intent(createExplicitFromImplicitIntent(playlist.this, intent));
                bindService(eintent, conn, Service.BIND_AUTO_CREATE);
                startService(eintent);
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,1,0,"删除");
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case 1:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String title = ((TextView)menuInfo.targetView.findViewById(R.id.songname)).getText().toString();

                db.delete("login", "title =?", new String[]{title+""});
                Log.e("dai","删除SQL项成功" );
                musics.remove(menuInfo.position);
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }
}