package dev.aullisia.pmmsc.util;

import com.mojang.serialization.Codec;

import java.util.UUID;

public class UuidCodec {
    public static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
}
