package pl.oblivion.core;

import org.lwjgl.opengl.GL11;
import pl.oblivion.game.RendererHandler;

import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public abstract class SimpleApp {


    private final Window window;
    private final Timer timer;
    private final RendererHandler rendererHandler;


    public SimpleApp() {
        this.window = new Window();
        this.timer = new Timer();
        this.rendererHandler = new RendererHandler();

    }

    public abstract void input();

    public abstract void logicUpdate(float delta);

    private void renderUpdate() {
        glClearColor(Config.RED,Config.GREEN,Config.BLUE,Config.ALPHA);
        rendererHandler.render();

    }


    private void cleanUp() {
        rendererHandler.cleanUp();
    }


    public void run() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / Config.TARGET_UPS;

        while (!window.windowShouldClose()) {
            glClear(GL11.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            GL11.glClearColor(Config.RED, Config.GREEN, Config.BLUE, Config.ALPHA);
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                logicUpdate(interval);
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
