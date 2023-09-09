package com.lucasmellof.forgeshot.callbacks;

import org.lwjgl.stb.STBIWriteCallback;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.WritableByteChannel;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class WriteCallback extends STBIWriteCallback implements AutoCloseable, Closeable {
	private final WritableByteChannel channel;
	private IOException exception;

	public WriteCallback(WritableByteChannel channel) {
		this.channel = channel;
	}

	@Override
	public void invoke(long context, long data, int size) {
		if (this.exception != null) {
			return;
		}
		var buffer = STBIWriteCallback.getData(data, size);
		try {
			channel.write(buffer);
		} catch (IOException e) {
			this.exception = e;
		}
	}

	@Override
	public void close() {
		this.free();
	}

	public IOException exception() {
		return exception;
	}
}
