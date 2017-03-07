package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Actor {
    private boolean setToDestroy, setToDestroyJoint, jointNotDestroyed;
    private MyGdxGame myGdxGame;
    private WheelJoint wheel1Joint, wheel2Joint;
    private WeldJoint bar1Joint, bar2Joint;
    private World world;
    private Body wheel1, wheel2;
    private Body stone;

    private Body bar1, bar2;
    public Body bar;

    public Actor(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        this.world = myGdxGame.world;
        init();
    }

    private void init() {

        BodyDef wheelDef = new BodyDef();
        wheelDef.position.set(2, 2);
        wheelDef.type = BodyDef.BodyType.DynamicBody;
        wheel1 = world.createBody(wheelDef);
        wheelDef.position.set(4, 2);
        wheel2 = world.createBody(wheelDef);

        CircleShape wheelShape = new CircleShape();
        wheelShape.setRadius(0.5f);
        FixtureDef wheelFixture = new FixtureDef();
        wheelFixture.shape = wheelShape;
        wheelFixture.density = 1;

        wheel1.createFixture(wheelFixture);
        wheel2.createFixture(wheelFixture).setUserData(this);

        BodyDef barDef = new BodyDef();
        barDef.position.set(3, 3);
        barDef.type = BodyDef.BodyType.DynamicBody;
        bar = world.createBody(barDef);

        PolygonShape barShape = new PolygonShape();
        barShape.setAsBox(1f, 0.05f);
        FixtureDef barFixture = new FixtureDef();
        barFixture.shape = barShape;
        barFixture.density = 2;
        bar.createFixture(barFixture);

        barDef.position.set(2, 2.5f);
        bar1 = world.createBody(barDef);
        barDef.position.set(4, 2.5f);
        bar2 = world.createBody(barDef);

        barShape.setAsBox(0.05f, 0.5f);
        bar1.createFixture(barFixture);
        bar2.createFixture(barFixture);

        WheelJointDef wheelJointDef = new WheelJointDef();
        wheelJointDef.initialize(bar1, wheel1, new Vector2(2, 2), new Vector2(0, 1));
        wheelJointDef.collideConnected = false;
        wheelJointDef.enableMotor = true;
        wheelJointDef.motorSpeed = -3.14f;
        wheelJointDef.maxMotorTorque = 100;
        wheelJointDef.dampingRatio = 1f;
        wheelJointDef.frequencyHz = 4;
        wheel1Joint = (WheelJoint) world.createJoint(wheelJointDef);

        wheelJointDef.enableMotor = false;
        wheelJointDef.initialize(bar2, wheel2, new Vector2(4, 2), new Vector2(0, 1));
        wheel2Joint = (WheelJoint) world.createJoint(wheelJointDef);

        WeldJointDef barJointDef = new WeldJointDef();
        barJointDef.initialize(bar1, bar, new Vector2(2, 3));
        bar1Joint = (WeldJoint) world.createJoint(barJointDef);
        barJointDef.initialize(bar2, bar, new Vector2(4, 3));
        bar2Joint = (WeldJoint) world.createJoint(barJointDef);

        setToDestroy = false;
        setToDestroyJoint = false;
        jointNotDestroyed = true;

        BodyDef bDef2 = new BodyDef();
        bDef2.position.set(7, 2);
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
        return bar.getPosition().y > 0;
    }

    public void update() {
        if (setToDestroyJoint) {
            world.destroyJoint(wheel1Joint);
            world.destroyJoint(wheel2Joint);
            world.destroyJoint(bar1Joint);
            world.destroyJoint(bar2Joint);
            setToDestroyJoint = false;
            jointNotDestroyed = false;
        }
        if (setToDestroy) {
            world.destroyBody(wheel1);
            world.destroyBody(wheel2);
            world.destroyBody(bar);
            world.destroyBody(bar1);
            world.destroyBody(bar2);
            world.destroyBody(stone);
            init();
        }
        if (!isAlive()) {
            System.err.println(bar.getPosition().y);
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
        wheel2.applyLinearImpulse(new Vector2(0, 5.5f), wheel2.getWorldCenter(), true);
    }

    private void moveForward() {
        if (wheel1.getLinearVelocity().x < 2) {
            wheel1.applyLinearImpulse(new Vector2(1.5f, 0), wheel1.getWorldCenter(), true);
        }
    }

    private void moveBackward() {
        if (wheel1.getLinearVelocity().x > -2) {
            wheel1.applyLinearImpulse(new Vector2(-1.5f, 0), wheel1.getWorldCenter(), true);
        }
    }

    void destroyJoints() {
        if (jointNotDestroyed) {
            setToDestroyJoint = true;
        }
    }
}
