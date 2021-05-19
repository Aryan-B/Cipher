package social.aryan.cipher;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class chatActivity extends AppCompatActivity implements TextWatcher {

    private String name;
    private WebSocket webSocket;
    private String SERVER_PATH;// = "ws://192.168.0.107:3000";
    private EditText messageEdit;
    private Button sendBtn;
    private RecyclerView recyclerView;
    private chatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        name = extras.getString("name");
        SERVER_PATH = extras.getString("address");
        name = getIntent().getStringExtra("name");
        initiateSocketConnection();

    }

    private void initiateSocketConnection() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
//        Toast.makeText(chatActivity.this,SERVER_PATH,Toast.LENGTH_SHORT).show();
        webSocket = client.newWebSocket(request, new SocketListener());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();
        if (string.isEmpty()) {
            resetMessageEdit();
        }

    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");
        messageEdit.addTextChangedListener(this);
    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            runOnUiThread(() -> {
                Toast.makeText(chatActivity.this,"Connection Successful!",Toast.LENGTH_SHORT).show();
                initializeView();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);
                    jsonObject.put("message", new cipher(jsonObject.get("message").toString(),"12345").decrypt());
                    chatAdapter.addItem(jsonObject);
                    recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }
    }

    private void initializeView() {

        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);
        recyclerView = findViewById(R.id.recyclerView);

        chatAdapter = new chatAdapter(getLayoutInflater());
        recyclerView.setAdapter(chatAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", new cipher(messageEdit.getText().toString(),"12345").encrypt());

                webSocket.send(jsonObject.toString());

                jsonObject.put("isSent", true);
                jsonObject.put("message",new cipher(jsonObject.get("message").toString(),"12345").decrypt());
                chatAdapter.addItem(jsonObject);

                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);

                resetMessageEdit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
    }
}
