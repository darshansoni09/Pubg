package com.example.pubgtournament.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pubgtournament.Adapters.duoAdapter;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */

public class DuoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    List<Tournament> tournamentList;
    duoAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    String map_type,match_type,match_version;
    String match_fee,first_prize,match_number,per_kill_rate,match_id;

    private static final String shared_pref="UserDetails";

    String pubg_name, first_name, last_name, email_id, mobile_number, password, refer_code;
    String user_id, user_balance1, bonus, withdraw_balance;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_duo, container, false);

        recyclerView=view.findViewById(R.id.duoRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tournamentList=new ArrayList<>();

        swipeRefreshLayout=view.findViewById(R.id.swipeDuo);

        swipeRefreshLayout.setOnRefreshListener(this);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(shared_pref,Context.MODE_PRIVATE);
        pubg_name=sharedPreferences.getString("pubg_name",null);
        first_name=sharedPreferences.getString("first_name",null);
        password=sharedPreferences.getString("password",null);
        last_name=sharedPreferences.getString("last_name",null);
        withdraw_balance=sharedPreferences.getString("withdraw_balance",null);
        user_id=sharedPreferences.getString("user_id",null);
        bonus=sharedPreferences.getString("bonus",null);
        email_id=sharedPreferences.getString("email_id",null);
        mobile_number=sharedPreferences.getString("mobile_number",null);
        refer_code=sharedPreferences.getString("refer_code",null);

        Log.d("userinfoDuo",""+pubg_name);
        Log.d("userinfoDuo",""+password);
        Log.d("userinfoDuo",""+first_name);
        Log.d("userinfoDuo",""+last_name);
        Log.d("userinfoDuo",""+email_id);
        Log.d("userinfoDuo",""+mobile_number);
        Log.d("userinfoDuo",""+refer_code);
        Log.d("userinfoDuo",""+bonus);
        Log.d("userinfoDuo",""+withdraw_balance);
        Log.d("userinfoDuo",""+user_id);

        Call<List<UsersData>> call= RetrofitClient
                .getInstance()
                .getApi()
                .getUsers(pubg_name);

        call.enqueue(new Callback<List<UsersData>>() {
            @Override
            public void onResponse(Call<List<UsersData>> call, retrofit2.Response<List<UsersData>> response) {

                List<UsersData> usersData=response.body();

                for (UsersData data : usersData){
                    user_balance1=data.getUserBalance();

                }
                Log.d("userBalanceSolo",user_balance1);

                String name="Duo";

                final ProgressDialog progressDialog=ProgressDialog.show(getContext(),"","Loading...");
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="https://darshansoni0902.000webhostapp.com/PubgTournament/soloJsonData.php?match_type="+name;
                Log.d("url1234",url);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();

                                try {
                                    JSONArray jsonArray=new JSONArray(response);

                                    for (int i=0;i<jsonArray.length();i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        match_id=jsonObject.getString("match_id");
                                        map_type=jsonObject.getString("map_type");
                                        match_type=jsonObject.getString("match_type");
                                        match_version=jsonObject.getString("match_version");
                                        match_fee=jsonObject.getString("match_fee");
                                        first_prize=jsonObject.getString("first_prize");
                                        match_number=jsonObject.getString("match_number");
                                        per_kill_rate=jsonObject.getString("per_kill_rate");

                                        Tournament tournament=new Tournament(map_type,match_type,match_version,match_id
                                                ,match_fee,first_prize,match_number,per_kill_rate);

                                        tournamentList.add(tournament);

                                    }
                                 adapter=new duoAdapter(getContext(),tournamentList,user_balance1,user_id,pubg_name);
                                 recyclerView.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

                queue.add(stringRequest);
            }

            @Override
            public void onFailure(Call<List<UsersData>> call, Throwable t) {

            }
        });


        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.detach(DuoFragment.this).attach(DuoFragment.this).commit();
            }
        },1000);
    }

}
