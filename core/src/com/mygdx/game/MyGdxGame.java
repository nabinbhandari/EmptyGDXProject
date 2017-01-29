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
    SpriteBatch batch;
    Sprite background;
    World world;
    Box2DDebugRenderer b2dr;
    Body body;
    OrthographicCamera camera;

    float screenWidth, screenHeight;

    Actor actor;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        batch = new SpriteBatch();
        background = new Sprite(new Texture("background.png"));
        world = new World(new Vector2(0, -200), true);
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera(500f, 500f * screenHeight / screenWidth);
        camera.position.set(250, 250 * screenHeight / screenWidth, 0);
        camera.update();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        Rectangle rect = new Rectangle(0, 0, background.getWidth(), 50);

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

        body = world.createBody(bdef);

        shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
        fdef.shape = shape;
        body.createFixture(fdef);

        actor = new Actor(world);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        camera.position.x = actor.b2Body.getWorldCenter().x;
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        world.step(1 / 60f, 6, 2);
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
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
