package top.evanechecssss.sub_name.common;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import org.jetbrains.annotations.Nullable;
import top.evanechecssss.sub_name.capabilities.move.name.NameMove;
import top.evanechecssss.sub_name.capabilities.move.sub.SubMove;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MoveNamesCommand extends CommandBase {

    @Override
    public String getName() {
        return "names_move";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getAliases() {
        return Lists.newArrayList("nm", "move_names", "name_pos");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> empty = new java.util.ArrayList<>(Collections.emptyList());
        empty.add("sub");
        empty.add("sb");
        empty.add("n");
        empty.add("name");
        if (args.length == 1) {
            empty.addAll(Arrays.asList(server.getOnlinePlayerNames()));
            return empty;
        }
        if (args.length >= 3) {
            return Collections.emptyList();
        }
        return empty;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "sub_name.usage.move";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String selector = args[0];
        try {
            List<Entity> entityList = getEntityList(server, sender, selector);
            executeForEntities(entityList, getOperation(args[1]), tryGetHigh(args[2], sender), tryGetHorizon(args[3], sender));
        } catch (EntityNotFoundException e) {
            if (sender instanceof EntityPlayer) {
                executeForSender((EntityPlayer) sender, getOperation(args[0]), tryGetHigh(args[1], sender), tryGetHorizon(args[2], sender));
            } else {
                sender.sendMessage(new TextComponentTranslation("sub_name.argumentException.sender"));
            }
        }

    }

    private float tryGetHigh(String high, ICommandSender sender) {
        try {
            return Float.parseFloat(high);
        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentTranslation("sub_name.argumentException.float", high));

            getUsage(sender);
            return 0;
        }
    }

    private float tryGetHorizon(String horizon, ICommandSender sender) {
        try {
            return Float.parseFloat(horizon);
        } catch (NumberFormatException e) {
            sender.sendMessage(new TextComponentTranslation("sub_name.argumentException.float", horizon));
            getUsage(sender);
            return 0;
        }
    }

    private CommandOperation getOperation(String operation) {
        switch (operation) {
            case "sub":
            case "s":
                return CommandOperation.SUB;
            case "name":
            case "n":
                return CommandOperation.NAME;
            default:
                return CommandOperation.UNDEFINED;
        }
    }

    private void executeForEntities(List<Entity> entityList, CommandOperation operation, float high, float horizon) {
        entityList.forEach(entity -> {
            switch (operation) {
                case SUB:
                    SubMove.setHighToEntity(entity, high);
                    SubMove.setHorizonToEntity(entity, horizon);
                    SubMove.synchronize(entity);
                    return;
                case NAME:
                    NameMove.setHighToEntity(entity, high);
                    NameMove.setHorizonToEntity(entity, horizon);
                    NameMove.synchronize(entity);
                    return;
                case UNDEFINED:
                    getUsage(entity);
            }
        });

    }

    private void executeForSender(EntityPlayer player, CommandOperation operation, float high, float horizon) {
        switch (operation) {
            case SUB:
                SubMove.setHighToEntity(player, high);
                SubMove.setHorizonToEntity(player, horizon);
                SubMove.synchronize(player);
                return;
            case NAME:
                NameMove.setHighToEntity(player, high);
                NameMove.setHorizonToEntity(player, horizon);
                NameMove.synchronize(player);
                return;
            case UNDEFINED:
                getUsage(player);
        }
    }

    private enum CommandOperation {
        SUB, NAME, UNDEFINED;

        CommandOperation() {
        }
    }
}
