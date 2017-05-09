package com.sorcererxw.demo.gridwebview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.gson.Gson;
import com.mikepenz.materialize.util.UIUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.sorcererxw.demo.gridwebview.ScrollInfo.SCROLL_DIRECTION_HORIZONTAL;
import static com.sorcererxw.demo.gridwebview.ScrollInfo.SCROLL_DIRECTION_VERTICAL;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<String> mList = new ArrayList<>();

    private SpeedyGridLayoutManager mLayoutManager;

    private WebViewAdapter mAdapter;
    private int mSize = 4;

    private RxSharedPreferences mPreferences;
    private static final String DEVICE_ID_KEY = "deviceId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mPreferences = RxSharedPreferences.create(getPreferences(Context.MODE_PRIVATE));

        mLayoutManager = new SpeedyGridLayoutManager(this, 1,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new WebViewAdapter(this,
                UIUtils.getScreenWidth(this),
                UIUtils.getScreenHeight(this)
                        - UIUtils.getActionBarHeight(this)
                        - UIUtils.getStatusBarHeight(this),
                mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refresh() {
        mPreferences.getString(DEVICE_ID_KEY, "111").asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .map(deviceId -> {
                    connectMqtt(deviceId);
                    return deviceId;
                })
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<String, ObservableSource<WebData>>() {
                    @Override
                    public ObservableSource<WebData> apply(String deviceId) throws Exception {
                        return WebClient.getInstance().getUrls(deviceId);
//                        return Observable.just(WebData.sample());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WebData>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WebData webData) {
                        handleWebData(webData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Completed");
                    }
                });
    }

    private void handleWebData(WebData webData) {
        mSize = webData.getScreenNum();
        mList = new ArrayList<>(
                Stream.of(webData.getUrls())
                        .map(WebData.UrlsBean::getContent)
                        .collect(Collectors.toList())
        );
        ScrollInfo info = new ScrollInfo(false, 640, SCROLL_DIRECTION_VERTICAL);
        if (mList.size() == 0) {
            Timber.d("list size==0");
            info.setNeedScroll(false);
        } else {
            WebData.StyleVOBean styleVO = webData.getStyleVO();
            if (styleVO == null) {
                Timber.d("styleVO==null");
                info.setNeedScroll(false);
            } else {
                if (styleVO.getMode().equals("横向")) {
                    info.setScrollDirection(SCROLL_DIRECTION_HORIZONTAL);
                }
                if (styleVO.getSpeed() == 0) {
                    Timber.d("speed = 0");
                    info.setNeedScroll(false);
                } else {
                    info.setNeedScroll(true);
                    info.setScrollSpeed(styleVO.getSpeed());
                }
            }
        }
        new Handler(MainActivity.this.getMainLooper()).post(() -> init(info));
    }

    RecyclerView.OnScrollListener mOnScrollListener;

    private void init(ScrollInfo scrollInfo) {
        if (!scrollInfo.isNeedScroll()) {
            Toast.makeText(this, "do not need scrolling", Toast.LENGTH_SHORT).show();
        }
        Timber.d("screen num: " + (int) Math.sqrt(mSize));
        mLayoutManager.setSpanCount((int) Math.sqrt(mSize));
        mAdapter.setList(mList, (int) Math.sqrt(mSize));
        if (!scrollInfo.isNeedScroll()) {
            if (mOnScrollListener != null) {
                mRecyclerView.removeOnScrollListener(mOnScrollListener);
            }
        } else {
            mLayoutManager.setSpeed(scrollInfo.getScrollSpeed());
            if (scrollInfo.getScrollDirection() == SCROLL_DIRECTION_HORIZONTAL) {
                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            } else {
                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            }
            mRecyclerView.post(() -> {
                mOnScrollListener = new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                            if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                                mRecyclerView.post(() ->
                                        mRecyclerView.smoothScrollToPosition(mList.size() - 1));
                            } else {
                                mRecyclerView.smoothScrollToPosition(0);
                            }
                        }
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        }
                    }
                };
                mRecyclerView.addOnScrollListener(mOnScrollListener);

                mRecyclerView.smoothScrollToPosition(mList.size() - 1);
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refresh();
            return true;
        } else if (item.getItemId() == R.id.action_set_device_id) {
            new MaterialDialog.Builder(this)
                    .input("device id", mPreferences.getString(DEVICE_ID_KEY).get(),
                            (dialog, input) -> {
                                mPreferences.getString(DEVICE_ID_KEY).set(input.toString());
                            })
                    .show();
            return true;
        }
        return false;
    }

    private MqttManager mMqttManager;

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    private void connectMqtt(String deviceId) {
        mMqttManager = new MqttManager(this, deviceId, new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                Timber.e(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Timber.d(topic + ": " + message.toString());
                try {
                    Gson gson = new Gson();
                    handleWebData(gson.fromJson(message.toString(), WebData.class));
                } catch (Exception e) {
                    Timber.e(e);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        mMqttManager.startReconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMqttManager != null) {
            try {
                mMqttManager.close();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}