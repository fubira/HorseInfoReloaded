package net.ironingot.horseinfo;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

import org.dimdev.rift.listener.client.EntityRendererAdder;

import java.util.Map;

public class HorseInfoRenderer implements EntityRendererAdder
{
    @Override
    public void addEntityRenderers(Map<Class<? extends Entity>, Render<? extends Entity>> entityRenderMap, RenderManager renderManager) {
        HorseInfoMod.logger.info("addEntityRenderers: ");
        entityRenderMap.remove(EntityHorse.class);
        entityRenderMap.put(EntityHorse.class, new RenderHorseExtra(renderManager));
        entityRenderMap.remove(EntitySkeletonHorse.class);
        entityRenderMap.put(EntitySkeletonHorse.class, new RenderHorseUndeadExtra(renderManager));
        entityRenderMap.remove(EntityZombieHorse.class);
        entityRenderMap.put(EntityZombieHorse.class, new RenderHorseUndeadExtra(renderManager));
        entityRenderMap.remove(EntityMule.class);
        entityRenderMap.put(EntityMule.class, new RenderHorseChestExtra(renderManager, 0.92f));
        entityRenderMap.remove(EntityDonkey.class);
        entityRenderMap.put(EntityDonkey.class, new RenderHorseChestExtra(renderManager, 0.87f));
        entityRenderMap.remove(EntityLlama.class);
        entityRenderMap.put(EntityLlama.class, new RenderLlamaExtra(renderManager));
        entityRenderMap.remove(EntityWolf.class);
        entityRenderMap.put(EntityWolf.class, new RenderWolfExtra(renderManager));
        entityRenderMap.remove(EntityOcelot.class);
        entityRenderMap.put(EntityOcelot.class, new RenderOcelotExtra(renderManager));
    }
}
