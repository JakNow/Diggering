package pl.oblivion.core;

import pl.oblivion.game.Camera;
import pl.oblivion.game.MouseInput;
import pl.oblivion.game.Renderer;

public abstract class SimpleApp {

    protected final Window window;
    protected final Renderer rendererHandler;
    protected final MouseInput mouseInput;
    protected final PhysicsState physicsState;
    private final Timer timer;
    protected Camera camera;

    protected SimpleApp() {
        this.window = new Window();
        this.mouseInput = new MouseInput(window);
        this.timer = new Timer();
        this.rendererHandler = new Renderer(window);
        physicsState = new PhysicsState();

    }

    public abstract void logicUpdate(float delta, MouseInput mouseInput);

    private void renderUpdate() {
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

            while (accumulator >= interval) {
                mouseInput.input();
                physicsState.update();
                logicUpdate(interval, mouseInput);
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
