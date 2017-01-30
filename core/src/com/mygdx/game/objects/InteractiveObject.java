package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public abstract class InteractiveObject extends Sprite {
    MyGdxGame myGdxGame;
    World world;
    boolean consumed = false;
    BodyDef bDef = new BodyDef();
    Body b2Body;

    InteractiveObject(Texture texture) {
        super(texture);
    }

    @Override
    public void draw(Batch batch) {
        if (!consumed) {
            super.draw(batch);
        }
    }

    public void reset() {
        consumed = false;
    }

    public abstract void onCollideWithActor();
}
