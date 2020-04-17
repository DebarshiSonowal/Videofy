package com.deb.videofy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private Button startpayment, mButton;
    private EditText orderamount;
    private String TAG =" main";
    paymentdetails paymentdetails;
//RazorpayClient mRazorpayClient = new RazorpayClient("rzp_test_xjNbwCnq3YGbGA","VoMASMVNXz4niYSDX4xyeYCh");
    String uid;
    DatabaseReference local =  FirebaseDatabase.getInstance().getReference();
    FirebaseUser mUser;
    JsonObject pre = new JsonObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        mUser = FirebaseAuth.getInstance().getCurrentUser();
//        uid = mUser.getUid();
        startpayment = (Button) findViewById(R.id.startpayment);
        orderamount = (EditText) findViewById(R.id.orderamount);
        mButton = findViewById(R.id.button3);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    RazorpayClient razorpay = new RazorpayClient("rzp_test_xjNbwCnq3YGbGA", "VoMASMVNXz4niYSDX4xyeYCh");
                    JSONObject options = new JSONObject();
                    orderdetails orderdetails = new orderdetails("5000","INR","ADQD",true);
                    options.put("amount", 5000);
                    options.put("currency", "INR");
                    Order order = razorpay.Orders.create(options);
                    JSONObject jsonObject = new JSONObject(String.valueOf(order));
                    String id = jsonObject.getString("id");
                    local.child("Orders").child(id).setValue(orderdetails);
                } catch (RazorpayException | JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        startpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderamount.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Amount is empty", Toast.LENGTH_LONG).show();
                }else {
                    startPayment();
                }
            }
        });


    }
    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;
        final Checkout co = new Checkout();

        try {
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", 50000); // amount in the smallest currency unit
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "order_rcptid_11");
//            orderRequest.put("payment_capture", true);
//
//            Order order = mRazorpayClient.Orders.create(orderRequest);
            customerdata customerdata = new customerdata();
            JSONObject options = new JSONObject();

            options.put("name", "Video4U");
            customerdata.setName("Video4U");
            options.put("description", "App Payment");
            customerdata.setDescription("App Payment");
            options.put("order_id","order_EfPG5vzHmdoWXg");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            customerdata.setImage("https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
            options.put("currency", "INR");

            customerdata.setCurrency("INR");
            String payment = orderamount.getText().toString();
            options.put("payment_capture",true);
//            Order order = razorpay.Orders.create(orderRequest);
            // amount is in paise so please multiple it by 100
            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
            double total = Double.parseDouble(payment);
            total = total * 100;
            options.put("amount", total);
//            options.put("order_id", "order_9A33XWu170gUtm");
            customerdata.setAmount((int) total);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "awsdad@gmail.com");

            preFill.put("contact", "8638372157");
            options.put("prefill", preFill);
            co.open(activity, options);

            Checkout.clearUserData(this);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        try {
            Log.d(TAG, " payment successfull "+ s);
            Log.d(TAG,s);
            Toast.makeText(this, "Payment successfully done! " +s, Toast.LENGTH_SHORT).show();
            paymentdetail paymentdetail = new paymentdetail(paymentData.getOrderId(),paymentData.getPaymentId(),paymentData.getSignature());
            paymentdetails paymentdetails = new paymentdetails();
            paymentdetails.setEmail("awsdad@gmail.com");
            paymentdetails.setPhone("8638372157");
            paymentdetails.setCurrency("INR");
            paymentdetails.setOrder_id("order_EfPG5vzHmdoWXg");
            String payment = orderamount.getText().toString();
            double total = Double.parseDouble(payment);
            total = total * 100;
            paymentdetails.setAmount( (int) total);
//        JSONObject opt = new JSONObject();
//        String order = paymentData.getOrderId();
//        try {
//            opt.put("razorpay_order_id",order);
//            opt.put("razorpay_payment_id",paymentData.getPaymentId());
//            opt.put("razorpay_signature",paymentData.getSignature());
            local.child("Payments").child(paymentData.getPaymentId()).setValue(paymentdetails);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }
}
