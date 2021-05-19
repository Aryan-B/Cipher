package social.aryan.cipher;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;

import static android.content.Context.CLIPBOARD_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class encodeActivity extends Activity {

    public TextView fetch;
    Button clear;
    Button copy1;
    Button copy2;
    Button paste2;
    Button refresh;
    Switch switches;
    EditText text1;
    EditText text2;
    EditText text3;
    String value;


    private static String array_def = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+,./;'[]<>?:=-{}|~`1234567890¢¥±º×ßæŒÖû\\\" ";
    private static String[][] arrays = arraymaker(array_def);
    private static String[] arraydefault = array_def.split("");
    //===================================


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
        fetch = findViewById(R.id.fetched);
        refresh = findViewById(R.id.refresh);

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
                    encrypt_data = encrypt(arrays, value, password);
                    text1.setText(encrypt_data);
                } else if (checked == 4) {
                    decrypt_data = decrypt(arrays, value, password);
                    text1.setText(decrypt_data);
                }
            }
        });
        switches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    text1.setText(encrypt(arrays, value, password));
                    checked = 0;
                }else {
                    text1.setText(decrypt(arrays, value, password));
                    checked = 4;
                }
            }
        });
    }

//        =====================================================================================================================


    private void updateTextView() {
        fetch.setText("Loading...Wait");
    }


    //===================================
    public String encrypt(String[][] arrays2, String texts, String pwds) {
        String[] text = texts.split("");
        String[] pwd = pwds.split("");
        String encrypted = "";
        int damn = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length; i++) {
            if (Arrays.asList(arraydefault).contains(text[i])) {
                damn = array_def.indexOf(text[i]);
            }
            String[] arr = arrays2[Integer.parseInt(pwd[i % pwd.length])];
            sb.append(arr[damn]);

        }
        encrypted = sb.toString();
        return encrypted;
    }

    public String decrypt(String[][] arrays2, String texts, String pwds) {

        String[] text = texts.split("");
        String[] pwd = pwds.split("");
        String decrypted = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            if (Arrays.asList(arrays2[Integer.parseInt(pwd[i % pwd.length])]).contains(text[i])) {
                String arrencrpt = TextUtils.join("", arrays2[Integer.parseInt(pwd[i % pwd.length])]);
                sb.append(arraydefault[arrencrpt.indexOf(text[i])]);
            }
        }
        decrypted = sb.toString();
        return decrypted;
    }

    public static String[][] arraymaker(String input) {
        String[][] output = new String[10][input.length()];
        for (int i = 0; i < 10; i++) {
            String[] temp = input.split("");
            Collections.rotate(Arrays.asList(temp), (i + 1) * 9);
            output[i] = temp;
        }
        return output;
    }


//    public static void main(String[] args) {
//        try {
//            CipherClient client = new CipherClient("127.0.0.1",4949);
//
//
//
//            String l = "{ \"id\": \"1\", \"type\": \"search\",\"query\": \"deer\", \"limit\": \"2\" }";
//            client.sendRequest(l);
//            System.out.println("request sent \n");
//
//            String yo = client.getReply();
//            System.out.println("response received : " + yo + "\n");
//
//            String m = "{ \"id\": \"1\", \"type\": \"close\",\"query\": \"lion\", \"limit\": \"1\" }";
//            client.sendRequest(m);
//            System.out.println("request sent \n");
//
//            String z = client.getReply();
//            System.out.println("response received : " + z + "\n");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}