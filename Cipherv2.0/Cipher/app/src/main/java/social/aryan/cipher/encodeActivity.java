package social.aryan.cipher;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class encodeActivity extends Activity {

    Button clear;
    Button copy1;
    Button copy2;
    Button paste2;
    Switch switches;
    EditText text1;
    EditText text2;
    EditText text3;
    String value;

    public String decrypt_data;
    public String encrypt_data;
    public String password;
    public int checked = 0;


    //    ===========================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        copy1 = findViewById(R.id.copyD);
        copy2 = findViewById(R.id.copyE);
        clear = findViewById(R.id.clear);
        paste2 = findViewById(R.id.pasteE);
        text1 = findViewById(R.id.decryptedData);
        text2 = findViewById(R.id.encryptedData);
        text3 = findViewById(R.id.PWD);
        switches = findViewById(R.id.switch1);

//        =====================================================================================================================
        copy1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text copied to clipboard", text1.getText().toString()));
                Toast.makeText(encodeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        copy2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v2) {
                ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Text copied to clipboard", text2.getText().toString()));
                Toast.makeText(encodeActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                text2.setText("");
            }
        });
        paste2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    CharSequence textToPaste = ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).getPrimaryClip().getItemAt(0).getText();
                    int start = Math.max(text2.getSelectionStart(), 0);
                    int end = Math.max(text2.getSelectionEnd(), 0);
                    text2.getText().replace(Math.min(start, end), Math.max(start, end), textToPaste, 0, textToPaste.length());
                } catch (Exception e) {
                }
            }

        });
        text3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                password = s.toString();
                text1.setEnabled(!password.isEmpty());
                text2.setEnabled(!password.isEmpty());
                if (password.length() == 0) {
                    text2.setText("");
                } else if (checked == 0) {
                    text1.setText(encrypt_data);
                } else if (checked == 4) {
                    text1.setText(decrypt_data);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        text2.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                value = s.toString();
                if (value.isEmpty()) {
                    text1.setText("Result");
                    return;
                }


                decrypt_data = "";
                if (checked == 0) {
                    cipher data1 = new cipher(value, password);
                    encrypt_data = data1.encrypt();
                    text1.setText(encrypt_data);
                } else if (checked == 4) {
                    cipher data2 = new cipher(value, password);
                    decrypt_data = data2.decrypt();
                    text1.setText(decrypt_data);
                }
            }
        });
        switches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    text1.setText(new cipher(value,password).encrypt());
                    checked = 0;
                }else {
                    text1.setText(new cipher(value,password).decrypt());
                    checked = 4;
                }
            }
        });
    }
}