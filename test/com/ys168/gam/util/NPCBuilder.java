package com.ys168.gam.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ys168.gam.model.Npc;

public class NPCBuilder {

    private static final String BASE_DIR = NPCBuilder.class.getClassLoader().getResource("").toString().substring(6) + "npc/";

    public static void main(String[] args) {
        JSONArray array = JSONLoader.loadArray(BASE_DIR + "npc.json");
        List<Npc> npcs = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Npc npc = new Npc();
            for (String key : object.keySet()) {
                npc.setAttribute(key, object.get(key));
            }
            npcs.add(npc);
        }

        for (Npc npc : npcs) {
            System.out.println(npc.getAttribute("teach"));
        }
    }

}
