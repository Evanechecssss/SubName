package top.evanechecssss.sub_name.capabilities.move.name;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class NameMoveProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(INameMove.class)
    public static final Capability<INameMove> NAME_MOVE_CAP = null;

    private INameMove instance = NAME_MOVE_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == NAME_MOVE_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? NAME_MOVE_CAP.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) NAME_MOVE_CAP.getStorage().writeNBT(NAME_MOVE_CAP, this.instance, null);
    }


    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NAME_MOVE_CAP.getStorage().readNBT(NAME_MOVE_CAP, this.instance, null, nbt);
    }
}
