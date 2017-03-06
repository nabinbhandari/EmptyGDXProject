package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.objects.Actor;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.Fish;

public class MyGdxGame extends ApplicationAdapter {
    public World world;
    public static float backgroundWidth = 0;

    private float screenHeight;
    private SpriteBatch batch;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    private Actor actor;
    private Background background1, background2;
    private Texture backGroundTexture;

    private Fish fish;

    @Override
    public void create() {
        float screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        backGroundTexture = new Texture("background.png");
        backgroundWidth = backGroundTexture.getWidth() / Constants.PPM;

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -15f), true);
        background1 = new Background(world, 0);
        background2 = new Background(world, backgroundWidth);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH * screenHeight / screenWidth);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_WIDTH / 2 * screenHeight / screenWidth, 0);
        camera.update();

        fish = new Fish(this, 7, 1);

        actor = new Actor(this);

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenY > screenHeight / 2 && actor.b2Body.getLinearVelocity().y == 0) {
                    actor.jump();
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputAdapter);
    }

    public void resetObjects() {
        actor.reset();
        background1.reset(0);
        background2.reset(backgroundWidth);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 60, 20);
        batch.begin();
        background1.update(camera.position.x);
        background2.update(camera.position.x);
        actor.update();
        fish.update();
        fish.draw(batch);
        camera.position.x = actor.b2Body.getWorldCenter().x;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.end();
        b2dr.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        backGroundTexture.dispose();
        batch.dispose();
        world.dispose();
    }
}
