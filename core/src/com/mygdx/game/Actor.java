package com.mygdx.game;

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
        this.world = world;
        init();
    }

    public void init() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(100, 300);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(40);

        fDef.shape = shape;
        b2Body.createFixture(fDef);
    }

    public void jump() {
        b2Body.applyLinearImpulse(new Vector2(0, 200), b2Body.getWorldCenter(), true);
    }

    public void move(boolean forward) {
        if (forward) {
            b2Body.applyLinearImpulse(new Vector2(50, 0), b2Body.getWorldCenter(), true);
        } else {
            b2Body.applyLinearImpulse(new Vector2(-50, 0), b2Body.getWorldCenter(), true);
        }
    }
}
