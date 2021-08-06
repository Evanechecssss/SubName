package top.evanechecssss.sub_name.capabilities.names;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import top.evanechecssss.sub_name.SubName;
import top.evanechecssss.sub_name.network.NamesNetwork;


public class Names implements INames {

    private String name = "";
    private String subName = "";
    private boolean showName = true;
    private boolean showSub = true;


    @Override
    public void set(String name, String subName, boolean showName, boolean showSub) {
        this.name = name;
        this.subName = subName;
        this.showName = showName;
        this.showSub = showSub;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSubName() {
        return this.subName;
    }

    @Override
    public boolean getShowSubName() {
        return showSub;
    }

    @Override
    public boolean getShowName() {
        return showName;
    }

    public static void synchronize(Entity entity) {
        INames handler = getHandler(entity);
        SubName.getWrapper().sendToAll(new NamesNetwork.NamesMessage(handler.getName(), handler.getSubName(), entity.getUniqueID().toString(), handler.getShowName(), handler.getShowSubName()));
    }

    @Override
    public void clean(boolean isName) {
        if (isName) this.name = "";
        else this.subName = "";
    }

    public static void enableNames() {
        SubName.getWrapper().sendToAll(new NamesNetwork.ChangeNameMessage());
    }

    public static void refreshName(EntityPlayerMP player) {
        enableNames();
        player.world.playerEntities.forEach(EntityPlayer::refreshDisplayName);
        SubName.getWrapper().sendToAll(new NamesNetwork.RefreshDisplayNameMessage());
    }

    public static void setToHandler(String name, String subName, boolean showName, boolean showSub, Entity entity) {
        INames handler = getHandler(entity);
        handler.set(name, subName, showName, showSub);
    }

    public static void cleanToHandler(boolean isName, Entity entity) {
        INames handler = getHandler(entity);
        handler.clean(isName);
    }

    public static void setShowName(Entity entity, boolean showName) {
        INames handler = getHandler(entity);
        handler.set(Names.getDisplayName(entity), Names.getSubName(entity), showName, Names.getShowSub(entity));
    }

    public static void setShowSub(Entity entity, boolean showSub) {
        INames handler = getHandler(entity);
        handler.set(Names.getDisplayName(entity), Names.getSubName(entity), Names.getShowName(entity), showSub);
    }

    public static boolean getShowSub(Entity entity) {
        return getHandler(entity).getShowSubName();
    }

    public static boolean getShowName(Entity entity) {
        return getHandler(entity).getShowName();
    }


    public static String getSubName(Entity entity) {
        return getHandler(entity).getSubName();
    }

    public static String getDisplayName(Entity player) {
        return getHandler(player).getName();
    }

    public static INames getHandler(Entity entity) {
        if (entity.hasCapability(NamesProvider.NAMES_CAP, null))
            return entity.getCapability(NamesProvider.NAMES_CAP, null);
        return null;
    }
}
