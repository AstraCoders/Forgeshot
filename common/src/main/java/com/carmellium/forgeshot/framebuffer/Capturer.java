package com.carmellium.forgeshot.framebuffer;

import com.carmellium.forgeshot.Mine;
import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class Capturer {

	public static final int CHANNEL_COUNT = 3; // RGB
	public static final int BYTES_PER_PIXEL = CHANNEL_COUNT;

	private final ByteBuffer buffer;
	private final Dimension dimension;

	public Capturer() {
		this.dimension = getCurrentDimension();
		this.buffer = ByteBuffer.allocateDirect(dimension.width() * dimension.height() * BYTES_PER_PIXEL);
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public Dimension getDimension() {
		return dimension;
	}

	private Dimension getCurrentDimension() {
		return new Dimension(Mine.getWidth(), Mine.getHeight());
	}

	public void capture() {
		// check if the dimensions are still the same
		Dimension dim1 = getCurrentDimension();
		Dimension dim2 = getDimension();
		if (!dim1.equals(dim2)) {
			throw new IllegalStateException(String.format("Display size changed! %s != %s", dim1, dim2));
		}
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		Mine.writeToBuffer(buffer, BYTES_PER_PIXEL);
		buffer.rewind();
	}
}
