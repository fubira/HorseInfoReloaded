package net.ironingot.horseinfo.playername;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class PlayerNameManager
{
    private Map<UUID, String> cache = new HashMap<UUID, String>();
    private List<UUID> requestList = new ArrayList<UUID>();

    private final int API_ACCESS_INTERVAL = 100;
    private int interval = 0;

    public PlayerNameManager()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTickEvent(TickEvent.ClientTickEvent event)
    {
        if (requestList.isEmpty())
            return;

        if (interval > 0)
        {
            interval --;
            return;
        }

        UUID uuid = requestList.get(0);
        requestList.remove(0);

        String name = PlayerNameFetcher.getPlayerNameFromMojang(uuid);

        if (name != null) {
            cache.put(uuid, name);
        } else {
            cache.put(uuid, "Unknown Player");
        }

        interval = API_ACCESS_INTERVAL;
    }

    public String getPlayerName(UUID uuid)
    {
        if (cache.containsKey(uuid))
            return cache.get(uuid);

        if (!requestList.contains(uuid))
            requestList.add(uuid);

        return uuid.toString().substring(0, 13);
    }
}
