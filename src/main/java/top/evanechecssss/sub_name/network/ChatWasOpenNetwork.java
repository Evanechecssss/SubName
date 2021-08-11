package top.evanechecssss.sub_name.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.evanechecssss.sub_name.ModInfo;

public class ChatWasOpenNetwork {

    @SideOnly(Side.CLIENT)
    public static boolean getChatOpenState(EntityPlayerMP player){
        NBTTagCompound compound = player.getEntityData();
        if (compound.hasKey(ModInfo.CHAT_OPEN)) {
            return compound.getBoolean(ModInfo.CHAT_OPEN);
        }else {
            return false;
        }
    }
    public static class ChatWasOpenMessage implements IMessage{
        public boolean chatOpen;

        public ChatWasOpenMessage() {
            super();
        }
        public ChatWasOpenMessage(boolean chatOpen) {
            this.chatOpen = chatOpen;
        }

        @Override
        public void fromBytes(ByteBuf byteBuf) {
            this.chatOpen = byteBuf.readBoolean();
        }

        @Override
        public void toBytes(ByteBuf byteBuf) {
            byteBuf.writeBoolean(this.chatOpen);
        }
    }
    public static class ChatWasOpenHandler implements IMessageHandler<ChatWasOpenMessage, IMessage>{


        private static void writeData(EntityPlayerMP playerMP, boolean chatOpen){
           NBTTagCompound compound = playerMP.getEntityData();
           compound.setBoolean(ModInfo.CHAT_OPEN,chatOpen);
        }
        @Override
        public IMessage onMessage(ChatWasOpenMessage chatWasOpenMessage, MessageContext messageContext) {
            messageContext.getServerHandler().player.getServerWorld().addScheduledTask(() -> {
                writeData(messageContext.getServerHandler().player, chatWasOpenMessage.chatOpen);
            });
            return null;
        }
    }
}
