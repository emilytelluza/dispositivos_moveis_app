package br.edu.uniritter.primeirade24_2.services;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.edu.uniritter.primeirade24_2.models.Post;

public class PostServices {
    private static final String URL = "https://jsonplaceholder.typicode.com/posts";
    // é uma boa prática colocar em cada classe um TAG para facilitar a identificação de logs
    private static final String TAG = "PostServices";
    private List<Post> posts;
    private Context context;
    private boolean ready = false;
    //
    // Singleton instance
    private static PostServices instance;


    // Implementação do Singleton
    //tornar construtor privado
    private PostServices(Context context) {
        this.context = context;
        this.posts = new ArrayList<>();
        initialLoad();
    }

    //método para obter a instância
    public static PostServices getInstance(Context context) {
        if (instance == null) {
            instance = new PostServices(context);
        }
        return instance;
    }

    //segundo método para obter a instância
    public static PostServices getInstance() throws RuntimeException{
        if (instance == null) {
            throw new RuntimeException("Não foi possível obter a instância de PostServices. É necessário chamar o método getInstance(Context) antes.");
        }
        return instance;
    }
    public boolean isReady() {
        return ready;
    }
    public List<Post> getPosts() {
        // To-do: implementar lista imutável ou de cópias
        return posts;
    }
    public Post getPost(int id) {
        //modo tradicional
        //for (Post post : posts) {
        //    if (post.getId() == id) {
        //        return post;
        //    }
        //}
        //modo funcional usando Stream
        return posts.stream().filter(post -> post.getId() == id).findFirst().orElse(null);

    }
    private void initialLoad() {
        // cria a fila de requisições http feitas pelo Volley
        RequestQueue queue = Volley.newRequestQueue(context);
        // cria a requisição
        // uma requisição precisa de endereço (URL),
        // um listener de sucesso e
        // um listener de erro

        JsonArrayRequest jsonArrRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        Log.d("Response", jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                posts.add(new Post(jsonArray.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Log.d("Post", "dentro do initalLoad: " + posts.size());
                        ready = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("Error", volleyError.toString());
            }

        });
        // adiciona a requisição na fila para ser executada
        queue.add(jsonArrRequest);
    }
}
