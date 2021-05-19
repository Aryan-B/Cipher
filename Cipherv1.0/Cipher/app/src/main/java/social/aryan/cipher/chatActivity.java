package social.aryan.cipher;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;


public class chatActivity extends AppCompatActivity {

    private TextView see;
    private EditText type;
//ScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button buttonSend = (Button) findViewById(R.id.send);

        type = (EditText) findViewById(R.id.messagetype);
        see = (TextView) findViewById(R.id.messagebox);
        see.setMovementMethod(new ScrollingMovementMethod());

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(type.getText().toString());
//                scrollToBottom();
                type.setText("");
            }
        });



    }
//        private void scrollToBottom()
//        {
//            mScrollView.post(new Runnable()
//            {
//                public void run()
//                {
//                    mScrollView.smoothScrollTo(0, see.getBottom());
//                }
//            });
//        }



    private void sendMessage(final String msg) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
//                    URL a=new URL("https://e1f523c7b6bf.ngrok.io");
                    Socket socket = new Socket("192.168.0.107",4949);

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                    out.print(msg + "\n");
                    out.flush(); // important! make sure x actually gets sent



                    final String st = in.readLine();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String s = see.getText().toString();
                            if (st.trim().length() != 0)
                                see.setText(s + "\nFrom Server : " + st);
                        }
                    });



                    in.close();
                    out.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}