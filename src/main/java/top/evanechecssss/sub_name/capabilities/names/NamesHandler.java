package top.evanechecssss.sub_name.capabilities.names;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import top.evanechecssss.sub_name.ModInfo;
import top.evanechecssss.sub_name.capabilities.move.name.NameMove;

public class NamesHandler {

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event) {
        EntityPlayer player = event.getEntityPlayer();
        final INames original = Names.getHandler(event.getOriginal());
        final INames clone = Names.getHandler(player);
        clone.set(original.getName(), original.getSubName(), original.getShowName(), original.getShowSubName());
    }

    public static final ResourceLocation NAMES_CAP = new ResourceLocation(ModInfo.MODID, "names");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(NAMES_CAP, new NamesProvider());
    }


    @SubscribeEvent
    public void playerLogged(PlayerLoggedInEvent event) {
        EntityPlayer entityPlayer = event.player;
        if (entityPlayer.world.isRemote) return;
        Names.synchronize(entityPlayer);
    }
    @SubscribeEvent
    public void tracker(PlayerEvent.StartTracking event) {
        EntityPlayer entityPlayer = event.getEntityPlayer();
        if (entityPlayer.world.isRemote) return;
        Names.synchronize(event.getTarget());
        Names.refreshName((EntityPlayerMP) entityPlayer);
    }
}
