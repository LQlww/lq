package com.example.musicplayer50;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 *localmusicactivity 本地所有音乐曲目的界面 
 *
 * 1、定义一个Music类，里面包含了歌曲的相关信息，比如歌曲地址，名字还有歌手以及get方法；
 *
 * 2、findmusic类用来查找本地音乐信息，将歌曲传递到之前定义的Music型形成一个list<Music>的数组，并返回这个数组；
 *
 * 3、MusicAdapter自定义listview适配器，让listview显示全部歌曲。 
 *
 * 4、当在本地曲库点击某一首歌曲的时候，如果这首歌曲播放列表已有，则不添加到播放列表，如果没有则添加到播放列表。具体操作为将该首歌曲的信息传入相应的SQL表格中，SQL表中的数据将作为playlist(播放列表)里listview歌单的数据显示出来。
 */
public class LocalMusicActivity extends AppCompatActivity {

    private MusicSqlHelper dbHelper;
    private MusicService musicService;
    private MusicAdapter adapter;
    private Boolean Exist = false;
    ListView listView;
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
        setContentView(R.layout.localmusic);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        listView = (ListView) findViewById(R.id.listView);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_EXTERNAL_STORAGE);
        }

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.start();
            }
        });
        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(LocalMusicActivity.this,playlist.class);
                startActivity(intent3);
            }
        });



        dbHelper = new MusicSqlHelper(this,"login.db",null,1);


        Findmusic findmusic = new Findmusic();
        musics = findmusic.getmusics(LocalMusicActivity.this.getContentResolver());   //找到资源，music型组
        adapter = new MusicAdapter(LocalMusicActivity.this,R.layout.musicitem,musics); //新建想对应的适配器
        listView.setAdapter(adapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.e("dai", "onitemclick");
                Music music = musics.get(position);
                String url = music.getUrl();
                String title = music.getTitle();
                String artist = music.getArtist();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.query("login",null,null,null,null,null,null);
                Log.e("dai","当前歌曲的title是："+title );
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    Log.e("dai","当前游标title是："+cursor.getString(cursor.getColumnIndex("title")));
                    if(title.equals(cursor.getString(cursor.getColumnIndex("title")))) {
                        Log.e("dai","已经存在歌曲，不插入了" );
                        Exist = true;
                        break;
                    }
                }
                Log.e("dai","当前歌曲是否存在 "+Exist );
                if(Exist==false) {
                    Log.e("dai", "创建键");
                    values.put("title", title);
                    values.put("artist", artist);
                    values.put("url", url);
                    db.insert("login", null, values);
                    values.clear();
                    Log.e("dai", "成功插入login表");
                    Exist = false;
                }
                cursor.close();
                Intent intent = new Intent("startnew");
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                intent.putExtra("artist",artist);

                final Intent eintent = new Intent(createExplicitFromImplicitIntent(LocalMusicActivity.this,intent));
                bindService(eintent,conn, Service.BIND_AUTO_CREATE);
                startService(eintent);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
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
