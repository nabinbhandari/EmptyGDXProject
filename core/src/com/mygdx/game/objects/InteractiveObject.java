package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

public abstract class InteractiveObject extends Sprite {

    InteractiveObject(Texture texture) {
        super(texture);
    }

    public abstract void onCollide();
}
