package com.example.urvish.inappbillingdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BillingClient mBillingClient;
    private boolean ableToReq = false;
    private List<String> purchaseTokenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
        purchaseTokenList = new ArrayList<>();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                Log.d(TAG, "onBillingSetupFinished: " + responseCode);
                if (responseCode == BillingClient.BillingResponse.OK) {
                    ableToReq = true;

                }

            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(MainActivity.this, "Not able to connect with client", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK) {
            if (purchases != null) {
                Toast.makeText(this, "Item purchased" + purchases, Toast.LENGTH_SHORT).show();
                purchaseTokenList.add(purchases.get(0).getPurchaseToken());
            } else {
                Toast.makeText(this, "Unable to purchase the items", Toast.LENGTH_SHORT).show();
            }
        } else if (responseCode == 7) {
            Toast.makeText(this, "Unable to purchase the items", Toast.LENGTH_SHORT).show();
        }
    }

    public void purchaseItem(View view) {
        List skuList = new ArrayList<>();
        skuList.add("android.test.purchased");
        SkuDetailsParams.Builder skuBuilder = SkuDetailsParams.newBuilder();
        skuBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                Log.d(TAG, "onSkuDetailsResponse: " + responseCode);
                Log.d(TAG, "onSkuDetailsResponse: " + skuDetailsList.size() + "" + skuDetailsList);
                for (final SkuDetails skuDetails : skuDetailsList) {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSku(skuDetails.getSku()).setType(BillingClient.SkuType.INAPP).build();
                    int res = mBillingClient.launchBillingFlow(MainActivity.this, flowParams);
                    Log.d(TAG, "onSkuDetailsResponse: " + res);
                    if (res == 7) {
                        Toast.makeText(MainActivity.this, "Item is already purchased", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }

    public void canlcedPurchase(View view) {
        List skuList = new ArrayList<>();
        skuList.add("android.test.canceled");
        SkuDetailsParams.Builder skuBuilder = SkuDetailsParams.newBuilder();
        skuBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                Log.d(TAG, "onSkuDetailsResponse: " + responseCode);
                Log.d(TAG, "onSkuDetailsResponse: " + skuDetailsList.size() + "" + skuDetailsList);
                for (final SkuDetails skuDetails : skuDetailsList) {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSku(skuDetails.getSku()).setType(BillingClient.SkuType.INAPP).build();
                    int res = mBillingClient.launchBillingFlow(MainActivity.this, flowParams);
                    Log.d(TAG, "onSkuDetailsResponse: " + res);

                }
            }
        });
    }

    public void purchaseUnavailble(View view) {
        List skuList = new ArrayList<>();
        skuList.add("android.test.item_unavailable");
        SkuDetailsParams.Builder skuBuilder = SkuDetailsParams.newBuilder();
        skuBuilder.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        mBillingClient.querySkuDetailsAsync(skuBuilder.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                Log.d(TAG, "onSkuDetailsResponse: " + responseCode);
                Log.d(TAG, "onSkuDetailsResponse: " + skuDetailsList.size() + "" + skuDetailsList);
                for (final SkuDetails skuDetails : skuDetailsList) {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSku(skuDetails.getSku()).setType(BillingClient.SkuType.INAPP).build();
                    int res = mBillingClient.launchBillingFlow(MainActivity.this, flowParams);
                    Log.d(TAG, "onSkuDetailsResponse: " + res);

                }
            }
        });
    }


    public void consumeItem(View view) {
        Log.d(TAG, "consumeItem: " + purchaseTokenList.size());
        for (String purchaseToken : purchaseTokenList) {

            mBillingClient.consumeAsync(purchaseToken, new ConsumeResponseListener() {
                @Override
                public void onConsumeResponse(int responseCode, String purchaseToken) {
                    if (responseCode == BillingClient.BillingResponse.OK) {
                        Toast.makeText(MainActivity.this, "consumed item", Toast.LENGTH_SHORT).show();
                        purchaseTokenList.remove(purchaseToken);
                        Log.d(TAG, "onConsumeResponse: " + purchaseTokenList.size());
                    }
                }
            });
        }
    }
}
