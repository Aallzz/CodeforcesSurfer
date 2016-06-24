package com.company;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HTTPConnection {

    private String stringUrl;
    private URL url;
    private HttpURLConnection mainHttpConnection = null;

    public HTTPConnection(String stringUrl) throws Exception {
        this.stringUrl = stringUrl;
        url = new URL(stringUrl);
    }

    ArrayList<String> makeRequest() throws Exception {
        mainHttpConnection = (HttpURLConnection)url.openConnection();
        ArrayList<String> result = new ArrayList<>();
        InputStream inputStream = mainHttpConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine = "";

        while ((nextLine = bufferedReader.readLine()) != null) {
            result.add(nextLine);
        }

        return result;
    }

}