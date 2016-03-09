package net.ironingot.horseinfo.playername;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;

public class PlayerNameFetcher
{
    private static Logger logger = FMLLog.getLogger();

    public static String getPlayerNameFromMojang(UUID uuid)
    {
        String suuid = uuid.toString().toLowerCase().replaceAll("-", "");

        try
        {
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + suuid);
            logger.info("get PlayerName from Mojang: " + url);

            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            byte[] buffer = new byte[con.getContentLength()];
            in.read(buffer);
            String res = new String(buffer);
            Gson gson = new Gson();
            PlayerNameFetchResult result = gson.fromJson(res, PlayerNameFetchResult.class);
            return result.name;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private class PlayerNameFetchResult
    {
        public String id;
        public String name;
    }
}
