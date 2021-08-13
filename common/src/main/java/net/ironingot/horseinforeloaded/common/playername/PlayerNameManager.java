package net.ironingot.horseinforeloaded.common.playername;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import com.google.gson.Gson;

public class PlayerNameManager
{
    private final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private Map<UUID, String> cache = new HashMap<UUID, String>();
    private List<UUID> requestList = new ArrayList<UUID>();

    private class PlayerNameResult
    {
        public String name;
    }

    public String getPlayerName(UUID uuid)
    {
        if (cache.containsKey(uuid))
            return cache.get(uuid);

        if (!requestList.contains(uuid))
        {
            requestList.add(uuid);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String suuid = uuid.toString().toLowerCase().replaceAll("-", "");
                    // String name = "Unknown Player";

                    try
                    {
                        URL url = new URL(PROFILE_URL + suuid);
                        URLConnection con = url.openConnection();
                        InputStream in = con.getInputStream();
                        byte[] buffer = new byte[con.getContentLength()];
                        in.read(buffer);
                        String res = new String(buffer);
                        Gson gson = new Gson();
                        PlayerNameResult result = gson.fromJson(res, PlayerNameResult.class);
                        cache.put(uuid, result.name);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    int index = requestList.indexOf(uuid);
                    if (index >= 0) {
                        requestList.remove(index);
                    }
                }
            }).start();
        }

        return uuid.toString().substring(0, 13);
    }
}
