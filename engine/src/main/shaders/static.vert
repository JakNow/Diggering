#version 400

layout(location = 0)in vec3 in_position;
layout(location = 1)in vec2 in_textures;

out vec2 pass_textures;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main() {
	gl_Position = projectionMatrix*viewMatrix*transformationMatrix*vec4(in_position, 1.0);
	pass_textures = in_textures;
}
