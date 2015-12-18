package view.scene;

import utils.Vector;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;


public class Shader {
	private int program = -1;
	private Map<String,Integer> uniforms = new HashMap<>();
	private IntBuffer intBuffer = BufferUtils.createIntBuffer(100);
	private FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(100);
	
	public Shader(String vert, String frag) {
		program = ShaderLoader.loadShaderPair(vert, frag);
	}

	public int getUniform(String name) {
		if (uniforms.containsKey(name))
			return uniforms.get(name);
		int ret = GL20.glGetUniformLocation(program, name);
		uniforms.put(name, ret);
		return ret;
	}
	
	public void setUniform(String name, boolean transpose, Matrix4f matrix) {
		floatBuffer.position(0);
		floatBuffer.limit(16);
		matrix.store(floatBuffer);
		floatBuffer.position(0);
		GL20.glUniformMatrix4(getUniform(name), transpose, floatBuffer);
	}
	
	public void enable() {
		if (program != -1)
			GL20.glUseProgram(program);
	}
	
	public void disable() {
		GL20.glUseProgram(0);
	}
	
	public void dispose() {
		if (program!=-1)
			GL20.glDeleteProgram(program);
	}
}
