package com.enkwave_p30_terminal.p30_terminal_plugin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.CustomLoader;
import com.enkwave_p30_terminal.p30_terminal_plugin.pos_implementation.PrepImplementation;
import com.enkwave_p30_terminal.p30_terminal_plugin.terminal_process.KeyExChange;
import com.enkwave_p30_terminal.p30_terminal_plugin.terminal_process.Purchase;
import com.whty.smartpos.tysmartposapi.ITYSmartPosApi;

import java.util.ArrayList;
import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/** P30TerminalPlugin */
public class P30TerminalPlugin implements FlutterPlugin, MethodCallHandler , ActivityAware, PluginRegistry.ActivityResultListener {
  public static ITYSmartPosApi smartPosApi;
  private MethodChannel channel;
  private Activity activity;
  private CustomLoader customLoader;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "p30_terminal_plugin");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if(call.method.equals("prep")){
      String terminalID = (String)call.arguments;
      new KeyExChange(activity, terminalID).run();
    } else if(call.method.equals("pay")){
      HashMap<String, String> transactionData = call.argument("transactionData");
      System.out.println(transactionData + " =====================>");

      new Purchase(activity,transactionData ).payNow();
      result.success(null);

    }else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
    //adding SDK
    smartPosApi = ITYSmartPosApi.get(activity);

    //loader
    customLoader = new CustomLoader(activity);


    //add permission
    initPermission();

  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();

  }

  @Override
  public void onDetachedFromActivity() {

  }

  @Override
  public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    return false;
  }

  private void initPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      String[] permissions = {
              Manifest.permission.READ_EXTERNAL_STORAGE,
              Manifest.permission.WRITE_EXTERNAL_STORAGE
      };

      ArrayList<String> toApplyList = new ArrayList<String>();
      for (String perm : permissions) {
        if (PackageManager.PERMISSION_GRANTED != activity.checkSelfPermission(perm)) {
          toApplyList.add(perm);
        }
      }
      String[] tmpList = new String[toApplyList.size()];
      if (!toApplyList.isEmpty()) {
       activity.requestPermissions(toApplyList.toArray(tmpList), 100);
      }
    }
  }
}
