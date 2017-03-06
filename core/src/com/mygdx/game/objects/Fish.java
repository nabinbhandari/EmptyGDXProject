package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Fish extends InteractiveObject {

    private boolean setToDestroy;

    public Fish(MyGdxGame myGdxGame, int initX, int initY) {
        super(new Texture("fish.png"));
        setScale(1 / Constants.PPM / 4);
        this.myGdxGame = myGdxGame;
        this.world = myGdxGame.world;
        init(initX, initY);
    }

    private void init(int initX, int initY) {
        bDef.position.set(initX, initY);
        bDef.type = BodyDef.BodyType.KinematicBody;
        b2Body = world.createBody(bDef);
        b2Body.setLinearVelocity(-2f, 0);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.25f, .125f);

        fDef.shape = shape;
        fDef.isSensor = true;
        Fixture fixture = b2Body.createFixture(fDef);
        fixture.setUserData(this);

        setFlip(true, false);
        setToDestroy = false;
    }

    @Override
    public void reset() {
        super.reset();
        setToDestroy = true;
    }

    public void update() {
        if (setToDestroy) {
            world.destroyBody(b2Body);
            init(7, 1);
        }
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void onCollideWithActor() {
        consumed = true;
        myGdxGame.actor.destroyJoints();
    }
}
