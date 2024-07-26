package utilities.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Channels {
    public static final String DEFAULT_CHANNEL = "@__DEFAULT_CHANNEL__";
    public static final String GLOBAL_CHANNEL = "@__GLOBAL__";

    private static final Map<String, Set<Audio>> audioChannels = new HashMap<>();
    private static final Map<String, Float> volumeChannels = new HashMap<>();

    static {
        volumeChannels.put(GLOBAL_CHANNEL, 1.0f);
    }

    public static void register(String channel, Audio audio) {
        audioChannels.computeIfAbsent(channel, k -> new HashSet<>()).add(audio);
    }

    public static void setChannelVolume(String channel, float volume) {
        if (volume < 0f || volume > 1f) {
            throw new RuntimeException("Volume " + volume + " is invalid, range: (0.0 - 1.0)");
        } else {
            volumeChannels.put(channel, volume);
            updateVolume(channel);
        }
    }

    public static void updateVolume(String channel) {
        float volume = volumeChannels.getOrDefault(channel, 1.0f) * volumeChannels.get(GLOBAL_CHANNEL);

        Set<Audio> audios = audioChannels.get(channel);
        if (audios != null) {
            for (Audio audio : audios) {
                audio.setVolume(volume);
            }
        }
    }
}
