package com.example.projectsem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class razro_pay1 extends AppCompatActivity  implements PaymentResultListener {
    Button paybn;
    TextView paystat;
    TextView textView3;
    String placeId = "", imageurl;
    String placename,amount,uname,mobile,checkin,pic;
    FirebaseAuth mAuth;
    String s;
    String date;
    FirebaseUser firebaseUser;
    DatabaseReference post1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.razro_pay);

        Checkout.preload(getApplicationContext());
        paybn = findViewById(R.id.paybtn);
        textView3=findViewById(R.id.paydefault);
        post1=FirebaseDatabase.getInstance().getReference().child("BookPost");
        placeId = getIntent().getStringExtra("pId");
        placename=getIntent().getStringExtra("place");
        amount=getIntent().getStringExtra("amount");
        uname=getIntent().getStringExtra("uname");
        mobile=getIntent().getStringExtra("mobile");
        checkin=getIntent().getStringExtra("checkin");
        pic=getIntent().getStringExtra("pic");
//        Toast.makeText(razro_pay1.this,""+placeId,Toast.LENGTH_SHORT).show();
        getPlaceDetails(placeId);


        // paystat = findViewById(R.id.paystatus);
        paybn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
s=textView3.getText().toString();

 PaymentNow(s);

            }
        });
    }

    private void getPlaceDetails(String placeID)
    {
//        Toast.makeText(this, "toast msg"+placeID, Toast.LENGTH_SHORT).show();
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Checkin");
        homeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    if (dataSnapshot.child("pIid").getValue() != null) {
                        if (Objects.requireNonNull(dataSnapshot.child("pIid").getValue()).toString().equals(placeID)) {
                            Model2 tour = dataSnapshot.getValue(Model2.class);
//                            Toast.makeText(razro_pay1.this, ""+amount, Toast.LENGTH_SHORT).show();
                            textView3.setText(tour.getTamnt());

                            break;
                        }
                    }
//                    Toast.makeText(razro_pay1.this, "" + dataSnapshot.child("pIid").getValue(), Toast.LENGTH_SHORT).show();
                }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}

    public String getCurrentUserId() {
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            return firebaseUser.getEmail();
        } else {
            return null;
        }
    }
    private void UpdateDatabase() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MM:dd:yyyy");
        date = currentDate.format(calendar.getTime());
//Toast.makeText(razro_pay1.this,""+checkin,Toast.LENGTH_SHORT).show();
        String userId=getCurrentUserId();

        HashMap<String, Object> map = new HashMap<>();
        map.put("pIid", placeId);
        map.put("tplace",placename);
//        map.put("tdesc", addesc);
        map.put("tamnt",amount);
//        map.put("tacti", adacti);
//        map.put("tmeals",adMeals);
        map.put("tuname",uname);
        map.put("tmobile",mobile);
//        map.put("tnumperson",model.getTnumperson());
        map.put("tcheckin",checkin);
        map.put("user",userId);
        map.put("tpic",pic);
        map.put("date",date);
//            key=post1.push().getKey();
        post1.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
//                    pg.dismiss();
//                    Toast.makeText(razro_pay1.this, "Stored", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(placedetails.this, customer2.class));
//                    Model model=new Model();
//                    Intent intent=new Intent(placedetails.this,razro_pay.class);
//                    intent.putExtra("pId", model.getpId());
//                    startActivity(intent);

                } else {
//                    pg.dismiss();
                    Toast.makeText(razro_pay1.this, "Failed" , Toast.LENGTH_SHORT).show();
                }
            }

        });
//

        //startActivity(new Intent(placedetails.this,dummy.class));
    }

    private void PaymentNow(String amount) {
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_M5lpO1nTJfHAxF");
        checkout.setImage(R.drawable.ic_launcher_background);
        double finalAmount = Float.parseFloat(amount) * 100;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Travel bees");
            options.put("description", "pay Charges");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAmount + "");
            options.put("prefill.email", "shravyabhandary124@gmail.com ");
            options.put("prefill.contact", "6363976103");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e("TAG", "Error in the starting Razropay Checkout", e);
//            Toast.makeText(razro_pay.this,"error",Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onPaymentSuccess(String s) {

//        Toast.makeText(getApplicationContext(), "payment Successfull!", Toast.LENGTH_SHORT).show();
        UpdateDatabase();
        startActivity(new Intent(razro_pay1.this,success.class));
        finish();
//        paystat.setText(s);






















    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "payment Failed!", Toast.LENGTH_SHORT).show();
//        paystat.setText(s);
    }
}
