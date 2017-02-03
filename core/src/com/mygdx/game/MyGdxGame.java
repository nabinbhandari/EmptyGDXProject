package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.Actor;
import com.mygdx.game.objects.Background;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.Fish;

public class MyGdxGame extends ApplicationAdapter {
    public World world;
    public static float backgroundWidth = 0;

    public int score = 0;
    private BitmapFont font;
    private float screenWidth, screenHeight;
    private SpriteBatch batch, fontBatch;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    private Actor actor;
    private Fish fish;
    private Background background1, background2;
    private Array<Coin> coins;
    private Texture backGroundTexture;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.RED);
        backGroundTexture = new Texture("background.png");
        backgroundWidth = backGroundTexture.getWidth() / Constants.PPM;

        batch = new SpriteBatch();
        fontBatch = new SpriteBatch();
        world = new World(new Vector2(0, -15f), true);
        background1 = new Background(world, backGroundTexture, 0);
        background2 = new Background(world, backGroundTexture, backgroundWidth);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_WIDTH * screenHeight / screenWidth);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_WIDTH / 2 * screenHeight / screenWidth, 0);
        camera.update();

        actor = new Actor(this);
        fish = new Fish(this, 7, 1);
        coins = new Array<Coin>();
        coins.addAll(new Coin(this, 3, 2), new Coin(this, 4, 2));

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenY > screenHeight / 2 && actor.b2Body.getLinearVelocity().y == 0) {
                    actor.jump();
                }
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.UP){
                    actor.jump();
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputAdapter);
    }

    public void resetObjects() {
        score = 0;
        actor.reset();
        fish.reset();
        coins.get(0).reset();
        coins.get(1).reset();
        background1.reset(0);
        background2.reset(backgroundWidth);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 60, 20);
        batch.begin();
        background1.update(batch, camera.position.x);
        background2.update(batch, camera.position.x);
        if (actor.b2Body.getPosition().x - fish.b2Body.getPosition().x > backgroundWidth / 2) {
            fish.resetPosition(actor.b2Body.getPosition().x + (float) (4 + Math.random() * 5));
        }
        actor.update();
        fish.update();
        camera.position.x = actor.b2Body.getWorldCenter().x;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        actor.draw(batch);
        fish.draw(batch);
        for (Coin coin : coins) {
            if (actor.b2Body.getPosition().x - coin.b2Body.getPosition().x > backgroundWidth / 2) {
                coin.resetPosition(actor.b2Body.getPosition().x + 1 + (float) (Math.random() * 5));
            }
            coin.update();
            coin.draw(batch);
        }
        batch.end();
        fontBatch.begin();
        font.draw(fontBatch, "Score: " + score, screenWidth - 150, screenHeight - 20);
        fontBatch.end();
        b2dr.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        actor.dispose();
        fish.getTexture().dispose();
        coins.get(0).getTexture().dispose();
        coins.get(1).getTexture().dispose();
        backGroundTexture.dispose();
        font.dispose();
        batch.dispose();
        world.dispose();
    }
}
