package top.evanechecssss.sub_name.common;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import org.jetbrains.annotations.Nullable;
import top.evanechecssss.sub_name.capabilities.names.Names;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SetNameCommand extends CommandBase {

    @Override
    public String getName() {
        return "names_change";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> empty = Collections.emptyList();
        List<String> subList = Lists.newArrayList("");
        {
            subList.add("sub");
            subList.add("s");
        }
        List<String> nameList = Lists.newArrayList("");
        {
            nameList.add("name");
            nameList.add("n");
        }
        List<String> clearSubList = Lists.newArrayList("");
        {
            clearSubList.add("clear_sub");
            clearSubList.add("cls");
            clearSubList.add("clean-s");
        }
        List<String> clearNameList = Lists.newArrayList("");
        {
            clearNameList.add("clear_name");
            clearNameList.add("cln");
            clearNameList.add("clean-n");
        }
        List<String> infoNameList = Lists.newArrayList();
        {
            infoNameList.add("info");
            infoNameList.add("state");
        }
        List<String> visibleList = Lists.newArrayList();
        {
            visibleList.add("show_sub");
            visibleList.add("shs");
            visibleList.add("hds");
            visibleList.add("hide_sub");
            visibleList.add("show_name");
            visibleList.add("shn");
            visibleList.add("hdn");
            visibleList.add("hide_name");
        }
        Collection<String> collection = Lists.newArrayList();
        {
            collection.addAll(subList);
            collection.addAll(nameList);
            collection.addAll(clearNameList);
            collection.addAll(clearSubList);
            collection.addAll(infoNameList);
            collection.addAll(visibleList);
        }
        if (args.length == 1) {
            collection.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            return getListOfStringsMatchingLastWord(args, collection);
        } else if (args.length == 2) {
            if (clearNameList.contains(args[0])) {
                return empty;
            } else if (clearSubList.contains(args[0])) {
                return empty;
            } else if (subList.contains(args[0])) {
                return Lists.newArrayList(I18n.format("sub_name.compile.sub"));
            } else if (nameList.contains(args[0])) {
                return Lists.newArrayList(I18n.format("sub_name.compile.name"));
            }
            return getListOfStringsMatchingLastWord(args, collection);
        } else if (args.length == 3) {


            if (clearNameList.contains(args[1])) {
                return empty;
            } else if (clearSubList.contains(args[1])) {
                return empty;
            } else if (subList.contains(args[1])) {
                return Lists.newArrayList(I18n.format("sub_name.compile.sub"));
            } else if (nameList.contains(args[1])) {
                return Lists.newArrayList(I18n.format("sub_name.compile.name"));
            }


            return empty;
        }
        return empty;
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("cn", "change_names", "sub_names");
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "sub_name.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<Entity> entityList;
        try {
            entityList = getEntityList(server, sender, args[0]);
            entityList.forEach(entity -> {
                try {
                    executeForEntity(entity, getValueOfCommand(args, 2), getType(args[1]));
                } catch (CommandException e) {
                    getUsage(sender);
                }
            });
        } catch (EntityNotFoundException e) {
            if (sender instanceof EntityPlayer) {
                try {
                    executeForEntity((EntityPlayer) sender, getValueOfCommand(args, 1), getType(args[0]));

                } catch (CommandException exception) {
                    getUsage(sender);
                }
            } else {
                sender.sendMessage(new TextComponentTranslation("sub_name.argumentException.sender"));
            }
        }

    }

    private String getValueOfCommand(String[] args, int startIndex) {
        StringBuilder val = new StringBuilder();
        int f = args.length - 1;
        for (int i = startIndex; i < args.length; i++) {
            val.append(args[i].replace("&", "§"));
            if (i != f) {
                val.append(" ");
            }
        }
        return val.toString();
    }

    private NameCommandTypes getType(String arg) throws CommandException {
        switch (arg) {
            case "sub":
            case "s":
                return NameCommandTypes.SUB;
            case "name":
            case "n":
                return NameCommandTypes.NAME;
            case "clear_sub":
            case "cls":
            case "clean-s":
                return NameCommandTypes.CLEAN_SUB;
            case "clear_name":
            case "cln":
            case "clean-r":
                return NameCommandTypes.CLEAN_NAME;
            case "hide_sub":
            case "hds":
                return NameCommandTypes.HIDE_SUB;
            case "hide_name":
            case "hdn":
                return NameCommandTypes.HIDE_NAME;
            case "show_sub":
            case "shs":
                return NameCommandTypes.SHOW_SUB;
            case "show_name":
            case "shn":
                return NameCommandTypes.SHOW_MAME;
            case "state":
            case "info":
                return NameCommandTypes.INFO;
            default:
                throw new CommandException("sub_name.argumentException.sender");

        }
    }

    private void executeForEntity(Entity entity, String val, NameCommandTypes type) {

        switch (type) {
            case NAME:
                Names.setToHandler(val, Names.getSubName(entity), Names.getShowName(entity), Names.getShowSub(entity), entity);
                Names.synchronize(entity);
                if (entity instanceof EntityPlayer) {
                    Names.refreshName((EntityPlayerMP) entity);
                } else {
                    if (Names.getShowName(entity)) {
                        entity.setCustomNameTag(val);
                    } else {
                        entity.setCustomNameTag("");
                    }
                }
                return;
            case SUB:
                Names.setToHandler(Names.getDisplayName(entity), val, Names.getShowName(entity), Names.getShowSub(entity), entity);
                Names.synchronize(entity);
                return;
            case CLEAN_SUB:
                Names.cleanToHandler(false, entity);
                Names.synchronize(entity);
                return;
            case CLEAN_NAME:
                Names.cleanToHandler(true, entity);
                Names.synchronize(entity);
                if (entity instanceof EntityPlayer) {
                    Names.refreshName((EntityPlayerMP) entity);
                } else {
                    entity.setCustomNameTag(entity.getCustomNameTag());
                }
            case HIDE_NAME:
                Names.setShowName(entity, false);
                Names.synchronize(entity);
                if (entity instanceof EntityPlayer) {
                    Names.refreshName((EntityPlayerMP) entity);
                } else {
                    entity.setCustomNameTag("");
                }
                return;
            case HIDE_SUB:
                Names.setShowSub(entity, false);
                Names.synchronize(entity);
                return;
            case SHOW_SUB:
                Names.setShowSub(entity, true);
                Names.synchronize(entity);
                return;
            case SHOW_MAME:
                Names.setShowName(entity, true);
                Names.synchronize(entity);
                if (entity instanceof EntityPlayer) {
                    Names.refreshName((EntityPlayerMP) entity);
                }
                return;
            case INFO:
                entity.sendMessage(new TextComponentTranslation("sub_name.info1", entity.getName()));
                entity.sendMessage(new TextComponentTranslation("sub_name.info2", Names.getDisplayName(entity)));
                entity.sendMessage(new TextComponentTranslation("sub_name.info3", Names.getSubName(entity)));
                entity.sendMessage(new TextComponentTranslation("sub_name.info4", Boolean.toString(Names.getShowName(entity))));
                entity.sendMessage(new TextComponentTranslation("sub_name.info5", Boolean.toString(Names.getShowSub(entity))));
                return;
            default:
                entity.sendMessage(new TextComponentTranslation("sub_name.argumentException.invalid"));
                getUsage(entity);
        }
    }

    private enum NameCommandTypes {
        NAME,
        SUB,
        CLEAN_NAME,
        CLEAN_SUB,
        SHOW_MAME,
        HIDE_NAME,
        SHOW_SUB,
        HIDE_SUB,
        INFO;

        NameCommandTypes() {
        }
    }
}
