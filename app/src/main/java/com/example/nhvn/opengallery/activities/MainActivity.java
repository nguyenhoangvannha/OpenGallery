package com.example.nhvn.opengallery.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.nhvn.opengallery.R;
import com.example.nhvn.opengallery.adapters.PhotosAdapter;
import com.example.nhvn.opengallery.data.Album;
import com.example.nhvn.opengallery.data.provider.CPHelper;
import com.example.nhvn.opengallery.fragments.PhotosFragment;
import com.example.nhvn.opengallery.fragments.AlbumsFragment;
import com.example.nhvn.opengallery.fragments.VideosFragment;
import com.example.nhvn.opengallery.interfaces.IFragToMain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IFragToMain{
    private FrameLayout content;
    private ArrayList<Album> albums;
    private ArrayList<Album> videos;
    private boolean isAlbumsMode = false;
    private boolean isVideosMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_hidden).setActionView(new Switch(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        albums = CPHelper.getAlbums(this);
        getSupportFragmentManager().beginTransaction().add(R.id.content, AlbumsFragment.newInstance(albums))
                .addToBackStack(AlbumsFragment.TAG).commit();
        isAlbumsMode = true;
    }

    @Override
    public void onBackPressed() {
        if (isAlbumsMode || isVideosMode) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
            isAlbumsMode = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_albums) {
            if(!isAlbumsMode)
                getSupportFragmentManager().popBackStack();
            // Handle the camera action
        } else if (id == R.id.nav_videos) {
            isVideosMode = true;
            isAlbumsMode = false;
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.content, VideosFragment.newInstance("ABC", "DEF"), VideosFragment.TAG)
//                    .addToBackStack(VideosFragment.TAG).commit();
            videos = CPHelper.getVideos(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, AlbumsFragment.newInstance(videos))
                    .addToBackStack(AlbumsFragment.TAG).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_hidden) {
            ((Switch) item.getActionView()).toggle();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMsgFromFragToMain(Album album) {
//        Intent intent = new Intent(MainActivity.this, PhotosActivity.class);
//        intent.putExtra("ALBUM", album);
//        startActivity(intent);
        isAlbumsMode = false;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, PhotosFragment.newInstance(this, album))
                .addToBackStack(PhotosFragment.TAG)
                .commit();

    }

    @Override
    public void onMsgFromFragToMain(Album album, int pos, int isVideo) {
        if(isVideo!=0){
            Intent intent = new Intent(this, ViewVideoActivity.class);
            intent.putExtra("VIDEOS", album);
            intent.putExtra("POS", pos);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, PhotosPagerActivity.class);
            intent.putExtra("ALBUM", album);
            intent.putExtra("POS", pos);
            startActivity(intent);
        }

    }

    //@Override
    //public void onMsgFromFragToMain(Album video, int pos) {
    //    Intent intent = new Intent(this, PhotosPagerActivity.class);
    //    intent.putExtra("VIDEO", video);
    //    intent.putExtra("POS", pos);
    //    startActivity(intent);
    //}

}
