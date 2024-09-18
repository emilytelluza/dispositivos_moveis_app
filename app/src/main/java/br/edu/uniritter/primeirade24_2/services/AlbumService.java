package br.edu.uniritter.primeirade24_2.services;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.uniritter.primeirade24_2.models.Album;

public class AlbumService {
    private static final String ALBUMS_URL = "https://jsonplaceholder.typicode.com/albums";
    private RequestQueue requestQueue;
    private static AlbumService instance;

    private AlbumService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized AlbumService getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumService(context);
        }
        return instance;
    }

    public void getAlbums(final Response.Listener<List<Album>> listener, final Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ALBUMS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Album> albums = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonAlbum = response.getJSONObject(i);
                        Album album = new Album();
                        album.setId(jsonAlbum.getInt("id"));
                        album.setUserId(jsonAlbum.getInt("userId"));
                        album.setTitle(jsonAlbum.getString("title"));
                        albums.add(album);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(albums);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        });

        requestQueue.add(request);
    }
}
