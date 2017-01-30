package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.objects.Actor;
import com.mygdx.game.objects.Coin;
import com.mygdx.game.objects.Fish;

public class MyGdxGame extends ApplicationAdapter {
    private float screenHeight;
    private SpriteBatch batch;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    private Actor actor;

    private Fish fish;
    private Sprite background;
    private Array<Coin> coins;
    public World world;

    @Override
    public void create() {
        float screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        background = new Sprite(new Texture("background.png"));
        background.setOrigin(0, 0);
        background.setScale(1 / Constants.PPM);
        world = new World(new Vector2(0, -10f), true);
        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT * screenHeight / screenWidth);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2 * screenHeight / screenWidth, 0);
        camera.update();

        createGround();
        actor = new Actor(this);
        fish = new Fish(this, 7, 1);
        coins = new Array<Coin>();
        coins.addAll(new Coin(this, 3, 2), new Coin(this, 4, 2));

        InputAdapter inputAdapter = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenY < screenHeight / 2 && actor.b2Body.getLinearVelocity().y == 0) {
                    actor.jump();
                }
                return true;
            }
        };
        Gdx.input.setInputProcessor(inputAdapter);
    }

    public void resetObjects() {
        actor.reset();
        fish.reset();
        coins.get(0).reset();
        coins.get(1).reset();
    }

    private void createGround() {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;

        Rectangle rect = new Rectangle(0, 0, background.getWidth() * background.getScaleX(), .5f);
        bDef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        Body ground = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.friction = 0.5f;
        ground.createFixture(fDef);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.step(1 / 60f, 60, 20);

        actor.update();
        fish.update();
        camera.position.x = actor.b2Body.getWorldCenter().x;
        camera.update();

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        background.draw(batch);
        actor.draw(batch);
        fish.draw(batch);
        for (Coin coin : coins) {
            coin.draw(batch);
        }
        batch.end();
        b2dr.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();
        actor.dispose();
        batch.dispose();
        world.dispose();
    }
}
