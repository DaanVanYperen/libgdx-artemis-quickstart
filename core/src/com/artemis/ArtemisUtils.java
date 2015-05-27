package com.artemis;

/**
 * @author Daan van Yperen
 */
public class ArtemisUtils {

	/** Temporary fix to provide package local access to createFactory. */
    public static <T extends EntityFactory> T createFactory(World world, Class<?> factory) {
	    return world.createFactory(factory);
    }
}
