package pl.oblivion.base;


public class ModelPart {

    private final TexturedMesh[] texturedMeshes;
    private final float furthestPoint;

    public ModelPart(TexturedMesh... texturedMeshes) {
        this.texturedMeshes = texturedMeshes;

        float currentFurthestPoint = 0;
        for(TexturedMesh texturedMesh : texturedMeshes){
            if(texturedMesh.getFurthestPoint()>currentFurthestPoint){
                currentFurthestPoint = texturedMesh.getFurthestPoint();
            }
        }
        this.furthestPoint = currentFurthestPoint;
    }

    public TexturedMesh[] getTexturedMeshes() {
        return texturedMeshes;
    }

    public float getFurthestPoint() {
        return furthestPoint;
    }
}
