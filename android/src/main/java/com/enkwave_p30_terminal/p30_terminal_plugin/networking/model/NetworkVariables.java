package com.enkwave_p30_terminal.p30_terminal_plugin.networking.model;

public class NetworkVariables {
    public NetworkVariables() {
    }
  public static final String ip = "core.medusang.com";
  public  static   final String port = "8080";
 public static   final String ssl = "false";
    public static   final String compKey1 = "AD9160FC7946955E7BC4E27A8D10C0DA";
    public static   final String compKey2 = "BC986FA24F0A4BAFEE42C81910D8EF52";


    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getSsl() {
        return ssl;
    }
}
