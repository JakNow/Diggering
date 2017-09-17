package main.java.pl.oblivion.game;

import main.java.pl.oblivion.core.GameEngine;
import main.java.pl.oblivion.core.IGameLogic;

import static main.java.pl.oblivion.game.Config.*;
public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new Game();
            GameEngine gameEng = new GameEngine(TITLE, WIDTH, HEIGHT, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}
