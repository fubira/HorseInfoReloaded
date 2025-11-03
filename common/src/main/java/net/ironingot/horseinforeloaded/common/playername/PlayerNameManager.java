package net.ironingot.horseinforeloaded.common.playername;

import java.util.Set;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import com.google.gson.Gson;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;

public class PlayerNameManager
{
    private final String PROFILE_URL = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private final Map<UUID, String> cache = new ConcurrentHashMap<>();
    private final Set<UUID> requestList = ConcurrentHashMap.newKeySet();
    private final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r, "HorseInfo-PlayerName-Fetcher");
        thread.setDaemon(true);
        return thread;
    });

    private class PlayerNameResult
    {
        public String name;
    }

    public String getPlayerName(UUID uuid)
    {
        if (cache.containsKey(uuid))
            return cache.get(uuid);

        if (requestList.add(uuid))
        {
            executor.submit(() -> {
                String suuid = uuid.toString().toLowerCase().replaceAll("-", "");

                try
                {
                    URL url = new URI(PROFILE_URL + suuid).toURL();
                    URLConnection con = url.openConnection();
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(5000);

                    try (InputStream in = con.getInputStream()) {
                        byte[] buffer = new byte[con.getContentLength()];
                        in.read(buffer);
                        String res = new String(buffer);
                        Gson gson = new Gson();
                        PlayerNameResult result = gson.fromJson(res, PlayerNameResult.class);
                        if (result != null && result.name != null) {
                            cache.put(uuid, result.name);
                        }
                    }
                }
                catch (Exception e)
                {
                    HorseInfoCore.logger.error("Failed to fetch player name for UUID: {}", uuid, e);
                }
                finally
                {
                    requestList.remove(uuid);
                }
            });
        }

        return uuid.toString().substring(0, 13);
    }

    public void shutdown()
    {
        executor.shutdown();
    }
}
