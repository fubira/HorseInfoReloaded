package net.ironingot.horseinforeloaded.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "horseinforeloaded")
public class FabricConfig implements ConfigData {
    public boolean enableMod = false;

	public static FabricConfig register(){
		var configHolder = AutoConfig.register(FabricConfig.class, GsonConfigSerializer::new);

        return configHolder.getConfig();
	}
}
