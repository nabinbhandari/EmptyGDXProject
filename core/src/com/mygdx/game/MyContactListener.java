package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.objects.Actor;
import com.mygdx.game.objects.InteractiveObject;

/**
 * Created on 1/29/2017.
 *
 * @author Nabin.
 */

class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() instanceof Actor || fixtureB.getUserData() instanceof Actor) {
            Fixture object = fixtureA.getUserData() instanceof Actor ? fixtureB : fixtureA;
            if (object.getUserData() != null && object.getUserData() instanceof InteractiveObject) {
                ((InteractiveObject) object.getUserData()).onCollideWithActor();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
