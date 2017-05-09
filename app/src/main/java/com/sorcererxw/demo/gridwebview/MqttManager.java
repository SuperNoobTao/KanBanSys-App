package com.sorcererxw.demo.gridwebview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/4/11
 */

public class MqttManager {

    private static final String HOST = "tcp://iot.eclipse.org:1883";

    private Handler mHandler;

    private MqttClient mClient;

    private MqttConnectOptions mOptions;

    private ScheduledExecutorService mScheduler;

    protected MqttManager(final Context context, String deviceId, MqttCallback callback) {

        init(deviceId, callback);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
//                    Toast.makeText(context, (String) msg.obj,
//                            Toast.LENGTH_SHORT).show();
                } else if (msg.what == 2) {
                    System.out.println("连接成功");
//                    Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show();
                    try {
                        mClient.subscribe(deviceId, 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (msg.what == 3) {
//                    Toast.makeText(context, "连接失败，系统正在重连", Toast.LENGTH_SHORT).show();
                    System.out.println("连接失败，系统正在重连");
                }
            }
        };

        startReconnect();

    }

    public void startReconnect() {
        mScheduler = Executors.newSingleThreadScheduledExecutor();
        mScheduler.scheduleAtFixedRate(() -> {
            if (!mClient.isConnected()) {
                connect();
            }
        }, 0, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    private void init(String deviceId, MqttCallback callback) {
        try {
            //host为主机名，111为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            mClient = new MqttClient(HOST, deviceId, new MemoryPersistence());
            //MQTT的连接设置
            mOptions = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            mOptions.setCleanSession(true);

            // 设置超时时间 单位为秒
            mOptions.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            mOptions.setKeepAliveInterval(20);
            //设置回调
            mClient.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    //连接丢失后，一般在这里面进行重连
                    System.out.println("connectionLost----------");
                    callback.connectionLost(cause);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    //publish后会执行到这里
                    System.out.println("deliveryComplete---------"
                            + token.isComplete());
                    callback.deliveryComplete(token);
                }

                @Override
                public void messageArrived(String topicName, MqttMessage message)
                        throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    System.out.println("messageArrived----------");
                    callback.messageArrived(topicName, message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        new Thread(() -> {
            try {
                mClient.connect(mOptions);
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
                Message msg = new Message();
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    public void close() throws MqttException {
        mScheduler.shutdown();
        mClient.close();
    }
}
