package com.dclxvi.vahid.chat.tcpConnection;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.dclxvi.vahid.chat.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPresive implements Runnable {
  private ServerSocket serverSocket;

  private int serverPort;
  private Handler handler;
  private Context context;
  private BufferedReader in;
  private Socket client;

  //constractor
  public TCPresive(int serverPort, Context context, Handler handler) {
    this.handler = handler;
    this.context = context;
    this.serverPort = serverPort;
  }

  @Override
  public void run() {
    try {
      while (true) {
        serverSocket = new ServerSocket(serverPort);
        client = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String monitorSrt = "";
        while ((monitorSrt = in.readLine()) != null) {
          Log.i("reciveTest", ""+monitorSrt);
          String[] splitS;
          if (monitorSrt.contains(",")) {
            splitS = monitorSrt.split(",");
          } else {
            splitS = new String[]{monitorSrt};
          }
          for (String str : splitS) {

            if (str.equals("sId")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.sId) {
                packet.sId = spliteSS[1];
                Log.i("reciveTest", "sId:"+packet.sId);
              }
            }

            if (str.equals("dId")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.dId) {
                packet.dId = spliteSS[1];
                Log.i("reciveTest", "dId:"+packet.dId);
              }
            }
            if (str.equals("msg")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.msg) {
                packet.msg = spliteSS[1];
                Log.i("reciveTest", "msg:"+packet.msg);
              }
            }

          }
          try {
            Thread.sleep(100);
          } catch (Exception e) {
            Log.i("reciveTest", e.toString());
          }
        }

        this.in.close();
        client.close();
        serverSocket.close();
        Log.i("reciveTest", "all is close in normal");

      }
    } catch (IOException e) {
      e.printStackTrace();
      Log.i("reciveTest", e.toString());
    }
  }
}
