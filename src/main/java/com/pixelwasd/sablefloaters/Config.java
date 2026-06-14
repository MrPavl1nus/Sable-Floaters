package com.pixelwasd.sablefloaters;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
        private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        public static final ModConfigSpec.DoubleValue GENERAL_FLOATERS_FORCE = BUILDER
                .comment("A floater force")
                .translation("sablefloaters.config.general_floater_force")
                .defineInRange("floaterForce", 1, 0, Double.MAX_VALUE);

        public static final ModConfigSpec.DoubleValue FLOATER_DAMPING_FORCE = BUILDER
                .comment("A floater's damper force(in development)")
                .translation("sablefloaters.config.floater_damping_force")
                .defineInRange("floaterDamperForce", 2, 0, Double.MAX_VALUE);

        public static final ModConfigSpec.DoubleValue FLOATER_MAX_LOAD = BUILDER
                .comment("A floater's max value of load")
                .translation("sablefloaters.config.floater_max_load")
                .defineInRange("floaterMaxLoad", 10, 0, Double.MAX_VALUE);

        public static final ModConfigSpec.BooleanValue LOGGING = BUILDER
                .comment("logging(for debug only)")
                .translation("sablefloaters.config.logging")
                .define("logging", false);

        public static final ModConfigSpec.DoubleValue DEPTH_OFFSET = BUILDER
                .comment("A offset for calculation depth of block")
                .translation("sablefloaters.config.depthOffset")
                .defineInRange("depthOffset", 0.0, 0, 1);

        static final ModConfigSpec SPEC = BUILDER.build();
}
