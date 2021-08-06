package top.evanechecssss.sub_name.capabilities.move.sub;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class SubMoveStorage implements Capability.IStorage<ISubMove> {

    @Override
    public NBTBase writeNBT(Capability<ISubMove> capability, ISubMove instance, EnumFacing side) {
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setFloat("sub_high", instance.getHigh());
        tagCompound.setFloat("sub_horizon", instance.getHorizon());
        return tagCompound;
    }

    @Override
    public void readNBT(Capability<ISubMove> capability, ISubMove instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.set(tag.getFloat("sub_high"), tag.getFloat("sub_horizon"));
    }
}
