package com.DCLXVI.filteringchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;


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
        Log.i("debugg","serverIp"+ serverIp);

        sId = Integer.valueOf(edt_MyId.getText().toString());
        dId = Integer.valueOf(edt_ToId.getText().toString());


        Intent intent = new Intent(socketProgramming.this, MainActivity.class);
        intent.putExtra("sId", sId);
        intent.putExtra("dId", dId);
        intent.putExtra("serverIp", serverIp);
          JSONObject jj = new JSONObject();
          try {
              jj.put("sId",sId);
          } catch (JSONException e) {
              e.printStackTrace();
          }
          new TCPsend().setIP(serverIp).setMessage(jj.toString()).setPort(16661).start();


        startActivity(intent);
        finish();

        Log.i("debugg","sId: "+ sId +" and serverIp: "+ serverIp +" and to dId is "+ dId);

      }

    });
  }
}
