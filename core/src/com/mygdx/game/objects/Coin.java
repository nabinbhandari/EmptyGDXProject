package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.Constants;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/30/2017.
 *
 * @author Nabin.
 */

public class Coin extends InteractiveObject {

    public Coin(MyGdxGame myGdxGame, int initX, int initY) {
        super(new Texture("coin.png"));
        setScale(1 / Constants.PPM);
        this.myGdxGame = myGdxGame;
        this.world = myGdxGame.world;
        init(initX, initY);
    }

    private void init(float initX, float initY) {
        bDef.position.set(initX, initY);
        bDef.type = BodyDef.BodyType.StaticBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);

        fDef.shape = shape;
        fDef.isSensor = true;
        Fixture fixture = b2Body.createFixture(fDef);
        fixture.setUserData(this);

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
        setToDestroy = false;
        consumed = false;
    }

    public void resetPosition(float x) {
        world.destroyBody(b2Body);
        init(x, (float) (1 + Math.random()));
    }

    public void update() {
        if (setToDestroy) {
            resetPosition(3 + (float) (Math.random() * 5));
        } else if (consumed) {
            resetPosition(b2Body.getPosition().x + 1 + (float) (Math.random() * 5));
        }
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void onCollideWithActor() {
        consumed = true;
    }
}
