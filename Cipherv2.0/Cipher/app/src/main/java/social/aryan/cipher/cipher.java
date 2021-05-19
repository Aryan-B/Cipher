package social.aryan.cipher;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collections;

public class cipher {
    public String message="";
    public String pwds="";
    private static String array_def = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+,./;'[]<>?:=-{}|~`1234567890¢¥±º×ßæŒÖû\\\" ";
    private static String[][] arrays = arraymaker(array_def);
    private static String[] arraydefault = array_def.split("");

    public cipher(String message, String password){
        this.message=message;
        this.pwds=password;
    }
    public String encrypt() {
        String[] text = new String[0];
        if(message!=null)
            text= message.split("");
        String[] pwd = pwds.split("");
        String encrypted = "";
        int damn = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length; i++) {
            if (Arrays.asList(arraydefault).contains(text[i])) {
                damn = array_def.indexOf(text[i]);
            }
            String[] arr = arrays[Integer.parseInt(pwd[i % pwd.length])];
            sb.append(arr[damn]);

        }
        encrypted = sb.toString();
        return encrypted;
    }

    public String decrypt() {
        String[] text = new String[0];
        if(message!=null)
         text= message.split("");
        String[] pwd = pwds.split("");
        String decrypted = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            if (Arrays.asList(arrays[Integer.parseInt(pwd[i % pwd.length])]).contains(text[i])) {
                String arrencrpt = TextUtils.join("", arrays[Integer.parseInt(pwd[i % pwd.length])]);
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
}
