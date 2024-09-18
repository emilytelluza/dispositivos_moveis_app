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

import br.edu.uniritter.primeirade24_2.models.Comment;

public class CommentService {
    private static final String COMMENTS_URL = "https://jsonplaceholder.typicode.com/comments";
    private RequestQueue requestQueue;
    private static CommentService instance;

    private CommentService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized CommentService getInstance(Context context) {
        if (instance == null) {
            instance = new CommentService(context);
        }
        return instance;
    }

    public void getComments(final Response.Listener<List<Comment>> listener, final Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, COMMENTS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Comment> comments = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonComment = response.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setId(jsonComment.getInt("id"));
                        comment.setPostId(jsonComment.getInt("postId"));
                        comment.setName(jsonComment.getString("name"));
                        comment.setEmail(jsonComment.getString("email"));
                        comment.setBody(jsonComment.getString("body"));
                        comments.add(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(comments);
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
