package com.example.projectsem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    FirebaseAuth mAuth;
EditText regemail;
EditText regpass;
TextView ac;
ProgressDialog dialog;
String validation="^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=]).{6,}$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ac=findViewById(R.id.have_ac);
        Button btn=findViewById(R.id.btn);
        dialog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        btn.setOnClickListener(v->{
            createuser();
        });
        ac.setOnClickListener(v->{
           startActivity(new Intent(register.this,login.class));
        });
    }
    public void createuser()
    {
        EditText email=findViewById(R.id.btnlog);
        EditText pass=findViewById(R.id.pass1);
        String em=email.getText().toString();
        String pa=pass.getText().toString();
        if(TextUtils.isEmpty(em))
        {
            email.setError("Email cannot be empty");
            email.requestFocus();
        }
        else if(TextUtils.isEmpty(pa))
        {
            pass.setError("Password cannot be empty");
            pass.requestFocus();
        }
       else if(!pa.matches(validation))
        {
            Toast.makeText(register.this,"Password should contain 6 character with one uppercase letter ,one digit and one special character",Toast.LENGTH_LONG).show();
//            pass.setText("Password should contain 6 character with one uppercase letter ,one digit and one special character");
        }
        else{

            mAuth.createUserWithEmailAndPassword(em,pa).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                @Override
                public void onComplete(@NonNull Task<AuthResult> task){
                    User user= new User(em,pa);
                    if(task.isSuccessful()){
                        Toast.makeText(register.this,"User Registered successfully",Toast.LENGTH_LONG).show();
                        dialog.setTitle("Sign Up");
                        dialog.setMessage("Signing up");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        startActivity(new Intent(register.this,onboarding1.class));
                        }
                    else{
                        Toast.makeText(register.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }}
                });

}}}


