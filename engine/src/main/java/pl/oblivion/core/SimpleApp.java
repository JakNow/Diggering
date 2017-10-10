package pl.oblivion.core;

import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.game.Renderer;

public abstract class SimpleApp {


    public final Window window;
    public final Renderer rendererHandler;
    public final Camera camera;
    public final MouseInput mouseInput;
    private final Timer timer;

    public SimpleApp() {
        this.window = new Window();
        this.mouseInput = new MouseInput(window);
        this.timer = new Timer();
        this.rendererHandler = new Renderer(window);
        this.camera = new Camera(window);
    }

    public abstract void input(MouseInput mouseInput);

    public abstract void logicUpdate(float delta, MouseInput mouseInput);

    private void renderUpdate() {
        rendererHandler.update();
        rendererHandler.prepare();
        rendererHandler.render(window, camera);
    }


    private void cleanUp() {
        rendererHandler.cleanUp();
    }


    public void run() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / Config.TARGET_UPS;

        while (!window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input(mouseInput);

            while (accumulator >= interval) {
                camera.update(0.5f);
                mouseInput.input();
                logicUpdate(interval,mouseInput);
                accumulator -= interval;
            }

            renderUpdate();
            if (!window.isvSync()) {
                sync();
            }
            window.updateAfter();
        }

        window.destroy();
        cleanUp();
    }

    private void sync() {
        float loopSlot = 1f / Config.TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }
}