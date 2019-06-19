package com.holma.myserviceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.accountkit.AccountKit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.holma.myserviceapp.Adapter.MyServiceAdapter;
import com.holma.myserviceapp.Adapter.ServiceSliderAdapter;
import com.holma.myserviceapp.Common.Common;
import com.holma.myserviceapp.EventBus.ServiceLoadEvent;
import com.holma.myserviceapp.Model.Service;
import com.holma.myserviceapp.Retrofit.IMyServiceAPI;
import com.holma.myserviceapp.Retrofit.RetrofitClient;
import com.holma.myserviceapp.Services.PicassoImageLoadingService;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt_user_name;
    TextView txt_user_phone;

    @BindView(R.id.banner_slider)
    Slider banner_slider;
    @BindView(R.id.recycler_services)
    RecyclerView recycler_services;

    IMyServiceAPI myServiceAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txt_user_name = (TextView)headerView.findViewById(R.id.txt_user_name);
        txt_user_phone = (TextView)headerView.findViewById(R.id.txt_user_phone);

        txt_user_name.setText(Common.currentUser.getName());
        txt_user_phone.setText(Common.currentUser.getUserPhone());

        init();
        initView();

        loadService();

    }

    private void loadService() {
        dialog.show();

        compositeDisposable.add(
                    myServiceAPI.getService(Common.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(serviceModel -> {

                            EventBus.getDefault().post(new ServiceLoadEvent(true, serviceModel.getResult()));

                                },
                                throwable -> {

                            EventBus.getDefault().post(new ServiceLoadEvent(false, throwable.getMessage()));
                                })
        );
    }

    private void initView() {
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_services.setLayoutManager(layoutManager);
        recycler_services.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

        if (id == R.id.sign_out) {
            signOut();
        }

        else if (id == R.id.nav_update_info){

        }

        else if (id == R.id.nav_order_history){

        }

        else if (id == R.id.nav_service_provider){

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut(){
        AlertDialog confirmDialog = new AlertDialog.Builder(this)
                .setTitle("Sign out")
                .setMessage("Do you really want sign out?")
                .setNegativeButton("CANCEL", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Common.currentUser = null;
                    Common.currentService = null;

                    AccountKit.logOut();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear all previous activity
                    startActivity(intent);
                    finish();
                }).create();

        confirmDialog.show();
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myServiceAPI = RetrofitClient.getInstance(Common.BASE_URL).create(IMyServiceAPI.class);

        Slider.init(new PicassoImageLoadingService());
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    /*
    REGISTER EVENT BUS
     */
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //Listen EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void processServiceLoadEvent(ServiceLoadEvent event){
        if (event.isSuccess()){
            displayBanner(event.getServiceList());
            displayService(event.getServiceList());
        }

        else {
            Toast.makeText(this, "[SERVICE LOAD] "+event.getMessage(), Toast.LENGTH_SHORT).show();
        }

        dialog.dismiss();
    }

    private void displayService(List<Service> serviceList) {
        MyServiceAdapter adapter = new MyServiceAdapter(this, serviceList);
        recycler_services.setAdapter(adapter);
    }

    private void displayBanner(List<Service> serviceList) {
        banner_slider.setAdapter(new ServiceSliderAdapter(serviceList));
    }

}
