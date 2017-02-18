package com.naver.wysohn2002.mythicmobcreator.constants;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.constants.mobs.Drops;
import com.naver.wysohn2002.mythicmobcreator.util.ClassSerializer;

public class DropTables implements ConfigurationSerializable {
    public Drops Drops;

    //////////////////////////////////////////////////////////////////
    public static Mobs deserialize(Map<String, Object> ser){
        try {
            return (Mobs) ClassSerializer.deserialize(Mobs.class, ser);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public Map<String, Object> serialize() {
        try {
            return ClassSerializer.serialize(this);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
