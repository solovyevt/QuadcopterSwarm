#version 120

varying vec2 uv;
varying vec3 color;

void main() {
	gl_FragColor = vec4(color, 1.0);
}