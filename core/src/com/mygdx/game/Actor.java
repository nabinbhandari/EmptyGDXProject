package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Actor extends Sprite {
    World world;
    Body b2Body;

    public Actor(World world) {
        super(new Texture("bunny.png"));
        setScale(1/Constants.PPM);
        this.world = world;
        init();
    }

    public void init() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(2, 2);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(.35f);

        fDef.shape = shape;
        fDef.friction = 0.5f;
        b2Body.createFixture(fDef);
    }

    public void update() {
        setPosition(b2Body.getPosition().x - getWidth()/2, b2Body.getPosition().y - getHeight()/2);
    }

    public void jump() {
        b2Body.applyLinearImpulse(new Vector2(0, 5), b2Body.getWorldCenter(), true);
    }

    public void move(boolean forward) {
        if (forward) {
            b2Body.applyLinearImpulse(new Vector2(2, 0), b2Body.getWorldCenter(), true);
        } else {
            b2Body.applyLinearImpulse(new Vector2(-2, 0), b2Body.getWorldCenter(), true);
        }
    }
}
