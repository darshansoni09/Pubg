package com.example.pubgtournament.Models;

/**
 * Created by Darshan Soni on 13-Jun-19.
 */
public class Tournament {

    String map_type,match_type,match_version;
    String match_id,match_fee,first_prize,match_number,per_kill_rate;

    public Tournament(String map_type, String match_type, String match_version, String match_id, String match_fee, String first_prize, String match_number, String per_kill_rate) {
        this.map_type = map_type;
        this.match_type = match_type;
        this.match_version = match_version;
        this.match_id = match_id;
        this.match_fee = match_fee;
        this.first_prize = first_prize;
        this.match_number = match_number;
        this.per_kill_rate = per_kill_rate;
    }

    public String getMap_type() {
        return map_type;
    }

    public void setMap_type(String map_type) {
        this.map_type = map_type;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public String getMatch_version() {
        return match_version;
    }

    public void setMatch_version(String match_version) {
        this.match_version = match_version;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMatch_fee() {
        return match_fee;
    }

    public void setMatch_fee(String match_fee) {
        this.match_fee = match_fee;
    }

    public String getFirst_prize() {
        return first_prize;
    }

    public void setFirst_prize(String first_prize) {
        this.first_prize = first_prize;
    }

    public String getMatch_number() {
        return match_number;
    }

    public void setMatch_number(String match_number) {
        this.match_number = match_number;
    }

    public String getPer_kill_rate() {
        return per_kill_rate;
    }

    public void setPer_kill_rate(String per_kill_rate) {
        this.per_kill_rate = per_kill_rate;
    }
}
