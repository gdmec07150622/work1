package com.example.socketclienttest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button but1=(Button)findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(mrunnable).start();
            }
        });
    }

    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            String result=bundle.getString("result");
            TextView tv2=(TextView)findViewById(R.id.tv2);
            tv2.setText(result);
            Log.d("result",result);
        }
    };

    Runnable mrunnable=new Runnable() {
        @Override
        public void run() {
            try{
                Log.d("a","sadas");
                Socket s=new Socket("10.10.11.49",2006);
                Log.d("a","client connected");
                InputStream is=s.getInputStream();
                OutputStream os=s.getOutputStream();
                PrintStream ps=new PrintStream(os);
                ps.println("message from client");
                DataInputStream dis=new DataInputStream(is);
                String request=dis.readLine();
                Bundle bundle=new Bundle();
                bundle.putString("result",request);
                Message msg=new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);


            }catch(Exception e){
               e.printStackTrace();
            }
        }
    };

}
