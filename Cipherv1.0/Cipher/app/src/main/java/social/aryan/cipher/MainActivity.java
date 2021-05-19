package social.aryan.cipher;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    private EditText apiurl;
    private Button confirm;
    private Button defaultbtn;
    private Button chat;
    public String passedData="";
    public static String checker="https://pastebin.com/raw/k5VZcbg2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        apiurl = (EditText) findViewById(R.id.apiurltxt);
        confirm = (Button) findViewById(R.id.confirmbtn);
        defaultbtn = findViewById(R.id.defaultbtn);
        chat = findViewById(R.id.chat);

//        confirm.setEnabled(false);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                validate(apiurl.getText().toString());
            }
        });
        defaultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(checker);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, chatActivity.class);
                startActivity(intent2);
//                finish();
            }
        });
        apiurl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String dsbl = apiurl.getText().toString().trim();
                confirm.setEnabled(!dsbl.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
//    =========================================================================================================================================================================



//    =========================================================================================================================================================================


    public void validate(String url) {
        String word = "";
        word= url;

//            urlcall(word);
            Intent intent1 = new Intent(MainActivity.this, encodeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url",word);
            intent1.putExtras(bundle);
            startActivity(intent1);
//            finish();

    }
}



