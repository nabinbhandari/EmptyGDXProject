package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    private float screenWidth, screenHeight;
    private SpriteBatch batch;
    private Sprite background;
    private World world;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    private Actor actor;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        background = new Sprite(new Texture("background.png"));
        background.setOrigin(0, 0);
        background.setScale(1/Constants.PPM);
        world = new World(new Vector2(0, -10f), true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT * screenHeight / screenWidth);
        camera.position.set(Constants.VIEWPORT_WIDTH / 2, Constants.VIEWPORT_HEIGHT / 2 * screenHeight / screenWidth, 0);
        camera.update();

        createGround();

        actor = new Actor(world);
        Gdx.input.setInputProcessor(this);
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
        actor.update();
        camera.position.x = actor.b2Body.getWorldCenter().x;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.step(1 / 60f, 60, 20);
        Gdx.gl.glClearColor(0.4f, 0.6f, 0.9f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        actor.draw(batch);
        batch.end();
        b2dr.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.getTexture().dispose();
    }


    //InputProcessor methods.
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenY < screenHeight / 2) {
            actor.jump();
        }
        if (screenX < screenWidth / 3) {
            actor.move(false);
        } else if (screenX > screenWidth * 2 / 3) {
            actor.move(true);
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
