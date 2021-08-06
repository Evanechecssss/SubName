package top.evanechecssss.sub_name.client;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.evanechecssss.sub_name.capabilities.move.name.NameMove;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMove;
import top.evanechecssss.sub_name.capabilities.names.Names;


public class RenderHandler {

    @SubscribeEvent
    public void entityFormat(PlayerEvent.NameFormat event) {
        if (Names.getDisplayName(event.getEntityPlayer()).isEmpty() || !Names.getShowName(event.getEntityPlayer())) {
            event.setDisplayname(event.getEntityPlayer().getName());
        } else {
            event.setDisplayname(Names.getDisplayName(event.getEntityPlayer()));
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    @SideOnly(Side.CLIENT)
    public void onRenderLiving(RenderLivingEvent.Specials.Pre event) {
        Entity entity = event.getEntity();
        if (Names.getShowName(entity)) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(NameMove.getHorizonFromEntity(entity), 0.7 + NameMove.getHighFromEntity(entity), 0);
        } else {
            event.setCanceled(true);
        }

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void postRenderLiving(RenderLivingEvent.Specials.Post event) {
        if (Names.getShowName(event.getEntity())) {
            GlStateManager.popMatrix();
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityRender(RenderPlayerEvent.Pre event) {
        if (Names.getSubName(event.getEntityPlayer()).isEmpty() || !Names.getShowSub(event.getEntityPlayer())) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX() + SubMove.getHorizonFromEntity(event.getEntity()), event.getY() + SubMove.getHighFromEntity(event.getEntity()) + 0.3, event.getZ());
        GlStateManager.scale(0.8, 0.8, 0.8);
        event.getRenderer().renderEntityName((AbstractClientPlayer) event.getEntityPlayer(), 0, 0, 0, Names.getSubName(event.getEntityPlayer()), 0);
        GlStateManager.popMatrix();

    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void allEntity(RenderLivingEvent.Pre event) {
        if (event.getEntity() instanceof EntityPlayer) return;
        if (Names.getSubName(event.getEntity()).isEmpty() || !Names.getShowSub(event.getEntity())) return;
        if (Names.getShowSub(event.getEntity())) {
            event.getEntity().setCustomNameTag(Names.getDisplayName(event.getEntity()));
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(event.getX() + SubMove.getHorizonFromEntity(event.getEntity()), event.getY() + SubMove.getHighFromEntity(event.getEntity()) + 0.3, event.getZ());
        GlStateManager.scale(0.8, 0.8, 0.8);
        event.getRenderer().renderEntityName(event.getEntity(), 0, 0, 0, Names.getSubName(event.getEntity()), 0);
        GlStateManager.popMatrix();
    }


}
