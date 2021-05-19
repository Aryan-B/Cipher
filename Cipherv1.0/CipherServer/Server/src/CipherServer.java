import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.lang.*;


public class CipherServer {
    public static final int WMS_PORT = 4949;
    private ServerSocket serverSocket;

    public CipherServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }


    public void serve() throws IOException, InterruptedException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // create a new thread to handle that client
            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            handle(socket);
                        } finally {
                            socket.close();
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            });
            handler.start();
            handler.join();
        }
    }

    private void handle(Socket socket) throws IOException {
        System.err.println("client connected");
        File localFile = new File("filename.txt");
//        if (localFile.createNewFile()) {
//            System.err.println("file created");
//        } else {
//            localFile.delete();
//            System.err.println("file delete");
//            localFile.createNewFile();
//            System.err.println("new file created");
//        }
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream()), true);
        FileWriter writer = new FileWriter(localFile,true);

        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                try {
                    System.err.println("reply: " + line);
                    String y=encrypt(arrays,line,"12345");
                    out.println("ENCRYPTED TEXT: "+y+"  >>>  DECRYPTED TEXT: "+decrypt(arrays,y,"12345"));

                    writer.append("Encrypted message : "+ y +"\n");
                    writer.append("Decrypted message : "+ decrypt(arrays,y,"12345")+"\n");
                    writer.flush();

                } catch (NumberFormatException e) {
                    // complain about ill-formatted request
                    System.err.println("reply: err");
                    out.println("err\n");
                }
            }
        } finally {
            out.close();
            in.close();
        }
    }


    public static String[][] arraymaker(String input){
        String[][] output = new String[10][input.length()];
        for(int i=0;i<10;i++) {
            String[] temp = input.split("");
            Collections.rotate(Arrays.asList(temp), (i+1) * 9);
            output[i] = temp;
//            System.out.println(Arrays.toString(temp));
        }
        return output;
    }

    private static String array_def= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+,./;'[]<>?:=-{}|~`1234567890¢¥±º×ßæŒÖû\\\" ";
    private static String[][] arrays=arraymaker(array_def);
    private static String[] arraydefault = array_def.split("");

    public static String encrypt(String[][] arrays2, String texts, String pwds) {
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

    public static String decrypt(String[][] arrays2, String texts, String pwds) {
        String[] text = texts.split("");
        String[] pwd = pwds.split("");
        String decrypted = "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length; i++) {
            if (Arrays.asList(arrays2[Integer.parseInt(pwd[i % pwd.length])]).contains(text[i])) {
                String arrencrpt= (String.join("",arrays2[Integer.parseInt(pwd[i % pwd.length])]));
//                System.out.println(arrencrpt);
                sb.append(arraydefault[arrencrpt.indexOf(text[i])]);
//                System.out.println(arrencrpt.indexOf(text[i]));
//                System.out.println(Arrays.toString(arraydefault));
            }
        }
        decrypted = sb.toString();
        return decrypted;
    }

    public static void main(String[] args) {

                try {
            CipherServer server = new CipherServer(WMS_PORT);
            server.serve();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}






