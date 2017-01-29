package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Fish extends InteractiveObject {
    private World world;
    public boolean consumed = false;

    public Fish(World world) {
        super(new Texture("fish.png"));
        setScale(1 / Constants.PPM / 4);
        this.world = world;
        init();
    }

    private void init() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(5, 2);
        bDef.type = BodyDef.BodyType.StaticBody;
        Body b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.25f, .125f);

        fDef.shape = shape;
        fDef.isSensor = true;
        Fixture fixture = b2Body.createFixture(fDef);
        fixture.setUserData(this);

        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void onCollide() {
        consumed = true;
    }

    @Override
    public void draw(Batch batch) {
        if (!consumed) {
            super.draw(batch);
        }
    }
}
