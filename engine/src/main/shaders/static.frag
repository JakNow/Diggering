#version 400

in vec2 pass_textures;
out vec4 outColour;

struct Material
{
    vec4 ambientColour;
    vec4 diffuseColour;
    vec4 specularColour;

    int hasTexture;
    int hasNormalMap;
};

uniform sampler2D diffuseTexture;
uniform sampler2D normalTexture;
uniform Material material;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

void setupColours(Material material, vec2 textCoord)
{
    if (material.hasTexture == 1){
        diffuseC = texture(diffuseTexture, textCoord);
        ambientC = diffuseC;
        speculrC = ambientC;
    }
    else{
        ambientC = material.ambientColour;
        diffuseC = material.diffuseColour;
        speculrC = material.specularColour;
    }
}

void main(void) {

    setupColours(material,pass_textures);

    outColour = vec4(diffuseC);
}
