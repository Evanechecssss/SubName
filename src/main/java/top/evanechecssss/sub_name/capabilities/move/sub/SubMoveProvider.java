package top.evanechecssss.sub_name.capabilities.move.sub;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SubMoveProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(ISubMove.class)
    public static final Capability<ISubMove> SUB_MOVE_CAP = null;

    private ISubMove instance = SUB_MOVE_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SUB_MOVE_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? SUB_MOVE_CAP.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) SUB_MOVE_CAP.getStorage().writeNBT(SUB_MOVE_CAP, this.instance, null);
    }


    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        SUB_MOVE_CAP.getStorage().readNBT(SUB_MOVE_CAP, this.instance, null, nbt);
    }
}
