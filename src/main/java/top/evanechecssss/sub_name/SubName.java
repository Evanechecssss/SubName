package top.evanechecssss.sub_name;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import top.evanechecssss.sub_name.common.CommonProxy;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SubName
{
    private static Logger logger;
    private static SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("sub_name");
    @SidedProxy(clientSide = "top.evanechecssss.sub_name.client.ClientProxy",serverSide = "top.evanechecssss.sub_name.common.CommonProxy")
    private static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("PRE INIT");
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("INIT");
        proxy.init(event);
    }

    @EventHandler
    public void server(FMLServerStartingEvent event){
        logger.info("SERVER");
        proxy.server(event);
    }

    public static Logger getLogger(){
        return logger;
    }
    public static SimpleNetworkWrapper getWrapper(){
        return wrapper;
    }
}
