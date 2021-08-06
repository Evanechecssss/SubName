package top.evanechecssss.sub_name.capabilities.names;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class NamesProvider implements ICapabilitySerializable<NBTTagCompound> {
    @CapabilityInject(INames.class)
    public static final Capability<INames> NAMES_CAP = null;

    private INames instance = NAMES_CAP.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == NAMES_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? NAMES_CAP.<T>cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) NAMES_CAP.getStorage().writeNBT(NAMES_CAP, this.instance, null);
    }


    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NAMES_CAP.getStorage().readNBT(NAMES_CAP, this.instance, null, nbt);
    }
}
