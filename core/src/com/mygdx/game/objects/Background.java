package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 2/1/2017.
 *
 * @author Nabin.
 */

public class Background {

    private World world;
    private Body b2Body;

    private float currentX = 0;
    private boolean setToDestroy;

    public Background(World world, float startX) {
        this.world = world;
        this.currentX = startX;
        createGround();
    }

    private void createGround() {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;

        bDef.position.set(currentX + MyGdxGame.backgroundWidth / 2, .25f);
        b2Body = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(MyGdxGame.backgroundWidth / 2, .25f);
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.friction = 0.2f;
        b2Body.createFixture(fDef);
        setToDestroy = false;
    }

    public void update(float cameraX) {
        if (cameraX - currentX > MyGdxGame.backgroundWidth * 1.5 || setToDestroy) {
            world.destroyBody(b2Body);
            currentX += 2 * MyGdxGame.backgroundWidth;
            createGround();
        }
    }

    public void reset(float startX) {
        setToDestroy = true;
        currentX = startX - 2 * MyGdxGame.backgroundWidth;
    }
}
