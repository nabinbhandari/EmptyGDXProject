package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Actor {
    private MyGdxGame myGdxGame;
    private World world;
    public Body b2Body;
    private Body arm, stone;

    private boolean setToDestroy, setToDestroyJoint, jointNotDestroyed;
    private RevoluteJoint joint;

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
        bDef.position.set(2, 2);
        arm = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.3f, .5f);
        fDef.shape = shape;
        fDef.density = 1;
        b2Body.createFixture(fDef).setUserData(this);

        shape = new PolygonShape();
        shape.setAsBox(.1f, .3f);
        fDef.shape = shape;
        fDef.density = .5f;
        arm.createFixture(fDef).setUserData("arm");

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(b2Body, arm, b2Body.getWorldCenter());

        jointDef.localAnchorA.set(0, 0.25f);
        jointDef.localAnchorB.set(0, 0.2f);

        jointDef.enableLimit = true;
        jointDef.lowerAngle = -3.14f / 2;
        jointDef.upperAngle = 3.14f / 2;

        jointDef.collideConnected = false;

        joint = (RevoluteJoint) world.createJoint(jointDef);
        joint.setMotorSpeed(5);
        setToDestroy = false;
        setToDestroyJoint = false;
        jointNotDestroyed = true;

        BodyDef bDef2 = new BodyDef();
        bDef2.position.set(4, 2);
        bDef2.type = BodyDef.BodyType.DynamicBody;
        stone = world.createBody(bDef2);

        FixtureDef fDef2 = new FixtureDef();
        PolygonShape shape2 = new PolygonShape();
        shape2.setAsBox(.1f, .1f);
        fDef2.shape = shape2;
        fDef2.density = 100;
        stone.createFixture(fDef2);
    }

    public void reset() {
        setToDestroy = true;
    }

    private boolean isAlive() {
        return b2Body.getPosition().y > 0;
    }

    public void update() {
        if (setToDestroyJoint) {
            world.destroyJoint(joint);
            setToDestroyJoint = false;
            jointNotDestroyed = false;
        }
        if (setToDestroy) {
            world.destroyBody(b2Body);
            world.destroyBody(arm);
            world.destroyBody(stone);
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
            b2Body.applyLinearImpulse(new Vector2(1.5f, 0), b2Body.getWorldCenter().add(0, 0.2f), true);
        }
    }

    private void moveBackward() {
        if (b2Body.getLinearVelocity().x > -2) {
            b2Body.applyLinearImpulse(new Vector2(-1.5f, 0), b2Body.getWorldCenter().add(0, 0.2f), true);
        }
    }

    void destroyJoints() {
        if (jointNotDestroyed) {
            setToDestroyJoint = true;
        }
    }
}
