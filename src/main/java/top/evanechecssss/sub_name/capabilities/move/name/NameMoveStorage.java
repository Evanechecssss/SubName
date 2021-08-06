package top.evanechecssss.sub_name.capabilities.move.name;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class NameMoveStorage implements Capability.IStorage<INameMove> {

    @Override
    public NBTBase writeNBT(Capability<INameMove> capability, INameMove instance, EnumFacing side) {
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setFloat("high", instance.getHigh());
        tagCompound.setFloat("horizon", instance.getHorizon());
        return tagCompound;
    }

    @Override
    public void readNBT(Capability<INameMove> capability, INameMove instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.set(tag.getFloat("high"), tag.getFloat("horizon"));
    }
}
