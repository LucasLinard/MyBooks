package tech.linard.android.mybooks;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 29/11/16.
 */

public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Volume> fetchVolumeData(URL requestURL) {

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(requestURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Volume> volumes = extractVolumeFromJson(jsonResponse);
        return volumes;
    }

    private static List<Volume> extractVolumeFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Volume> volumes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i=0; i < itemsArray.length(); i++) {
                JSONObject currentVolumeJSON = itemsArray.getJSONObject(i);
                Volume currentVolume = new Volume();
                currentVolume.setmKind(currentVolumeJSON.optString("kind"));
                currentVolume.setmId(currentVolumeJSON.optString("id"));
                currentVolume.setmEtag(currentVolumeJSON.optString("etag"));
                JSONObject volumeInfo = currentVolumeJSON.getJSONObject("volumeInfo");
                currentVolume.setmTitle(volumeInfo.optString("title"));
                currentVolume.setmSubtitle(volumeInfo.optString("subtitle"));
                currentVolume.setmPublisher(volumeInfo.optString("publisher"));
                currentVolume.setmDescription(volumeInfo.optString("description"));
                currentVolume.setmPublishedDate(volumeInfo.optString("publishedDate"));

                StringBuilder authorsBuilder = new StringBuilder();
                String currentAuthor = "";
                JSONArray authors = volumeInfo.getJSONArray("authors");
                for (int x = 0; x < authors.length(); x++) {
                    currentAuthor = authors.getString(x);
                    authorsBuilder.append(currentAuthor);
                }
                currentVolume.setmAuthors(authorsBuilder.toString());
                volumes.add(currentVolume);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return volumes;
    }

    private static String makeHttpRequest(URL requestURL) throws IOException {
        String jsonResponse = "";
        if (requestURL == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                // Conection OK;
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                // connection fail;
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the volume JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
      return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
}
