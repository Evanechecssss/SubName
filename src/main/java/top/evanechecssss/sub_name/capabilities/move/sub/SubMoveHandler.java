package top.evanechecssss.sub_name.capabilities.move.sub;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import top.evanechecssss.sub_name.ModInfo;

public class SubMoveHandler {
    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        final ISubMove original = SubMove.getHandler(event.getOriginal());
        final ISubMove clone = SubMove.getHandler(player);
        clone.set(original.getHigh(), original.getHorizon());
    }

    public static final ResourceLocation SUB_MOVE = new ResourceLocation(ModInfo.MODID, "sub_move");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(SUB_MOVE, new SubMoveProvider());
    }


    @SubscribeEvent
    public void playerLogged(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer entityPlayer = event.player;
        if (entityPlayer.world.isRemote) return;
        SubMove.synchronize(entityPlayer);
    }

    @SubscribeEvent
    public void tracker(PlayerEvent.StartTracking event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        if (entityPlayer.world.isRemote) return;
        event.getEntityPlayer().world.loadedEntityList.forEach(SubMove::synchronize);
    }
}
