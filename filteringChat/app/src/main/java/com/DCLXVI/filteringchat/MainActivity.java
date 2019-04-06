package com.DCLXVI.filteringchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

  EditText edtIn;
  Button btnSend;
  TextView txtMe;
  TextView txtOther;

  Thread server;
  Handler handler;
  Intent intent;
  Context context;

  private int sId;
  private int port=16661;
  private int endId;

  private String ip;
  private String oldmsg="";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    context = getApplicationContext();
    handler = new Handler();

    intent = getIntent();


    edtIn = findViewById(R.id.edt_In);
    btnSend = findViewById(R.id.btnSend);
    txtMe = findViewById(R.id.txtMe);
    txtOther = findViewById(R.id.txtOther);


    if (intent.hasExtra("sId") && intent.hasExtra("serverIp") && intent.hasExtra("dId")) {
      Log.i("debugg","in if line 51");
      sId = intent.getIntExtra("sId", 1);
      endId = intent.getIntExtra("dId", 2);
      ip = intent.getStringExtra("serverIp");
    }

    TCPresive serverRunnable = new TCPresive(port, context, handler);
    try {

      server = new Thread(serverRunnable);
      server.start();
      Log.i("debugg","server started");

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        try {

          String text = edtIn.getText().toString();
          new TCPsend().setIP(ip).setMessage("msg:" + text + ",sId:" + sId + ",dId:" + endId).setPort(port).start();
          String tmp = txtMe.getText().toString();
          tmp += "\n" + "sId: " + sId + "\t\t" + text;
          txtMe.setText(tmp);
          edtIn.setText("");
        } catch (Exception ex) {
          ex.printStackTrace();
        }

      }
    });

    new Thread(new Runnable() {
      @Override
      public void run() {
        Log.i("debuggUI","in thread");
        while (true){

          try {
            Thread.sleep(30);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          if (oldmsg!=packet.msg)
            synchronized (packet.dId){
              synchronized (packet.msg){
                oldmsg=packet.msg;

                handler.post(new Runnable() {
                  @Override
                  public void run() {
                    String tmp = txtOther.getText().toString();
                    tmp += "\n" + "sId:" + packet.dId + " say: " + packet.msg;
                    txtOther.setText(tmp);
                    Log.i("debuggUI","msg  set to UI ::::"+tmp);
                  }
                });

              }}}


      }
    }).start();

  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    Intent i = new Intent(MainActivity.this,socketProgramming.class);
    startActivity(i);
    finish();

  }
}
