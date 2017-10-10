package pl.oblivion.main;

import org.joml.Vector3f;
import pl.oblivion.assimp.StaticMeshLoader;
import pl.oblivion.base.ModelView;
import pl.oblivion.components.moveable.MoveComponent;
import pl.oblivion.components.moveable.RotateComponent;
import pl.oblivion.core.SimpleApp;
import pl.oblivion.staticModels.StaticModel;
import pl.oblivion.staticModels.StaticRenderer;

import java.io.File;

public class Main extends SimpleApp {


    public Main() {
        StaticRenderer staticRenderer = new StaticRenderer(window);
        rendererHandler.addRendererProgram(staticRenderer);


        String fileName = "core/resources/assets/models/sample_body.obj";
        File file = new File(fileName);
        ModelView test = null;
        try {
            test = StaticMeshLoader.load(file.getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StaticModel staticModel = new StaticModel(new Vector3f(0, 0, -1), new Vector3f(0, 0, 0), 1, test);

        staticModel.addComponent(new RotateComponent(staticModel));
        staticModel.getComponent(RotateComponent.class).setRotateSpeed(1.0f);

        staticModel.addComponent(new MoveComponent(staticModel, 0.5f));

        staticRenderer.getRendererHandler().setStaticModel(staticModel);

    }

    public static void main(String[] args) {
        new Main().run();
    }

    @Override
    public void input() {


    }

    @Override
    public void logicUpdate(float delta) {

    }
}
