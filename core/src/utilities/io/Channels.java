package utilities.io;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Channels {
    public static final String DEFAULT_CHANNEL = "@__DEFAULT_CHANNEL__";

    private static final HashMap<String, Set<Audio>> channels = new HashMap<>();

    public static void register(String channel, Audio audio) {
        channels.computeIfAbsent(channel, k -> new HashSet<>()).add(audio);
    }

    public static float getChannelVolume(String channel) {
        if (channels.containsKey(channel)) {
            Object[] audios = channels.get(channel).toArray();
            if (audios.length == 0) {
                return 1f;
            } else {
                Audio first = (Audio) audios[0];
                return first.isNull() ? 1f : first.getVolume();
            }
        } else {
            throw new RuntimeException("Channel \"" + channel + "\" doesn't exists.");
        }
    }

    public static void setChannelVolume(String channel, float volume) {
        if (!channels.containsKey(channel)) {
            channels.computeIfAbsent(channel, k -> new HashSet<>());
        }

        Set<Audio> audios = channels.get(channel);
        audios.forEach(a -> a.setVolume(volume));
    }
}
