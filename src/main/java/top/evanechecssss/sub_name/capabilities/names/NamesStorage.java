package top.evanechecssss.sub_name.capabilities.names;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class NamesStorage implements Capability.IStorage<INames> {
    @Override
    public NBTBase writeNBT(Capability<INames> capability, INames instance, EnumFacing side) {
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("name", instance.getName());
        tagCompound.setString("sub", instance.getSubName());
        tagCompound.setBoolean("showname", instance.getShowName());
        tagCompound.setBoolean("showsub", instance.getShowSubName());
        return tagCompound;
    }

    @Override
    public void readNBT(Capability<INames> capability, INames instance, EnumFacing side, NBTBase nbt) {
        final NBTTagCompound tag = (NBTTagCompound) nbt;
        instance.set(tag.getString("name"), tag.getString("sub"), tag.getBoolean("showname"), tag.getBoolean("showsub"));
    }
}
