package com.figer.game;

import android.content.Context;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.figer.game.Stage.Stage;
import com.figer.game.Stage.StageManager;
import com.figer.game.System.Input;
import com.figer.game.System.Renderer;

public class GameMain extends ApplicationAdapter{
    public GameMain(Context context) {
        this.context = context;
    }

	private Context context;

	/**				GUI Magic					*/
	// System
	private Renderer renderer;
	private Input input;

	/**             Stage Magic                 */
	private Array<Stage> stages;
	private StageManager stageManager;

	@Override
	public void create () {
		//GUI Elements
		renderer = new Renderer();
		input = new Input(renderer.getViewport());

		//Stage Magic
        stages = new Array<Stage>();
        stageManager = new StageManager();
        stages.add(new InitialStage(stageManager, context), new GameStage(stageManager),new DeckBuilderStage(stageManager));
	}

	@Override
	public void dispose () {
        //Destroy GUI elements
		renderer.dispose();
	}

	@Override
	public void resize(int w, int h) {
		renderer.resize(w, h);
	}

	@Override
	public void render () {
		stageManager.update(stages);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Drawing
		renderer.prepare();
		renderer.begin();
		stages.get(stageManager.getNumber()).draw(renderer);
		renderer.end();

		// Updating
		stages.get(stageManager.getNumber()).update(input);
		input.update();
	}
}