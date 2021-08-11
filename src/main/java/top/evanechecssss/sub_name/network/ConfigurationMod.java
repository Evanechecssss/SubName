package top.evanechecssss.sub_name.network;

import net.minecraftforge.common.config.Config;
import top.evanechecssss.sub_name.ModInfo;

@Config(modid = ModInfo.MODID,type = Config.Type.INSTANCE,name = ModInfo.MODID + "_conf")
public class ConfigurationMod {
    @Config.LangKey("sub_name.config.render")
    @Config.Comment("Use it for review your name display")
    public static boolean doRenderC = false;
}
