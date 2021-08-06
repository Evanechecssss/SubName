package top.evanechecssss.sub_name.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import top.evanechecssss.sub_name.capabilities.move.name.NameMove;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMove;

public class NameMoveNetwork {

    public static class NameMoveMessage implements IMessage {
        public NameMoveMessage() {
        }

        public float high;
        public float horizon;
        public String trueID;

        public NameMoveMessage(float horizon, float high, String trueID) {
            this.high = high;
            this.horizon = horizon;
            this.trueID = trueID;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.high = buf.readFloat();
            this.horizon = buf.readFloat();
            this.trueID = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeFloat(this.high);
            buf.writeFloat(this.horizon);
            ByteBufUtils.writeUTF8String(buf, this.trueID);
        }
    }

    public static class NameMoveMessageHandler implements IMessageHandler<NameMoveMessage, IMessage> {

        @SideOnly(Side.CLIENT)
        public static void doSynchronize(float high, float horizon, String trueID) {
            Minecraft.getMinecraft().player.getEntityWorld().loadedEntityList.forEach(entity -> {
                if (entity.getUniqueID().toString().equals(trueID)) {
                    NameMove.setHighToEntity(entity, high);
                    NameMove.setHorizonToEntity(entity, horizon);
                }
            });
        }

        @Override
        public IMessage onMessage(NameMoveMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                doSynchronize(message.high, message.horizon, message.trueID);
            });
            return null;
        }

    }

    public static class SubMoveMessage implements IMessage {
        public SubMoveMessage() {
        }

        public float high;
        public float horizon;
        public String trueID;

        public SubMoveMessage(float horizon, float high, String trueID) {
            this.high = high;
            this.horizon = horizon;
            this.trueID = trueID;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            this.high = buf.readFloat();
            this.horizon = buf.readFloat();
            this.trueID = ByteBufUtils.readUTF8String(buf);
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeFloat(this.high);
            buf.writeFloat(this.horizon);
            ByteBufUtils.writeUTF8String(buf, this.trueID);
        }
    }

    public static class SubMoveMessageHandler implements IMessageHandler<SubMoveMessage, IMessage> {

        @SideOnly(Side.CLIENT)
        public static void doSynchronize(float high, float horizon, String trueID) {
            Minecraft.getMinecraft().player.getEntityWorld().loadedEntityList.forEach(entity -> {
                if (entity.getUniqueID().toString().equals(trueID)) {
                    SubMove.setHighToEntity(entity, high);
                    SubMove.setHorizonToEntity(entity, horizon);
                }
            });
        }

        @Override
        public IMessage onMessage(SubMoveMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                doSynchronize(message.high, message.horizon, message.trueID);
            });
            return null;
        }
    }
}
