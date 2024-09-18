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

import br.edu.uniritter.primeirade24_2.models.Todo;

public class TodoService {
    private static final String TODOS_URL = "https://jsonplaceholder.typicode.com/todos";
    private RequestQueue requestQueue;
    private static TodoService instance;

    private TodoService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized TodoService getInstance(Context context) {
        if (instance == null) {
            instance = new TodoService(context);
        }
        return instance;
    }

    public void getTodos(final Response.Listener<List<Todo>> listener, final Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, TODOS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Todo> todos = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonTodo = response.getJSONObject(i);
                        Todo todo = new Todo();
                        todo.setId(jsonTodo.getInt("id"));
                        todo.setUserId(jsonTodo.getInt("userId"));
                        todo.setTitle(jsonTodo.getString("title"));
                        todo.setCompleted(jsonTodo.getBoolean("completed"));
                        todos.add(todo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(todos);
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
