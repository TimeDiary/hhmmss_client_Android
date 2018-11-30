package kr.co.hhmmss.hhmmss;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kr.co.hhmmss.hhmmss.auth.SignInActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Navigation Drawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    // User info on Navigation Drawer
    private FirebaseUser fbUser;
    private Bitmap bitmap;

    // For Fragments
    private CalendarFragment calendarFragment;
    private TimediaryFragment timediaryFragment;
    private TodoFragment todoFragment;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [START init_fragments]
        calendarFragment = new CalendarFragment();
        timediaryFragment = new TimediaryFragment();
        todoFragment = new TodoFragment();
        // [END init_fragments]

        // [START init_layout]
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the Actionbar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find drawer view
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);
        // [END init_layout]

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

//            // Create a new Fragment to be placed in the activity layout
//            TimediaryFragment mainFragment = new MainFragment();
//
//            // In case this activity was started with special instructions from an
//            // Intent, pass the Intent's extras to the fragment as arguments
//            mainFragment.setArguments(getIntent().getExtras());
//
//            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();
        }

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        fbUser = FirebaseAuth.getInstance().getCurrentUser();


        //actionBar.setDisplayShowTitleEnabled(false);

        bnv.setOnNavigationItemSelectedListener(new MyItemSelectedListener());
        /* Set default fragment */
        openFragment(timediaryFragment);
        initLayout();
    }

    class MyItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        private final String TAG = MyItemSelectedListener.class.getSimpleName();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.Calendar:
                    fragment = calendarFragment;
                    break;
                case R.id.TimeDiary:
                    fragment = timediaryFragment;
                    break;
                case R.id.ToDo:
                    fragment = todoFragment;
                    break;
                default:
                    fragment = timediaryFragment;
            }
            openFragment(fragment);

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            return true;
        }
    }

    public void openFragment(final Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Navigation Drawer item select
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_drawer_item1:
                Toast.makeText(this, "item1 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_drawer_item2:
                Toast.makeText(this, "item2 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_drawer_item3:
                Toast.makeText(this, "item3 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_drawer_logout:
                AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show();
                break;

            case R.id.navigation_drawer_delete:
                AuthUI.getInstance().delete(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                Toast.makeText(this, "계정삭제", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Navigation Drawer init
    private void initLayout() {
//        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //getSupportActionBar().setTitle("");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_main_navigation_root);
        View headerView = navigationView.getHeaderView(0);

        //Set Component for User Info
        ImageView nav_header_user_Profile = headerView.findViewById(R.id.nav_header_main_user_Profile);
        TextView nav_header_user_ID = (TextView) headerView.findViewById(R.id.nav_header_main_user_ID);
        TextView nav_header_user_Account = (TextView) headerView.findViewById(R.id.nav_header_main_user_Account);

        if (fbUser != null) {

            //Uri to Bitmap
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(fbUser.getPhotoUrl().toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException mue) {
                        mue.printStackTrace();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };
            mThread.start();
            try {
                mThread.join();
                //Set User Profile
                nav_header_user_Profile.setImageBitmap(bitmap);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            //Set User ID
            nav_header_user_ID.setText(fbUser.getDisplayName());
            //Set User Email Account
            if (fbUser.isEmailVerified())
                nav_header_user_Account.setText(fbUser.getEmail());
        } else {
            nav_header_user_Profile.setImageURI(null);
            nav_header_user_ID.setText(R.string.offline);
            nav_header_user_Account.setText(null);
        }

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
