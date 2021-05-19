package social.aryan.cipher;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.os.Handler;

import needle.Needle;

public class CipherClient {

    private Socket socket;
    private BufferedReader in;
    // Rep invariant: socket, in, out != null
    private PrintWriter out;
    public static final int WMS_PORT = 8080;

    /**
     * Make a WikiClient and connect it to a server running on
     * hostname at the specified port.
     *
     * @throws IOException if can't connect
     */


    public CipherClient(String hostname, int port) throws IOException {


        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

    }


    /**
     * Use a WikiMediatorServer to find the result of specified json fields.
     */

    public void sendRequest(String x) throws IOException {
        out.print(x + "\n");
        out.flush(); // important! make sure x actually gets sent
    }

    public String getReply() throws IOException {
        String reply = in.readLine();
        if (reply == null) {
            throw new IOException("connection terminated unexpectedly");
        }

        try {
            return reply;
        } catch (NumberFormatException nfe) {
            throw new IOException("misformatted reply: " + reply);
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }


}