#version 400

layout(location = 0)in vec3 in_position;
layout(location = 1)in vec2 in_textures;
layout(location = 2)in vec3 in_normal;

out vec2 pass_textures;
out vec3 surfaceNormal;
out vec4 worldPosition;
out vec3 toCameraVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;


void main() {
    worldPosition = transformationMatrix*vec4(in_position, 1.0);

	gl_Position = projectionMatrix*viewMatrix*worldPosition;
	pass_textures = in_textures;

	surfaceNormal = (transformationMatrix * vec4(in_normal,0.0)).xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
}
