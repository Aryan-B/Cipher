package social.aryan.cipher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class chatMainActivity extends AppCompatActivity {
    private String address;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        name = findViewById(R.id.name);
        EditText ip1 = findViewById(R.id.ipaddress1);
        EditText ip2 = findViewById(R.id.ipaddress2);
        EditText ip3 = findViewById(R.id.ipaddress3);
        EditText ip4 = findViewById(R.id.ipaddress4);
        EditText ngrok = findViewById(R.id.ngrok);
        Button joinip = findViewById(R.id.joinip);
        Button ngrokbtn = findViewById(R.id.ngrokbtn);
        Button heroku = findViewById(R.id.heroku);
        Button ojoin = findViewById(R.id.otherjoin);


        TextView[] ips = {ip1, ip2, ip3, ip4};
        for (TextView currTextView : ips) {
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    TextView text = (TextView) getCurrentFocus();

                    if (text != null && (text.length() == 3||currTextView==ip3)) {
                        View next = text.focusSearch(View.FOCUS_RIGHT);
                        if (next != null) {
                            next.requestFocus();
                        }
                    }
                    if ((text.length() == 0)) {
                        View next = text.focusSearch(View.FOCUS_LEFT);
                        if (next != ngrok) {
                            next.requestFocus();
                        }
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }
            });
        }



        joinip.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty()) {
                address = "ws://" + ip1.getText().toString() + "." +
                        ip2.getText().toString() + "." +
                        ip3.getText().toString() + "." +
                        ip4.getText().toString() + ":3000";
                if (address.length() == 23) {
                    next();
                } else {
                    Toast.makeText(this, "Invalid IP address", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }
        });
        ngrokbtn.setOnClickListener(v -> {
            if (!name.getText().toString().isEmpty()) {

                if (!ngrok.getText().toString().isEmpty()) {
                address="https://"+ngrok.getText().toString()+".ngrok.io";
                next();
            }else{
                Toast.makeText(this, "Code cannot be empty!",Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }
        });
        heroku.setOnClickListener(v->{
            if (!name.getText().toString().isEmpty()) {

                address = "https://cipher-crypto-server.herokuapp.com/";
                next();

            }else {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            }
        });
        ojoin.setOnClickListener(v -> {
            ip1.setVisibility(View.VISIBLE);
            ip2.setVisibility(View.VISIBLE);
            ip3.setVisibility(View.VISIBLE);
            ip4.setVisibility(View.VISIBLE);
            joinip.setVisibility(View.VISIBLE);
            ngrok.setVisibility(View.VISIBLE);
            ngrokbtn.setVisibility(View.VISIBLE);
            findViewById(R.id.textView5).setVisibility(View.VISIBLE);
            findViewById(R.id.textView6).setVisibility(View.VISIBLE);
            findViewById(R.id.textView7).setVisibility(View.VISIBLE);
            findViewById(R.id.textView8).setVisibility(View.VISIBLE);
            findViewById(R.id.textView9).setVisibility(View.VISIBLE);
            findViewById(R.id.textView10).setVisibility(View.VISIBLE);
        });



    }
    public void next(){
        Toast.makeText(this, "Connecting to "+address, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, chatActivity.class);
        Bundle extras = new Bundle();
        extras.putString("name", name.getText().toString());
        extras.putString("address", address);
        intent.putExtras(extras);
        startActivity(intent);
    }
}