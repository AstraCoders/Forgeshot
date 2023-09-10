package com.lucasmellof.forgeshot;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public enum CommonRes {
	HD(1280, 720, "HD (720p)"),
	FULL_HD(1920, 1080, "Full HD (1080p)"),
	QUAD_HD(2560, 1440, "Quad HD (1440p)"),
	ULTRA_HD(3840, 2160, "4K (2160p)"),
	EIGHT_K(7680, 4320, "8K (4320p)"),
	SIXTEEN_K(15360, 8640, "16K (8640p)");
	private final int width;
	private final int height;
	private final String name;

	CommonRes(int width, int height, String name) {
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static CommonRes getRes(int width, int height) {
		for (CommonRes res : values()) {
			if (res.width == width && res.height == height) {
				return res;
			}
		}
		return ULTRA_HD;
	}

	public String getName() {
		return name;
	}
}
