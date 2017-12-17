package pl.oblivion.base;

public abstract class ModelView {

	private final ModelPart[] modelParts;
	private final float furthestPoint;

	public ModelView(ModelPart... modelParts) {
		this.modelParts = modelParts;
		float currentFurthestPoint = 0;
		for (ModelPart modelPart : modelParts) {
			if (modelPart.getFurthestPoint() > currentFurthestPoint) {
				currentFurthestPoint = modelPart.getFurthestPoint();
			}
		}
		this.furthestPoint = currentFurthestPoint;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

	public ModelPart[] getModelParts() {
		return modelParts;
	}
}
