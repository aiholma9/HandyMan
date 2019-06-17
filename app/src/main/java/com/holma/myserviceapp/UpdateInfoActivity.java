package com.holma.myserviceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.holma.myserviceapp.Common.Common;
import com.holma.myserviceapp.Retrofit.IMyServiceAPI;
import com.holma.myserviceapp.Retrofit.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateInfoActivity extends AppCompatActivity {

    IMyServiceAPI myServiceAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;

    @BindView(R.id.edit_user_name)
    EditText edt_user_name;
    @BindView(R.id.edit_address)
    EditText edt_address;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        ButterKnife.bind(this);

        init();
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home);{
            finish();
            return true;
        }
        //return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar.setTitle("Update Information");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(Account account) {
                        compositeDisposable.add(
                                myServiceAPI.updateUserInfo(Common.API_KEY,
                                        account.getPhoneNumber().toString(),
                                        edt_user_name.getText().toString(),
                                        edt_address.getText().toString(),
                                        account.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(updateUserModel -> {

                                    if (updateUserModel.isSuccess()){ // If user has been update, just refresh again
                                        compositeDisposable.add(myServiceAPI.getUser(Common.API_KEY, account.getId())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(userModel -> {

                                                            if (userModel.isSuccess()){ // If user is available in database
                                                                Common.currentUser = userModel.getResult().get(0);
                                                                Intent intent = new Intent(UpdateInfoActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }

                                                            else { // If user is not in the database, start MainActivity for register
                                                                Toast.makeText(UpdateInfoActivity.this, "[GET USER RESULT] "+userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }

                                                            dialog.dismiss();

                                                        },
                                                        throwable -> {
                                                            dialog.dismiss();
                                                            Toast.makeText(UpdateInfoActivity.this, "[GET USER] "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }));
                                    }

                                    else {
                                        Toast.makeText(UpdateInfoActivity.this, "[UPDATE USER API RETURN] "+updateUserModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                            dialog.dismiss();
                                        },
                                        throwable -> {
                                            dialog.dismiss();
                                            Toast.makeText(UpdateInfoActivity.this, "[UPDATE USER API] "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }));
                    }

                    @Override
                    public void onError(AccountKitError accountKitError) {
                        Toast.makeText(UpdateInfoActivity.this, "[Account Kit Error]" + accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myServiceAPI = RetrofitClient.getInstance(Common.BASE_URL).create(IMyServiceAPI.class);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
