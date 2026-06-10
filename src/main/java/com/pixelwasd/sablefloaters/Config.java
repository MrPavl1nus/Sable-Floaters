package com.pixelwasd.sablefloaters;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.DoubleValue FLOATER_FORCE = BUILDER
            .comment("A floater force")
            .defineInRange("floaterForce", 1, 0, Double.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
