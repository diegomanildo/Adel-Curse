package utilities.audio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public final class Channels {
    public static final String DEFAULT_CHANNEL = "Default";
    public static final String GLOBAL_CHANNEL = "Global";

    private static final Map<String, HashSet<Audio>> audioChannels = new HashMap<>();
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
            updateVolume();
        }
    }

    public static void updateVolume() {
        audioChannels.forEach((channelName, audios) -> {
            float volume = getChannelVolume(channelName) * volumeChannels.get(GLOBAL_CHANNEL);
            if (audios != null) {
                for (Audio audio : audios) {
                    audio.setVolume(volume);
                }
            }
        });
    }

    public static float getChannelVolume(String channel) {
        return volumeChannels.getOrDefault(channel, 1.0f);
    }
}
