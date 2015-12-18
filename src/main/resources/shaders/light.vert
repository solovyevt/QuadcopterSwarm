#version 120

uniform mat4 viewMatrix;

varying vec2 uv;
varying vec3 color;

void main() {
	vec3 lightPos = vec3(0, 0, 0);
	vec3 lightAmbient = vec3(0, 0, 0);
	
	vec3 vertexPos = (gl_ModelViewMatrix * gl_Vertex).xyz;
	vec3 lightDirection = normalize(lightPos - vertexPos);
	vec3 surfaceNormal = (gl_NormalMatrix * gl_Normal).xyz;
	
	float diffuseLightIntensity = max(0.5, dot(surfaceNormal, lightDirection));
	
	color.rgb = diffuseLightIntensity * gl_Color.rgb;
	color += lightAmbient;
	
	uv = vec2(gl_MultiTexCoord0);
	gl_Position = gl_ProjectionMatrix * viewMatrix * gl_ModelViewMatrix * gl_Vertex;
}