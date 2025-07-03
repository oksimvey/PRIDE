package com.robson.pride.api.data.player;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class ClientSavedData {

    public static final byte Strength = 0;
    public static final byte Dexterity = 1;
    public static final byte Vigor = 2;
    public static final byte Endurance = 3;
    public static final byte Mind = 4;

    private byte[] lvl;

    private int[] xp;

    private byte[] skillList;

    private byte element;

    private String mountid;

    private String[] allies;

    private ClientSavedData(byte[] lvl, int[] xp, byte[] skillList, byte element, String mountid) {
       this.lvl = lvl;
       this.xp = xp;
       this.skillList = skillList;
       this.element = element;
       this.mountid = mountid;
    }

    public byte getTotalLevel() {
        byte total = 0;
        for (byte lvl : this.lvl) {
            total += lvl;
        }
        return (byte) (total / this.lvl.length);
    }

    public static ClientSavedData fromNBT(CompoundTag tag) {
        if (tag != null) {
            return new ClientSavedData(
                   tag.getByteArray("lvl"),
                    tag.getIntArray("xp"),
                    tag.getByteArray("skills"),
                    tag.getByte("element"),
                    tag.getString("mount"));
        }
        return null;
    }

    public byte getLevel(byte stat) {
        return this.lvl[stat];
    }

    public void addXP(byte stat, int amount) {
        this.xp[stat] += amount;
        while (getXP(stat) >= getMaxXP(stat)) {
            xp[stat] -= getMaxXP(stat);
            lvl[stat] += 1;
            if (lvl[stat] >= 100) {
                lvl[stat] = 100;
                xp[stat] = 0;
            }
        }
    }

    public void setMount(String id){
        this.mountid =  id;
    }

    public String getMount(){
        return this.mountid;
    }


    public void setXP(byte stat, int amount) {
        this.xp[stat] = amount;
    }

    public byte getElement() {
        return this.element;
    }

    public int getXP(byte stat) {
        return this.xp[stat];
    }

    public int getMaxXP(byte stat) {
        return Math.toIntExact(Math.round(100 * Math.pow(1.05, (getLevel(stat) - 1))));
    }

    public byte[] getSkillList() {
        return this.skillList;
    }

    public void setLevel(byte stat, byte level) {
        this.lvl[stat] = level;
    }

    public static CompoundTag toNBT(ClientSavedData data) {
        CompoundTag tag = new CompoundTag();
        tag.putByteArray("lvl", data.lvl);
        tag.putIntArray("xp", data.xp);
        tag.putByteArray("skills", data.skillList);
        tag.putByte("element", data.element);
        tag.putString("mount", data.mountid);
        return tag;
    }
}
