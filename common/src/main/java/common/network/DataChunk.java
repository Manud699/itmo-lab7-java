package common.network;

import java.io.Serializable;

public record DataChunk(int size, int current, byte[]payload) implements Serializable {
}
