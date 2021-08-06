package top.evanechecssss.sub_name.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.evanechecssss.sub_name.capabilities.names.Names;


public class NamesNetwork {

    public static class NamesMessage implements IMessage {
        public NamesMessage() {

        }

        public String name;
        public String subName;
        public String trueName;
        public boolean showName;
        public boolean showSub;


        public NamesMessage(String name, String subName, String trueName, boolean showName, boolean showSub) {
            this.name = name;
            this.subName = subName;
            this.trueName = trueName;
            this.showName = showName;
            this.showSub = showSub;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.name = ByteBufUtils.readUTF8String(buf);
            this.subName = ByteBufUtils.readUTF8String(buf);
            this.trueName = ByteBufUtils.readUTF8String(buf);
            this.showName = buf.readBoolean();
            this.showSub = buf.readBoolean();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            ByteBufUtils.writeUTF8String(buf, this.name);
            ByteBufUtils.writeUTF8String(buf, this.subName);
            ByteBufUtils.writeUTF8String(buf, this.trueName);
            buf.writeBoolean(this.showName);
            buf.writeBoolean(this.showSub);
        }
    }

    public static class NamesMessageHandler implements IMessageHandler<NamesMessage, IMessage> {

        @SideOnly(Side.CLIENT)
        public static void doSynchronize(String name, String subName, String trueName, boolean showName, boolean showSubName) {
            Minecraft.getMinecraft().player.getEntityWorld().loadedEntityList.forEach(entity -> {
                if (entity.getUniqueID().toString().equals(trueName)) {
                    Names.setToHandler(name, subName, showName, showSubName, entity);
                }
            });
        }

        @Override
        public IMessage onMessage(NamesMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> doSynchronize(message.name, message.subName, message.trueName, message.showName, message.showSub));
            return null;
        }
    }

    public static class RefreshDisplayNameMessage implements IMessage {
        @Override
        public void fromBytes(ByteBuf buf) {
        }

        @Override
        public void toBytes(ByteBuf buf) {
        }
    }

    public static class RefreshDisplayNameMessageHandler implements IMessageHandler<RefreshDisplayNameMessage, IMessage> {
        @SideOnly(Side.CLIENT)
        public static void doRefresh() {
            Minecraft.getMinecraft().player.world.playerEntities.forEach(EntityPlayer::refreshDisplayName);
        }

        @Override
        public IMessage onMessage(RefreshDisplayNameMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(RefreshDisplayNameMessageHandler::doRefresh);
            return null;
        }
    }

    public static class ChangeNameMessage implements IMessage {

        @Override
        public void fromBytes(ByteBuf buf) {

        }

        @Override
        public void toBytes(ByteBuf buf) {

        }
    }

    public static class ChangeNameMessageHandler implements IMessageHandler<ChangeNameMessage, IMessage> {
        @SideOnly(Side.CLIENT)
        public static void setDisplayName() {
            Minecraft.getMinecraft().world.playerEntities.forEach(player -> {
                if (Names.getDisplayName(player).isEmpty() || !Names.getShowName(player)) {
                    Minecraft.getMinecraft().player.connection.getPlayerInfo(player.getUniqueID()).setDisplayName(new TextComponentString(player.getName()));

                } else {
                    Minecraft.getMinecraft().player.connection.getPlayerInfo(player.getUniqueID()).setDisplayName(new TextComponentString(Names.getDisplayName(player)));
                }
            });
        }

        @Override
        public IMessage onMessage(ChangeNameMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(ChangeNameMessageHandler::setDisplayName);
            return null;
        }
    }
}
