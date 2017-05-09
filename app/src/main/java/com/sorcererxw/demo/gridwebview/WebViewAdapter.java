package com.sorcererxw.demo.gridwebview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description:
 * @author: SuperNoobTao
 * @date: 2017/2/28
 */

public class WebViewAdapter extends RecyclerView.Adapter<WebViewAdapter.ViewHolder> {

    private List<String> mList = new ArrayList<>();

    private Context mContext;

    private int mScreenHeight;
    private int mScreenWidth;
    private int mSpan = 1;

    public WebViewAdapter(Context context, int screenWidth, int screenHeight,
                          RecyclerView holdingRecyclerView) {
        mContext = context;

        mScreenHeight = screenHeight;
        mScreenWidth = screenWidth;

        mHoldingRecyclerView = holdingRecyclerView;
    }

    public void setList(List<String> list, int span) {
        mList = list;
        mSpan = span;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_webview, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.mContainer.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(mScreenWidth / mSpan, mScreenHeight / mSpan);
        } else {
            layoutParams.width = mScreenWidth / mSpan;
            layoutParams.height = mScreenHeight / mSpan;
        }
        holder.mContainer.setLayoutParams(layoutParams);

//        holder.mWebView.getSettings().setBuiltInZoomControls(true);
//        holder.mWebView.getSettings().setBuiltInZoomControls(false);
        holder.mWebView.getSettings().setJavaScriptEnabled(true);
        holder.mWebView.getSettings().setUserAgentString(
                "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0");
        holder.mWebView.setInitialScale(1);
//        holder.mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        holder.mWebView.getSettings().setLoadWithOverviewMode(true);
        holder.mWebView.getSettings().setUseWideViewPort(true);

        holder.mWebView.setWebViewClient(new WebViewClient());
        holder.mWebView.loadUrl(mList.get(position));

//        holder.mWebView.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    if (mHoldingRecyclerView != null) {
//                        mHoldingRecyclerView.requestDisallowInterceptTouchEvent(true);
//                    } else {
//                        Timber.d("null parent");
//                    }
//                    break;
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    if (mHoldingRecyclerView != null) {
//                        mHoldingRecyclerView.requestDisallowInterceptTouchEvent(false);
//                    } else {
//                        Timber.d("null parent");
//                    }
//            }
//            return false;
//        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private RecyclerView mHoldingRecyclerView;


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.webView_item)
        WebView mWebView;

        @BindView(R.id.relativeLayout_item_container)
        RelativeLayout mContainer;

        @BindView(R.id.card_item)
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
