package view.scene;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class Texture {
	private int textureID = -1;
	
	public Texture(int[] pixels, int width, int height) {
		writeGPU(width, height, pixels);
	}
	
	public Texture(BufferedImage image) {
		this(image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth()), image.getWidth(), image.getHeight());
	}
	
	public void bind() {
		if (textureID == -1) {
			throw new IllegalStateException("Cannot use an unloaded texture.");
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
	}
	
	public static void unbind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public void writeGPU(int width, int height, int[] pixels) {
		if (textureID != -1) {
			throw new IllegalStateException("Cannot load an already loaded texture.");
		}
		
		textureID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_BASE_LEVEL, 0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL12.GL_TEXTURE_MAX_LEVEL, 0);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
		for (int y=0 ; y<height ; y++) {
			for (int x=0 ; x<width ; x++) {
				Color pixel = new Color(pixels[y * width + x], true);
				buffer.put((byte) pixel.getRed());
				buffer.put((byte) pixel.getGreen());
				buffer.put((byte) pixel.getBlue());
				buffer.put((byte) pixel.getAlpha());
			}
		}
		buffer.flip();
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}
}
