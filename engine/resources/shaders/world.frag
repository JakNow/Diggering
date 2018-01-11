#version 400

in vec2 pass_textures;
in vec3 surfaceNormal;
in vec4 worldPosition;
in vec3 toCameraVector;

out vec4 outColour;

struct Material
{
    vec4 ambientColour;
    vec4 diffuseColour;
    vec4 specularColour;

    int hasTexture;
    int hasNormalMap;

    float reflectivity;
    float shininess;
};

struct Light
{
    vec3 position;
    vec4 colour;
};

uniform sampler2D diffuseTexture;
uniform sampler2D normalTexture;
uniform Material material;
uniform Light light;
uniform float brightness;

vec4 ambientC;
vec4 diffuseC;
vec4 speculrC;

void setupColours(Material material, vec2 textCoord){
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

vec4 calculateLighting(Light light, vec4 diffuseColour){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLight = normalize(light.position-worldPosition.xyz);

    float brightness = max(dot(unitNormal,unitLight),brightness);

    vec3 unitVectorToCamera = normalize(toCameraVector);

    vec3 reflectedLightDirection = reflect(-unitLight,unitNormal);

    float specularFactor = max(dot(reflectedLightDirection,unitVectorToCamera),0.1);
    float dampedFactor = pow(specularFactor,material.shininess);

    vec4 finalSpecular = dampedFactor * material.reflectivity * light.colour;
    return brightness * light.colour * diffuseColour + finalSpecular;
}

void main(void) {
    setupColours(material,pass_textures);

    outColour = calculateLighting(light,diffuseC);
}
