package pl.oblivion.staticModels;

import pl.oblivion.base.ModelPart;
import pl.oblivion.base.ModelView;

public class StaticModelView extends ModelView {

	private final ModelPart[] modelParts;
	private final float furthestPoint;

	public StaticModelView(ModelPart... modelParts) {
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
