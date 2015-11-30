package com.example.soumya.feedthepanda;

import android.util.Log;

/**
 * Created by Soumya on 30-11-2015.
 */
public enum ChannelSubscriptionType {
    FORCED(1, "Forced"), PUBLIC(2, "Public"), MEMBER(3, "Member");

    String naturalName;
    final int id;
    ChannelSubscriptionType(int id, String naturalName){ this.id = id; this.naturalName = naturalName; }

    public static ChannelSubscriptionType resolveToCategory(String what){
        // Creating a map might not be justifiable for such a small input. Array linear search
        // is efficient enough.
        for(ChannelSubscriptionType c: values()){
            if (what.equals(c.toString())){
                return c;
            }
        }
        Log.v("resolveToChannelSubscriptionType", "No ChannelSubscriptionType found for " + what + ". Reverting back to MEMBER");
        return MEMBER;
    }

    public static ChannelSubscriptionType resolveToCategory(int id){
        ChannelSubscriptionType result;
        switch (id)
        {
            case 1:
                result = FORCED; break;
            case 2:
                result = PUBLIC; break;
            case 3:
                result = MEMBER; break;
            default:
                result = MEMBER;
                Log.v("resolveToChannelSubscriptionType", "No ChannelSubscriptionType found for " + id + ". Reverting back to MEMBER");
        }
        return result;
    }
}
