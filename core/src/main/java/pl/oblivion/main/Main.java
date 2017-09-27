package pl.oblivion.main;

import pl.oblivion.core.SimpleApp;
import pl.oblivion.materials.Texture;
import pl.oblivion.objParser.ObjData;
import pl.oblivion.objParser.ObjParser;
import pl.oblivion.staticModels.StaticRenderer;
import pl.oblivion.utils.MyFile;

public class Main extends SimpleApp {

    private Texture texture;

    public Main() {
        texture = Texture.loadTexture("diffuseTexture.png");
        ObjData objData =  ObjParser.loadObjFile("test_cube.obj");

        new StaticRenderer(texture);


      objData.getModel();
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
