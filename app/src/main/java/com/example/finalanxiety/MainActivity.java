package com.example.finalanxiety;

import android.accessibilityservice.FingerprintGestureController;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.finalanxiety.database.CardsDatabase;
import com.example.finalanxiety.database.TimelineCard;
import com.example.finalanxiety.motivation_messages.MotivationCard;
import com.example.finalanxiety.motivation_messages.MotivationDatabase;
import com.example.finalanxiety.ui.notification.NotificationFragment;
import com.example.finalanxiety.ui.timeline.TimelineFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static List<TimelineCard> all_cards;
    private static List<MotivationCard> stack;
    private AppBarConfiguration mAppBarConfiguration;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    TrackLocation trackLocation;
    private Window wind;

    public static List<TimelineCard> allCards() {
        return all_cards;
    }

    public void cardList(List<TimelineCard> all_cards) {
        String cardInformation;
        ArrayList<String> card_list = new ArrayList<>();
        if (all_cards != null) {
            for (TimelineCard card : all_cards) {
                String card_date = card.getDate();
                String card_location = card.getLocation();
                String card_severity = card.getSeverity();
                String card_comments = card.getComments();
                cardInformation = card_date + "\n" + card_location + "\n" + card_severity + "\n"
                        + card_comments;
                card_list.add(cardInformation);
                myBundle.putStringArrayList("cardList", card_list);
            }
        }
    }

    public void getSeverityGraphInformation(List<TimelineCard> cards) {
        String severity;
        ArrayList<String> severity_list = new ArrayList<>();
        if (cards != null) {
            for (TimelineCard card : cards) {
                String card_severity = card.getSeverity();
                severity_list.add(card_severity);
                myBundle.putStringArrayList("severity_list", severity_list);
            }
        }
    }

    public void getContents(List<MotivationCard> stack) {
        ArrayList<String> contents = new ArrayList<>();
        if (stack != null) {
            for (MotivationCard motiv : stack) {
                String motiv_contents = motiv.getContents();
                contents.add(motiv_contents);
                myBundle.putStringArrayList("contents", contents);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.turquoise));

        CardsDatabase.getInstance(MainActivity.this).cardAccess().getAll().observe(this, new Observer<List<TimelineCard>>() {
            @Override
            public void onChanged(List<TimelineCard> timelineCards) {
                cardList(timelineCards);
                getSeverityGraphInformation(timelineCards);
            }
        });

        MotivationDatabase.getInstance(MainActivity.this).motivationAccess().getAllMotivation().observe(this, new Observer<List<MotivationCard>>() {
            @Override
            public void onChanged(List<MotivationCard> motivationCards) {
                getContents(motivationCards);
            }
        });

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_timeline, R.id.nav_document_mood, R.id.nav_notifications, R.id.nav_profile, R.id.create_motivation)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static Bundle myBundle = new Bundle();

    public double sendLatitude() {
        trackLocation = new TrackLocation(MainActivity.this);
        double latitude = 0;

        if (trackLocation.canGetLocation()) {
            latitude = trackLocation.getLatitude();
        }
        return latitude;
    }

    public double sendLongitude() {
        trackLocation = new TrackLocation(MainActivity.this);
        double longitude = 0;

        if (trackLocation.canGetLocation()) {
            longitude = trackLocation.getLongitude();
        }
        return longitude;
    }

    public String getLocationName(double latitude, double longitude) {
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        String starter = "";
        try {
            List<Address> address_list = geo.getFromLocation(latitude,longitude,1);
            Address address = (Address) address_list.get(0);
            starter += address.getAddressLine(0) +", ";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return starter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    @Override
    public boolean onKeyDown(int key, KeyEvent event) {
        if (key == KeyEvent.KEYCODE_VOLUME_UP){
            System.out.println("Hello1");
            return false;
        } else if (key == KeyEvent.KEYCODE_VOLUME_DOWN){
            System.out.println("Hello2");
            return false;
        }
        return true;
    }

    public boolean onKeyUp(int key, KeyEvent event) {
        if (key == KeyEvent.KEYCODE_VOLUME_UP){
            return false;
        } else if (key == KeyEvent.KEYCODE_VOLUME_DOWN){
            return false;
        }
        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trackLocation.stopListener();
    }
}
