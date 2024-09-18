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

import br.edu.uniritter.primeirade24_2.models.User;

public class UserService {
    private static final String USERS_URL = "https://jsonplaceholder.typicode.com/users";
    private RequestQueue requestQueue;
    private static UserService instance;

    private UserService(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized UserService getInstance(Context context) {
        if (instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }

    public void getUsers(final Response.Listener<List<User>> listener, final Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, USERS_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<User> users = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonUser = response.getJSONObject(i);
                        User user = new User();
                        user.setId(jsonUser.getInt("id"));
                        user.setName(jsonUser.getString("name"));
                        user.setUsername(jsonUser.getString("username"));
                        user.setEmail(jsonUser.getString("email"));

                        // Endereço
                        JSONObject jsonAddress = jsonUser.getJSONObject("address");
                        User.Address address = new User.Address();
                        address.setStreet(jsonAddress.getString("street"));
                        address.setSuite(jsonAddress.getString("suite"));
                        address.setCity(jsonAddress.getString("city"));
                        address.setZipcode(jsonAddress.getString("zipcode"));

                        // Geolocalização
                        JSONObject jsonGeo = jsonAddress.getJSONObject("geo");
                        User.Address.Geo geo = new User.Address.Geo();
                        geo.setLat(jsonGeo.getString("lat"));
                        geo.setLng(jsonGeo.getString("lng"));

                        address.setGeo(geo);
                        user.setAddress(address);

                        // Empresa
                        JSONObject jsonCompany = jsonUser.getJSONObject("company");
                        User.Company company = new User.Company();
                        company.setName(jsonCompany.getString("name"));
                        company.setCatchPhrase(jsonCompany.getString("catchPhrase"));
                        company.setBs(jsonCompany.getString("bs"));
                        user.setCompany(company);

                        users.add(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(users);
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
