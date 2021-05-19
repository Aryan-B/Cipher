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
    private Button defaultbtn;
    private Button chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        defaultbtn = findViewById(R.id.defaultbtn);
        chat = findViewById(R.id.chat);

        defaultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
            Intent intent1 = new Intent(MainActivity.this, encodeActivity.class);
            startActivity(intent1);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, chatMainActivity.class);
                startActivity(intent2);
            }
        });
    }
}



