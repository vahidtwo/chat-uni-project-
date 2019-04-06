package com.DCLXVI.filteringchat;


import android.content.Context;
import android.os.Handler;
import android.util.Log;

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
//  private DataInputStream in;
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
        Log.i("debugg", "in TCPRESEVER Thread");
        try {
          Log.i("debugg",""+serverPort);
          serverSocket = new ServerSocket(serverPort);
        }catch (IOException e) {
          e.printStackTrace();
          Log.i("reciveTest", e.toString());
          Log.e("debugg", e.toString());
        }
        Log.i("debugg", "server Socket Creat");
        client = serverSocket.accept();
        Log.i("debugg", "server Socket accept");
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//        in = new DataInputStream(client.getInputStream());
        Log.i("debugg", "server Socket data InputStream create ");

        String monitorSrt ;
        while ((monitorSrt = in.readLine()) != null) {
          Log.i("reciveTest", "in While" + monitorSrt);
          Log.i("debugg", "in While " + monitorSrt);
          String[] splitS;
          if (monitorSrt.contains(",")) {
            splitS = monitorSrt.split(",");
          } else {
            splitS = new String[]{monitorSrt};
          }
          for (String str : splitS) {

            if (str.contains("sId")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.sId) {
                packet.sId = spliteSS[1];
                Log.i("reciveTest", "sId:" + packet.sId);
                Log.i("debugg", "sId:" + packet.sId);
              }
            }

            if (str.contains("dId")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.dId) {
                packet.dId = spliteSS[1];
                Log.i("reciveTest", "dId:" + packet.dId);
                Log.i("debugg", "dId:" + packet.dId);
              }
            }
            if (str.contains("msg")) {
              String[] spliteSS = str.split(":");
              synchronized (packet.msg) {
                packet.msg = spliteSS[1];
                Log.i("reciveTest", "msg:" + packet.msg);
                Log.i("debugg", "msg:" + packet.msg);
              }
            }

          }
          try {
            Thread.sleep(100);
          } catch (Exception e) {
            Log.i("reciveTest", e.toString());
            Log.i("debugg", e.toString());
          }
        }

        this.in.close();
        client.close();
        serverSocket.close();
        Log.i("reciveTest", "all is close in normal");
        Log.i("debugg", "all is close in normal");

      }
    } catch (IOException e) {
      e.printStackTrace();
      Log.i("reciveTest", e.toString());
      Log.i("debugg", e.toString());
    }
  }
}
