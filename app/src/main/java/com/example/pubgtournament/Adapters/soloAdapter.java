package com.example.pubgtournament.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubgtournament.Activities.SoloMatchInfo;
import com.example.pubgtournament.Api.RetrofitClient;
import com.example.pubgtournament.Models.Tournament;
import com.example.pubgtournament.Models.UsersData;
import com.example.pubgtournament.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public class soloAdapter extends RecyclerView.Adapter<soloAdapter.soloViewHolder> {

    Context context;
    List<Tournament> tournamentList;
    String user_id;
    String user_balance1;
    String pubg_name;
    private static final String shared_pref="UserDetails";

    public soloAdapter(Context context, List<Tournament> tournamentList, String user_id, String user_balance1, String pubg_name) {

        this.context=context;
        this.tournamentList=tournamentList;
        this.user_id=user_id;
        this.pubg_name=pubg_name;
        this.user_balance1=user_balance1;

    }

    @NonNull
    @Override
    public soloViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.soloitemfile,viewGroup,false);

        return new soloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull soloViewHolder holder, int i) {

        final Tournament tournament=tournamentList.get(i);

        holder.first_prize.setText(String.valueOf(tournament.getFirst_prize()));
        holder.per_kill.setText(String.valueOf(tournament.getPer_kill_rate()));
        holder.entry_fee.setText(String.valueOf(tournament.getMatch_fee()));
        holder.match_version.setText(tournament.getMatch_version());
        holder.map_type.setText(tournament.getMap_type());
        holder.match_number.setText(String.valueOf(tournament.getMatch_number()));

        holder.btnSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("userBalanceAdapter",""+user_balance1);

                int fee=Integer.parseInt(user_balance1);
                Log.d("userBalanceInt",""+ fee);

                String entry=holder.entry_fee.getText().toString();

                int e=Integer.parseInt(entry);
                Log.d("entryFeeAdapter",""+e);

                int updated=fee-e;
                Log.d("updatedBalance",""+updated);

//                int fee= Integer.parseInt(holder.entry_fee.getText().toString());
//                int a1=Integer.parseInt(user_balance1);
//                int user_balance=a1-fee;
//                Log.d("entry_fee",""+fee);
//                Log.d("user_balance",""+user_balance1);
//                Log.d("a1solosololosososloe",""+a1);
//
//
                if (fee<=0 || fee<e){
                    Toast.makeText(context, "You don't have sufficient balance", Toast.LENGTH_SHORT).show();
                }else {

                    holder.btnSolo.setEnabled(false);

                    Toast.makeText(context, "You Have Successfully Joined Solo Contest", Toast.LENGTH_SHORT).show();

                    String first_prize=tournament.getFirst_prize();
                    String per_kill=tournament.getPer_kill_rate();
                    String entry_fee=tournament.getMatch_fee();
                    String match_version=tournament.getMatch_version();
                    String map_type=tournament.getMap_type();
                    String match_number=tournament.getMatch_number();
                    String match_type=tournament.getMatch_type();

                    Log.d("firstPrize",""+first_prize);

                    String chickenStatus="";
                    int usersKill=0;
                    int killPayment=0;
                    int userPrize=0;
                    int id=Integer.parseInt(user_id);

                    int mNuber,fPrize,pKill,eFee;
                    mNuber=Integer.parseInt(match_number);
                    fPrize=Integer.parseInt(first_prize);
                    pKill=Integer.parseInt(per_kill);
                    eFee=Integer.parseInt(entry_fee);

                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                    String time=format.format(calendar.getTime());

                    String date= DateFormat.getDateInstance().format(calendar.getTime());

                    SharedPreferences sharedPreferences = context.getSharedPreferences(shared_pref, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt("user_balance", updated);
                    editor.apply();

                    int balance = sharedPreferences.getInt("user_balance", 0);
                    Log.d("user_updated", String.valueOf(balance));

                    String history="You have successfully joined "+e+"rs Contest";
                    String result="Upcoming";

                    Call<ResponseBody> call =  RetrofitClient
                            .getInstance()
                            .getApi()
                            .userTransaction(pubg_name,history,date,time,fPrize, pKill, eFee, match_version, map_type, match_type, mNuber,result,usersKill,chickenStatus,killPayment,userPrize);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            try {
                                String s=response.body().string();

                                Log.d("responseResponse",""+s);

                                if (s.equals("Data Submit Successfully")){

                                    Call<ResponseBody> callUpdated=RetrofitClient
                                            .getInstance()
                                            .getApi()
                                            .updateBalance(updated,id);

                                    callUpdated.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            String sUpdated= null;
                                            try {
                                                sUpdated = response.body().string();
                                                Log.d("responseUpdated",""+sUpdated);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                                        }
                                    });

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SoloMatchInfo.class);
                intent.putExtra("first_prize",tournament.getFirst_prize());
                intent.putExtra("per_kill",tournament.getPer_kill_rate());
                intent.putExtra("entry_fee",tournament.getMatch_fee());
                intent.putExtra("match_version",tournament.getMatch_version());
                intent.putExtra("map_type",tournament.getMap_type());
                intent.putExtra("match_number",tournament.getMatch_number());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tournamentList.size();
    }

    public class soloViewHolder extends RecyclerView.ViewHolder{

        TextView first_prize,per_kill,entry_fee,match_version,map_type,match_number;
        Button btnSolo;

        public soloViewHolder(@NonNull View itemView) {
            super(itemView);

            first_prize=itemView.findViewById(R.id.solo_first_prize);
            per_kill=itemView.findViewById(R.id.solo_per_kill);
            entry_fee=itemView.findViewById(R.id.solo_entry_fee);
            match_version=itemView.findViewById(R.id.solo_match_version);
            map_type=itemView.findViewById(R.id.solo_map_type);
            match_number=itemView.findViewById(R.id.solo_match_number);

            btnSolo=itemView.findViewById(R.id.btn_solo);

        }
    }

}
