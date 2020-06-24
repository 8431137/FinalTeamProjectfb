package com.sangmyung.teamprojectfb.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sangmyung.teamprojectfb.R;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class PaymentActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    Context mContext;

    // create new Person
    private BillingClient mBillingClient;
    private DatabaseReference dataReference;
    private String tempuser;
    private int deposit_1000;
    private int deposit_5000;
    SkuDetails skuDetails1000, skuDetails5000;
    String skuID1000 = "deposit_1000", skuID5000 = "deposit_5000";  //제품 ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        dataReference= FirebaseDatabase.getInstance().getReference();
        tempuser=null;
        deposit_1000=0;
        deposit_5000=0;
        mContext = PaymentActivity.this;
        mBillingClient = BillingClient.newBuilder(mContext).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    // The billing client is ready. You can query purchases here.
                    List<String> skuList = new ArrayList<>();
                    skuList.add(skuID1000);
                    skuList.add(skuID5000);
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                            // Process the result.
                            if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();

                                    if(skuID1000.equals(sku)) {
                                        skuDetails1000 = skuDetails;
                                    } else if(skuID5000.equals(sku)) {
                                        skuDetails5000 = skuDetails;
                                    }
                                }
                            }
                        }});
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    /**
     * Handle a callback that purchases were updated from the Billing library
     */
    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    private void doBillingFlow(SkuDetails skuDetails) {
        BillingFlowParams flowParams;
        int responseCode;

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        responseCode = mBillingClient.launchBillingFlow(PaymentActivity.this, flowParams);

        /*if(responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
            onPurchasesUpdated(BillingClient.BillingResponse.OK, purchasesResult.getPurchasesList());
        }*/
    }

    private void handlePurchase(Purchase purchase) {
        String purchaseToken;
        purchaseToken = purchase.getPurchaseToken(); //원래는 이거 쓰는게 맞음
        //purchaseToken = "inapp:"+getPackageName()+":android.test.purchased";//purchase 테스트
        mBillingClient.consumeAsync(purchaseToken, consumeListener);
    }

    ConsumeResponseListener consumeListener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
            if (responseCode == BillingClient.BillingResponse.OK) {
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.
                tempuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(deposit_1000!=0){
                    dataReference.child("deposit").child(tempuser).setValue(deposit_1000);
                }
                if(deposit_5000!=0){
                    dataReference.child("deposit").child(tempuser).setValue(deposit_5000);
                }

            }
        }
    };

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id) {
                case R.id.btn1000:
                    doBillingFlow(skuDetails1000);
                    deposit_1000=1000;
                    break;

                case R.id.btn5000:
                    doBillingFlow(skuDetails5000);
                    deposit_5000=5000;
                    break;

            }
        }
    };
}

