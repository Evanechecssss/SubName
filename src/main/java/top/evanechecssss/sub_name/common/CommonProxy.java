package top.evanechecssss.sub_name.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import top.evanechecssss.sub_name.SubName;
import top.evanechecssss.sub_name.capabilities.move.name.INameMove;
import top.evanechecssss.sub_name.capabilities.move.name.NameMove;
import top.evanechecssss.sub_name.capabilities.move.name.NameMoveHandler;
import top.evanechecssss.sub_name.capabilities.move.name.NameMoveStorage;
import top.evanechecssss.sub_name.capabilities.move.sub.ISubMove;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMove;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMoveHandler;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMoveStorage;
import top.evanechecssss.sub_name.capabilities.names.INames;
import top.evanechecssss.sub_name.capabilities.names.Names;
import top.evanechecssss.sub_name.capabilities.names.NamesHandler;
import top.evanechecssss.sub_name.capabilities.names.NamesStorage;
import top.evanechecssss.sub_name.client.RenderHandler;
import top.evanechecssss.sub_name.network.NameMoveNetwork;
import top.evanechecssss.sub_name.network.NamesNetwork;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new NamesHandler());
        MinecraftForge.EVENT_BUS.register(new RenderHandler());
        MinecraftForge.EVENT_BUS.register(new NameMoveHandler());
        MinecraftForge.EVENT_BUS.register(new SubMoveHandler());
        CapabilityManager.INSTANCE.register(INames.class, new NamesStorage(), Names.class);
        CapabilityManager.INSTANCE.register(INameMove.class, new NameMoveStorage(), NameMove.class);
        CapabilityManager.INSTANCE.register(ISubMove.class, new SubMoveStorage(), SubMove.class);
        SubName.getWrapper().registerMessage(NamesNetwork.NamesMessageHandler.class, NamesNetwork.NamesMessage.class, 0, Side.CLIENT);
        SubName.getWrapper().registerMessage(NamesNetwork.RefreshDisplayNameMessageHandler.class, NamesNetwork.RefreshDisplayNameMessage.class, 1, Side.CLIENT);
        SubName.getWrapper().registerMessage(NamesNetwork.ChangeNameMessageHandler.class, NamesNetwork.ChangeNameMessage.class, 2, Side.CLIENT);
        SubName.getWrapper().registerMessage(NameMoveNetwork.NameMoveMessageHandler.class, NameMoveNetwork.NameMoveMessage.class, 3, Side.CLIENT);
        SubName.getWrapper().registerMessage(NameMoveNetwork.SubMoveMessageHandler.class, NameMoveNetwork.SubMoveMessage.class, 4, Side.CLIENT);

    }
    public void init(FMLInitializationEvent event) {
    }

    public void server(FMLServerStartingEvent event) {
        event.registerServerCommand(new SetNameCommand());
        event.registerServerCommand(new MoveNamesCommand());
    }
}
