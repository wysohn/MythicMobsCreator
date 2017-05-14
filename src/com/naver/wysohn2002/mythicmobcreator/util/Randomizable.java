package com.naver.wysohn2002.mythicmobcreator.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.logging.Level;

import com.naver.wysohn2002.mythicmobcreator.main.Main;

public abstract class Randomizable<T> {
    protected static final Random random = new Random();

    public abstract T createInstance();
    protected CustomizedRandom customizedRandom;

    public void randomize() {
        for(Field field : getClass().getFields()){
            RandomLimit limit = field.getAnnotation(RandomLimit.class);

            if(customizedRandom != null && customizedRandom.isCustomRandom(field.getType())){
                Object value = customizedRandom.onRandomize(field.getType());
                try {
                    field.set(this, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(field.getType() == Number.class){
                if(field.getType() == Integer.class){
                    int min = 0, max = 100;
                    if(limit != null){
                        min = limit.intMin(); max = limit.intMax();
                    }

                    try {
                        field.set(this, getRandomInt(min, max));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        Main.LOGGER.log(Level.WARNING, e.getMessage(), e);
                    }

                }else{
                    double min = 0.0, max = 100.0;
                    if(limit != null){
                        min = limit.doubleMin(); max = limit.doubleMax();
                    }

                    try {
                        field.set(this, getRandomDouble(min, max));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        Main.LOGGER.log(Level.WARNING, e.getMessage(), e);
                    }
                }
            }else if(field.getType().isEnum()){
                Class<? extends Enum> clazz = (Class<? extends Enum<?>>) field.getType();
                Enum<?>[] enums = clazz.getEnumConstants();

                try {
                    field.set(this, enums[random.nextInt(enums.length)]);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(field.getType() == Boolean.class){
                boolean bool = false;
                if(limit != null)
                    bool = limit.bool();
                else
                    bool = random.nextBoolean();

                try {
                    field.set(this, bool);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }else if(Randomizable.class.isAssignableFrom(field.getType())){
                try {
                    Randomizable randomizableValue = (Randomizable) field.get(this);
                    if(randomizableValue == null)
                        randomizableValue = (Randomizable) field.getType().newInstance();

                    randomizableValue.randomize();
                    field.set(this, randomizableValue);
                } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }else{
                //ignore
            }
        }
    }

    private static int getRandomInt(int from, int to){
        return from + random.nextInt(to - from);
    }

    private static double getRandomDouble(double from, double to){
        return from + random.nextDouble() * (to - from);
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RandomLimit{
        int intMin() default 0;
        int intMax() default 100;
        double doubleMin() default 0.0;
        double doubleMax() default 1.0;
        boolean bool() default false;
    }

    public interface CustomizedRandom{
        boolean isCustomRandom(Class<?> clazz);
        Object onRandomize(Class<?> clazz);
    }
}
