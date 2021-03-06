package com.wusy.wusylibrary.base;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DalaR on 2017/11/17.
 */

public abstract class BaseFragment extends Fragment {
    public String TAG="";
    private IntentFilter intentFilter;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        baseInit();
        findView(view);
        init();
//        showLogInfo(getActivity().getComponentName().getClassName()+"----Fragment执行onCreateView");
        return view;
    }
    public abstract int getContentViewId();
    public abstract void findView(View view);
    public abstract void init();
    private void baseInit(){
        //为TAG赋值。值为类名
        TAG=getClassName(getActivity().getComponentName().getClassName());
    }
    /**
     * Log打印info信息
     * @param info
     */
    public void showLogInfo(String info){
        Log.i(TAG,info);
    }

    /**
     * Log打印Eoor信息
     * @param error
     */
    public void showLogError(String error){
        Log.e(TAG,error);
    }
    /**
     * 打印Toast信息
     */
    public void showToast(String toast){
        Toast.makeText(getActivity(),toast,Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理广播时，添加Action动作
     * 注册广播的时候一定记得在onDestory中销毁
     * @param actions
     */
    public void addBroadcastAction(ArrayList<String> actions, BroadcastReceiver broadcastReceiver){
        //实例化广播需要的InntentFilter和MyBroadcastReceiver
        this.broadcastReceiver=broadcastReceiver;
        if(intentFilter==null) intentFilter=new IntentFilter();
        for(int i=0;i<actions.size();i++){
            intentFilter.addAction(actions.get(i));
        }
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 通过包名获取类名
     * @param packageName
     * @return
     */
    private String getClassName(String packageName){
        String [] names = packageName.split("\\.");
        return names[names.length-1];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver!=null)getActivity().unregisterReceiver(broadcastReceiver);
        showLogInfo(getActivity().getComponentName().getClassName()+"----Fragment执行OnDestory");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showLogInfo(getActivity().getComponentName().getClassName()+"----Fragment执行onDestroyView");
    }

    @Override
    public void onStop() {
        super.onStop();
        showLogInfo(getActivity().getComponentName().getClassName()+"----Fragment执行onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        showLogInfo(getActivity().getComponentName().getClassName()+"----Fragment执行onPause");
    }
}
