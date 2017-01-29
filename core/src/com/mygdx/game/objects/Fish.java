package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public class Fish extends Sprite {
    private World world;
    private Body b2Body;

    public Fish(World world) {
        super(new Texture("fish.png"));
        setScale(1 / Constants.PPM/4);
        this.world = world;
        init();
    }

    private void init() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(5, 2);
        bDef.type = BodyDef.BodyType.StaticBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.25f,.125f);

        fDef.shape = shape;
        b2Body.createFixture(fDef);
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }
}
