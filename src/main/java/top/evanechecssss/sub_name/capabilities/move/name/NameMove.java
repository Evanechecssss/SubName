package top.evanechecssss.sub_name.capabilities.move.name;

import net.minecraft.entity.Entity;
import top.evanechecssss.sub_name.SubName;
import top.evanechecssss.sub_name.network.NameMoveNetwork;

public class NameMove implements INameMove {
    private float high = 0;
    private float horizon = 0;

    @Override
    public void setHigh(float high) {
        this.high = high;
    }

    @Override
    public void setHorizon(float horizon) {
        this.horizon = horizon;
    }


    @Override
    public float getHorizon() {
        return horizon;
    }

    @Override
    public float getHigh() {
        return high;
    }

    @Override
    public void set(float high, float horizon) {
        this.high = high;
        this.horizon = horizon;
    }

    public static void synchronize(Entity entity) {
        INameMove handler = getHandler(entity);
        SubName.getWrapper().sendToAll(new NameMoveNetwork.NameMoveMessage(handler.getHorizon(), handler.getHigh(), entity.getUniqueID().toString()));
    }


    public static void setHighToEntity(Entity entity, float high) {
        INameMove handler = NameMove.getHandler(entity);
        handler.setHigh(high);
    }

    public static void setHorizonToEntity(Entity entity, float horizon) {
        INameMove handler = NameMove.getHandler(entity);
        handler.setHorizon(horizon);
    }

    public static float getHighFromEntity(Entity entity) {
        INameMove handler = NameMove.getHandler(entity);
        return handler.getHigh();
    }

    public static float getHorizonFromEntity(Entity entity) {
        INameMove handler = NameMove.getHandler(entity);
        return handler.getHorizon();
    }

    public static INameMove getHandler(Entity entity) {
        if (entity.hasCapability(NameMoveProvider.NAME_MOVE_CAP, null))
            return entity.getCapability(NameMoveProvider.NAME_MOVE_CAP, null);
        return null;
    }
}
