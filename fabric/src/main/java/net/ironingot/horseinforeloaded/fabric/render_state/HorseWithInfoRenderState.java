package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class HorseWithInfoRenderState extends HorseRenderState {
    public String displayName = "";
    public String owner = "";
    public Entity rider = null;

    public double speed = 0;
    public double jumpHeight = 0;
    public double jumpStrength = 0;
    public float health = 0;
    public float maxHealth = 0;
    public boolean isTamed = false;

    public boolean hasChest = false;
}
