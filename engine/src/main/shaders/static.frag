#version 400

in vec2 pass_textures;

out vec4 outColour;

uniform sampler2D diffuseTexture;

void main(void) {
	outColour = texture(diffuseTexture,pass_textures);
}
