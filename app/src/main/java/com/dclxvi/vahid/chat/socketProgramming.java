package com.dclxvi.vahid.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dclxvi.vahid.chat.tcpConnection.TCPsend;


public class socketProgramming extends AppCompatActivity {

  public static int sId;
  public static int dId;
  public static String serverIp;

  private EditText edt_MyId;
  private EditText edt_ToId;
  private EditText edt_ip;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.intro);
    Button btnConnection = findViewById(R.id.btnConnect);
    edt_ToId =  findViewById(R.id.edt_endId);
    edt_MyId =  findViewById(R.id.edtMyId);
    edt_ip =  findViewById(R.id.edtIp);

    btnConnection.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        serverIp = edt_ip.getText().toString();
        Log.i("test","serverIp"+ serverIp);

        sId = Integer.valueOf(edt_MyId.getText().toString());
        dId = Integer.valueOf(edt_ToId.getText().toString());


        Intent intent = new Intent(socketProgramming.this, MainActivity.class);
        intent.putExtra("port", sId);
        intent.putExtra("dId", dId);
        intent.putExtra("serverIp", serverIp);

        new TCPsend().setIP(serverIp).setMessage("sId:"+ sId).setPort(666).start();


        startActivity(intent);
        finish();

        Log.w("test","sId: "+ sId +" and serverIp: "+ serverIp +" and to sId is "+ dId);

      }

    });
  }
}
