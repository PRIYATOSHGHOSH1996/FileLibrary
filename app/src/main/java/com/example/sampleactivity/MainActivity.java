package com.example.sampleactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.filelibrary.Callback;
import com.filelibrary.Utils;
import com.filelibrary.exception.ActivityOrFragmentNullException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void click(View view) {
       final ImageView imageView =findViewById(R.id.image_view);
        final TextView textView =findViewById(R.id.text);
        try {
            Utils.with(this)
                    .getImageFromCamera()
                    .cropEnable(true)
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            imageView.setImageURI(uri);
                            textView.setText("image save at: "+filepath);
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();
        } catch (ActivityOrFragmentNullException e) {
            e.printStackTrace();
        }


        //  Log.e("uri = ",uri+"");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Utils.Builder.notifyPermissionsChange(requestCode,permissions, grantResults);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
             Utils.Builder.notifyActivityChange(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void click1(View view) {
        final ImageView imageView =findViewById(R.id.image_view1);
        final TextView textView =findViewById(R.id.text1);
        try {
            Utils.with(this)
                    .getImageFile()
                    .compressEnable(true)
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            imageView.setImageURI(uri);
                            textView.setText("image save at: "+filepath);
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();
        } catch (ActivityOrFragmentNullException e) {
            e.printStackTrace();
        }
    }

    public void click2(View view) {
        final TextView textView =findViewById(R.id.text2);
        try {
            Utils.with(this)
                    .getFile()
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            textView.setText("selected file path is: "+filepath);
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();
        } catch (ActivityOrFragmentNullException e) {
            e.printStackTrace();
        }

    }
}
