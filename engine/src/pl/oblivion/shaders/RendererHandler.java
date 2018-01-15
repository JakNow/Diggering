package pl.oblivion.shaders;

import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.oblivion.base.Model;
import pl.oblivion.base.TexturedMesh;
import pl.oblivion.lighting.Light;
import pl.oblivion.lighting.PointLight;

public abstract class RendererHandler {

	protected Light defaultLight = new PointLight(new Vector3f(0,0,0),new Vector4f(1.0f));

	protected int[] bindingAttributes;

	protected RendererHandler() {
	}

	public abstract void delete();

	public abstract void prepareModel(TexturedMesh texturedMesh);

	public abstract void prepareInstance(Model model);

	public abstract void unbindTexturedMesh(TexturedMesh texturedMesh);

	public abstract void processModel(Model model);
}
