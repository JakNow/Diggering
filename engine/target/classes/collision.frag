#version 400
out vec4 outColour;

uniform vec4 meshColour;


void main(void) {
    outColour = meshColour;
}
