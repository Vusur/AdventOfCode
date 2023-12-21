package aoc.day20;

import aoc.day20.modules.Module.Pulse;

public record QueueItem(String originKey, String targetKey, Pulse pulse) {

}
