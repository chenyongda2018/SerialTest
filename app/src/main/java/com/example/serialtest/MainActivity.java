package com.example.serialtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.serialtest.serial.User;
import com.twitter.serial.stream.Serial;
import com.twitter.serial.stream.bytebuffer.ByteBufferSerial;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private User user = new User("zhangsanzhangsanzhangsanzhangsanzhangsanzhangsanzhangsan",
            "12345678123456781234567812345678123456781234567812345678123456781234567812345678");

    public static final int N = 10000;

    public static final Executor executor = Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.parcelable).setOnClickListener(v -> {
        });
        findViewById(R.id.serial).setOnClickListener(v -> serial());
    }

    private void serial() {
        executor.execute(()->{
            try {
                long start = System.currentTimeMillis();
                for (int i = 0; i < N; i++) {
                    final Serial serial = new ByteBufferSerial();
                    final byte[] serializedData = serial.toByteArray(user,User.SERIALIZER);
                    final User user1 = serial.fromByteArray(serializedData,User.SERIALIZER);
                }
                long end = System.currentTimeMillis();
                long cost  = end - start;
                Log.d(TAG,"Parcelable="+cost );
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}