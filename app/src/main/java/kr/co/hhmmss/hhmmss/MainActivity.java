package kr.co.hhmmss.hhmmss;

import android.content.Intent;
import android.content.res.Configuration;
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
import android.util.Log;
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

import kr.co.hhmmss.hhmmss.auth.SignInActivity;

//TODO Set UserInfo, use UserInfo Class // Fragment
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bnv;

    // Navigation Drawer
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    // User info on Navigation Drawer
    private ImageView nav_header_user_Profile;
    private TextView nav_header_user_ID;
    private TextView nav_header_user_Account;
    private FirebaseUser fbUser;

    // For Fragments
    private Fragment fragment;
    private Class fragmentClass;
    private CalendarActivity calendarFragment;
    private TimediaryFragment timediaryFragment;
    private TodoActivity todoFragment;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragment = null;

        // [START init_fragments]
        calendarFragment = new CalendarActivity();
        timediaryFragment = new TimediaryFragment();
        todoFragment = new TodoActivity();
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

        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
        //User info on Navigation Drawer


        //actionBar.setDisplayShowTitleEnabled(false);


        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frag_calendar, new CalendarActivity());
        ft.commit();*/

        //Bottom Navigation Select
        bnv.setOnNavigationItemSelectedListener(new MyItemSelectedListener());

        initLayout();
    }

    class MyItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        private final String TAG = MyItemSelectedListener.class.getSimpleName();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.Calendar:
                    fragmentClass = CalendarActivity.class;
                    Log.d(TAG, fragmentClass.toString() + " called.");
                    break;
                case R.id.TimeDiary:
                    fragmentClass = TimediaryFragment.class;
                    Log.d(TAG, fragmentClass.toString() + " called.");
                    break;
                case R.id.ToDo:
                    fragmentClass = TodoActivity.class;
                    Log.d(TAG, fragmentClass.toString() + " called.");
                    break;
                default:
                    fragmentClass = TimediaryFragment.class;
                    Log.d(TAG, fragmentClass.toString() + " called.");
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                Log.d(TAG, "fragment " + fragment.toString() + "called.");
            } catch (Exception e) {
                Log.w(TAG, "onNavigationItemSelected:FragmentChangeFailed : ", e);
                return false;
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            return true;
        }
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

        //TODO Toolbar Title Text - switch로 fragment마다 다르게
        //getSupportActionBar().setTitle("");

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);
        navigationView = (NavigationView) findViewById(R.id.nv_main_navigation_root);
        View headerView = navigationView.getHeaderView(0);

        //Set Component for User Info
        nav_header_user_Profile = (ImageView) headerView.findViewById(R.id.nav_header_main_user_Profile);
        nav_header_user_ID = (TextView) headerView.findViewById(R.id.nav_header_main_user_ID);
        nav_header_user_Account = (TextView) headerView.findViewById(R.id.nav_header_main_user_Account);

        if (fbUser != null) {


            /*if (fbUser.getPhotoUrl().toString() != "https://lh4.googleusercontent.com/-v0soe-ievYE/AAAAAAAAAAI/AAAAAAACyas/yR1_yhwBcBA/photo.jpg?sz=50"){
                nav_header_user_Profile.setImageURI(fbUser.getPhotoUrl());
            }else{
//                nav_header_user_Profile.setImageURI();
            }*/


            nav_header_user_ID.setText(fbUser.getDisplayName());

            if (fbUser.isEmailVerified())
                nav_header_user_Account.setText(fbUser.getEmail());


        } else {
            nav_header_user_Profile.setImageURI(null);
            nav_header_user_ID.setText(null);
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
