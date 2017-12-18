#version 400

layout(location = 0)in vec3 in_position;

uniform mat4 projectionMatrix;
uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

void main() {
	gl_Position = projectionMatrix*viewMatrix*transformationMatrix*vec4(in_position, 1.0);
}
