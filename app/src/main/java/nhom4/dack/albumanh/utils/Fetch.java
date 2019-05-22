package nhom4.dack.albumanh.utils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fetch {
    public static String get(String uri) throws IOException, NullPointerException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
