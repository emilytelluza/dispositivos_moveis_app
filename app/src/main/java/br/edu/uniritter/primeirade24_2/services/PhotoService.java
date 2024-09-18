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

import br.edu.uniritter.primeirade24_2.models.Photo;

public class PhotoService {
    private static final String PHOTOS_URL = "https://jsonplaceholder.typicode.com/photos";
    private RequestQueue requestQueue;
    private static PhotoService instance;

    private PhotoService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized PhotoService getInstance(Context context) {
        if (instance == null) {
            instance = new PhotoService(context);
        }
        return instance;
    }

    public void getPhotos(final Response.Listener<List<Photo>> listener, final Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, PHOTOS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Photo> photos = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonPhoto = response.getJSONObject(i);
                        Photo photo = new Photo();
                        photo.setId(jsonPhoto.getInt("id"));
                        photo.setAlbumId(jsonPhoto.getInt("albumId"));
                        photo.setTitle(jsonPhoto.getString("title"));
                        photo.setUrl(jsonPhoto.getString("url"));
                        photo.setThumbnailUrl(jsonPhoto.getString("thumbnailUrl"));
                        photos.add(photo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(photos);
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
