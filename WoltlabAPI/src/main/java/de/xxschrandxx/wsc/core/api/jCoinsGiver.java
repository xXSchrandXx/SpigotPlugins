package de.xxschrandxx.wsc.core.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class jCoinsGiver {

    /**
     * Sends jCoins to https://github.com/xXSchrandXx/SimplejCoinsListener 
     * @param url The url String.
     * @param secretkey The secret key.
     * @param authorID The authors ID. Has to be an existing admin ID!
     * @param authorname The authors name.
     * @param moderative Weather this is moderative.
     * @param receiveruserID The receivers ID.
     * @param amount The amount.
     * @param reason The reason.
     * @return The message from the Website.
     * @throws IOException {@link IOException}
     * @throws ProtocolException {@link ProtocolException}
     * @throws SecurityException {@link SecurityException}
     */
  public static List<String> sendMoney(String url, String secretkey, Integer authorID, String authorname, Boolean moderative, Integer receiveruserID, Integer amount, String reason) throws IOException, ProtocolException, SecurityException {
    List<String> result = new ArrayList<String>();
    String urlString = url +
        "&secretkey=" + secretkey +
        "&amount=" + amount +
        "&reason=" + reason +
        "&userID=" + receiveruserID +
        "&authorID=" + authorID +
        "&authorname=" + authorname +
        "&moderative=" + moderative;
    if(urlString.contains(" "))
      urlString = urlString.replace(" ", "%20");
    if (url.startsWith("https://")) {
      HttpsURLConnection con = (HttpsURLConnection) (new URL(urlString)).openConnection();
      String input = "";
      BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
      while ((input = reader.readLine()) != null) {
        result.add(input);
      }
      reader.close();
      con.disconnect();
    }
    else if (url.startsWith("http://")) {
      HttpURLConnection con = (HttpURLConnection) (new URL(urlString)).openConnection();
      String input = "";
      BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
      while ((input = reader.readLine()) != null) {
        result.add(input);
      }
      reader.close();
      con.disconnect();
    }
    return result;
  }

}