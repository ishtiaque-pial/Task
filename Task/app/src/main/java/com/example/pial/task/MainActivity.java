package com.example.pial.task;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView text;
    private ArrayList<String> names= new ArrayList<String>();
    private ArrayList<String> phoneNumbers= new ArrayList<String>();
    private ArrayList<Store> storage=new ArrayList<Store>();
    private ArrayList<Store> data=new ArrayList<Store>();
    private SharedPreferences sharedPreferences;
    private StoreListAdapter storeListAdapter;
    private Button btnLoadMore;
    private static int flag=0,count=0;


    private static final int PERMS_REQUEST_CODE = 123;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        text= (TextView) findViewById(R.id.name);
        recyclerView= (RecyclerView) findViewById(R.id.list_itemmm);
        if (hasPermissions()) {
            sharedPreferences = getSharedPreferences("Task", MODE_PRIVATE);
            boolean initialized = sharedPreferences.getBoolean("initialized", false);

            if (initialized == false) {
                getNumber(this.getContentResolver());
            }

            StoreManeger storeManeger = new StoreManeger(this);
            storage = storeManeger.getAllContact(10);
            for (int i=0;i<storage.size();i++)
            {
                data.add(storage.get(i));
            }
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(mLayoutManager);
            count=sharedPreferences.getInt("count",0);
            StoreListAdapter adapter=new StoreListAdapter(data,MainActivity.this,recyclerView,linearLayoutManager,flag,count);
            recyclerView.setAdapter(adapter);
        }

        else {
            requestPerms();
        }

    }


    public void getNumber(ContentResolver cr)
    {
        StoreManeger storeManeger=new StoreManeger(this);
        Store store;
        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
        while (phones.moveToNext())
        {
            String namee=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumberr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            names.add(namee);
            phoneNumbers.add(phoneNumberr);

        }
        phones.close();
        long result=storeManeger.addContact(names,phoneNumbers);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("count",names.size());
        editor.putBoolean("initialized", true);
        editor.commit();
    }


    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }

    private boolean hasPermissions() {

        int res = 0;

        String[] permissions = new String[]{Manifest.permission.READ_CONTACTS};
        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMS_REQUEST_CODE:

                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed){
            sharedPreferences = getSharedPreferences("Task", MODE_PRIVATE);
            boolean initialized = sharedPreferences.getBoolean("initialized", false);
            if (initialized == false) {
                getNumber(this.getContentResolver());
            }

            StoreManeger storeManeger = new StoreManeger(this);
            storage = storeManeger.getAllContact(10);
            for (int i=0;i<storage.size();i++)
            {
                data.add(storage.get(i));
            }
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager mLayoutManager = linearLayoutManager;
            recyclerView.setLayoutManager(mLayoutManager);
            count=sharedPreferences.getInt("count",0);
            StoreListAdapter adapter=new StoreListAdapter(data,MainActivity.this,recyclerView,linearLayoutManager,flag,count);
            recyclerView.setAdapter(adapter);

        }
        else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Contact Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


