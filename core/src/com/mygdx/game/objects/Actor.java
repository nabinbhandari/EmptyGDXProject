package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Actor {
    private MyGdxGame myGdxGame;
    private World world;
    public Body b2Body, hangingBody;

    private boolean setToDestroy;

    public Actor(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.world = myGdxGame.world;
        init();
    }

    private void init() {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(2, 2);
        b2Body = world.createBody(bDef);
        bDef.position.set(2, 1);
        hangingBody = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.3f, .5f);
        fDef.shape = shape;
        fDef.density = 10;
        fDef.filter.categoryBits = 2;
        b2Body.createFixture(fDef).setUserData("actor");

        shape = new PolygonShape();
        shape.setAsBox(.1f, .3f);
        fDef.shape = shape;
        fDef.density = 5;
        fDef.filter.categoryBits = 4;
        fDef.isSensor = true;
        hangingBody.createFixture(fDef).setUserData("hanging");

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(b2Body, hangingBody, b2Body.getWorldCenter());

        jointDef.localAnchorA.set(0, 0.2f);
        jointDef.localAnchorB.set(0, 0.2f);

        jointDef.maxMotorTorque = 10f;
        jointDef.collideConnected = false;

        RevoluteJoint joint = (RevoluteJoint) world.createJoint(jointDef);
        joint.setMotorSpeed(5);
        joint.enableMotor(true);
        setToDestroy = false;
    }

    public void reset() {
        setToDestroy = true;
    }

    private boolean isAlive() {
        return b2Body.getPosition().y > 0;
    }

    public void update() {
        if (setToDestroy) {
            world.destroyBody(b2Body);
            init();
        }
        if (!isAlive()) {
            myGdxGame.resetObjects();
        }
        if (Gdx.input.isTouched()) {
            float screenX = Gdx.input.getX();
            if (screenX < Gdx.graphics.getWidth() / 3) {
                moveBackward();
            } else if (screenX > Gdx.graphics.getWidth() * 2 / 3) {
                moveForward();
            }
        }
    }

    public void jump() {
        b2Body.applyLinearImpulse(new Vector2(0, 5.5f), b2Body.getWorldCenter(), true);
    }

    private void moveForward() {
        if (b2Body.getLinearVelocity().x < 2) {
            b2Body.applyLinearImpulse(new Vector2(1.5f, 0), b2Body.getWorldCenter(), true);
        }
    }

    private void moveBackward() {
        if (b2Body.getLinearVelocity().x > -2) {
            b2Body.applyLinearImpulse(new Vector2(-1.5f, 0), b2Body.getWorldCenter(), true);
        }
    }
}
