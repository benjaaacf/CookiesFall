package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

	public class CookiesFallMenu extends Game {

		private SpriteBatch batch;
		private BitmapFont font;
		private int higherScore;
                private static CookiesFallMenu instance;
                
                public CookiesFallMenu() {}
                
                public static CookiesFallMenu getInstance() {
                    if (instance == null) {
                        instance = new CookiesFallMenu();
                    }
                    return instance;
                }

                @Override
		public void create() {
			batch = new SpriteBatch();
			font = new BitmapFont(); // use libGDX's default Arial font
			this.setScreen(new MainMenuScreen(this));
		}
                
                @Override
		public void render() {
			super.render(); // important!
		}
                
                @Override
		public void dispose() {
			batch.dispose();
			font.dispose();
		}

		public SpriteBatch getBatch() {
			return batch;
		}

		public BitmapFont getFont() {
			return font;
		}

		public int getHigherScore() {
			return higherScore;
		}

		public void setHigherScore(int higherScore) {
			this.higherScore = higherScore;
		}
		

	}
