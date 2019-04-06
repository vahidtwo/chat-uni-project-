package com.DCLXVI.filteringchat;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPsend extends AsyncTask<Void, Void, Void> {
  String ip = "192.168.88.225";
  String massage;
  int port = 16662;


  private Socket socket;
  private PrintWriter pw;

  @Override
  protected Void doInBackground(Void... params) {
    try {
      Log.i("debuggsend","send socket in doInBackground");
      Log.i("debuggsend","ip:"+ip+" port:"+port+" "+massage);

      socket = new Socket(ip, port);
      Log.i("debuggsend","send socket socket creat");
      pw = new PrintWriter(socket.getOutputStream());
      Log.i("debuggsend","send socket msg is "+massage);
      pw.write(massage);
      Log.i("debuggsend","send socket pw write");
      pw.flush();
      Log.i("debuggsend","send socket flush");
      pw.close();
      socket.close();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void start() {
    this.execute();
    Log.i("debuggsend", "send THread start");
  }

  public TCPsend setIP(String IP) {
    this.ip = IP;
    return this;
  }

  public TCPsend setPort(int port) {
    this.port = port;
    return this;
  }

  public TCPsend setMessage(String massage) {
    this.massage = massage;
    return this;
  }
  public TCPsend setMessage(boolean massage) {
    this.massage = ""+massage;
    return this;
  }
}