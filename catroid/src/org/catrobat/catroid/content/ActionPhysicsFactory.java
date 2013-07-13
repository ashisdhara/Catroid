/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.content;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.actions.physics.ChangeSizeByNPhysicsAction;
import org.catrobat.catroid.content.actions.physics.ChangeXByNPhysicsAction;
import org.catrobat.catroid.content.actions.physics.IfOnEdgeBouncePhysicsAction;
import org.catrobat.catroid.content.actions.physics.MoveNStepsPhysicsAction;
import org.catrobat.catroid.content.actions.physics.NextLookPhysicsAction;
import org.catrobat.catroid.content.actions.physics.PlaceAtPhysicsAction;
import org.catrobat.catroid.content.actions.physics.PointInDirectionPhysicsAction;
import org.catrobat.catroid.content.actions.physics.PointToPhysicsAction;
import org.catrobat.catroid.content.actions.physics.SetBounceFactorAction;
import org.catrobat.catroid.content.actions.physics.SetFrictionAction;
import org.catrobat.catroid.content.actions.physics.SetGravityAction;
import org.catrobat.catroid.content.actions.physics.SetLookPhysicsAction;
import org.catrobat.catroid.content.actions.physics.SetMassAction;
import org.catrobat.catroid.content.actions.physics.SetPhysicObjectTypeAction;
import org.catrobat.catroid.content.actions.physics.SetSizeToPhysicsAction;
import org.catrobat.catroid.content.actions.physics.SetVelocityAction;
import org.catrobat.catroid.content.actions.physics.SetXPhysicsAction;
import org.catrobat.catroid.content.actions.physics.SetYPhysicsAction;
import org.catrobat.catroid.content.actions.physics.TurnLeftPhysicsAction;
import org.catrobat.catroid.content.actions.physics.TurnLeftSpeedAction;
import org.catrobat.catroid.content.actions.physics.TurnRightPhysicsAction;
import org.catrobat.catroid.content.actions.physics.TurnRightSpeedAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.physics.PhysicObject;
import org.catrobat.catroid.physics.PhysicObject.Type;
import org.catrobat.catroid.physics.PhysicWorld;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ActionPhysicsFactory extends ActionFactory {

	private PhysicObject getPhysicObject(Sprite sprite) {
		return getPhysicWorld().getPhysicObject(sprite);
	}

	private PhysicWorld getPhysicWorld() {
		return ProjectManager.getInstance().getCurrentProject().getPhysicWorld();
	}

	@Override
	public Action createChangeSizeByNAction(Sprite sprite, Formula size) {
		ChangeSizeByNPhysicsAction action = Actions.action(ChangeSizeByNPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setPhysicWorld(getPhysicWorld());
		action.setSize(size);
		return action;
	}

	@Override
	public Action createChangeXByNAction(Sprite sprite, Formula xMovement) {
		ChangeXByNPhysicsAction action = Actions.action(ChangeXByNPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setxMovement(xMovement);
		return action;
	}

	@Override
	public Action createChangeYByNAction(Sprite sprite, Formula yMovement) {
		ChangeXByNPhysicsAction action = Actions.action(ChangeXByNPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setxMovement(yMovement);
		return action;
	}

	@Override
	public Action createGlideToAction(Sprite sprite, Formula x, Formula y, Formula duration) {
		// TODO implement.
		return super.createGlideToAction(sprite, x, y, duration);
	}

	@Override
	public Action createIfOnEdgeBounceAction(Sprite sprite) {
		IfOnEdgeBouncePhysicsAction action = Actions.action(IfOnEdgeBouncePhysicsAction.class);
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createMoveNStepsAction(Sprite sprite, Formula steps) {
		MoveNStepsPhysicsAction action = Actions.action(MoveNStepsPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setSteps(steps);
		return action;
	}

	@Override
	public Action createNextLookAction(Sprite sprite) {
		NextLookPhysicsAction action = Actions.action(NextLookPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setPhysicWorld(getPhysicWorld());
		return action;
	}

	@Override
	public Action createPointInDirectionAction(Sprite sprite, Formula degrees) {
		PointInDirectionPhysicsAction action = Actions.action(PointInDirectionPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setDegreesInUserInterfaceDimensionUnit(degrees);
		return action;
	}

	@Override
	public Action createPointToAction(Sprite sprite, Sprite pointedSprite) {
		PointToPhysicsAction action = Actions.action(PointToPhysicsAction.class);
		action.setSprite(sprite);
		action.setPointedSprite(pointedSprite);
		action.setPhysicObject(getPhysicObject(pointedSprite));
		return action;
	}

	@Override
	public Action createSetLookAction(Sprite sprite, LookData lookData) {
		SetLookPhysicsAction action = Actions.action(SetLookPhysicsAction.class);
		action.setSprite(sprite);
		action.setLookData(lookData);
		action.setPhysicWorld(getPhysicWorld());
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createSetSizeToAction(Sprite sprite, Formula size) {
		SetSizeToPhysicsAction action = Actions.action(SetSizeToPhysicsAction.class);
		action.setSprite(sprite);
		action.setSize(size);
		action.setPhysicWorld(getPhysicWorld());
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createSetXAction(Sprite sprite, Formula x) {
		SetXPhysicsAction action = Actions.action(SetXPhysicsAction.class);
		action.setSprite(sprite);
		action.setX(x);
		//		action.setPhysicObject(getPhysicObject(sprite));
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createSetYAction(Sprite sprite, Formula y) {
		SetYPhysicsAction action = Actions.action(SetYPhysicsAction.class);
		action.setSprite(sprite);
		action.setY(y);
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createTurnLeftAction(Sprite sprite, Formula degrees) {
		TurnLeftPhysicsAction action = Actions.action(TurnLeftPhysicsAction.class);
		action.setSprite(sprite);
		action.setDegrees(degrees);
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createTurnRightAction(Sprite sprite, Formula degrees) {
		TurnRightPhysicsAction action = Actions.action(TurnRightPhysicsAction.class);
		action.setSprite(sprite);
		action.setDegrees(degrees);
		action.setPhysicObject(getPhysicObject(sprite));
		return action;
	}

	@Override
	public Action createSetBounceFactorAction(Sprite sprite, Formula bounceFactor) {
		SetBounceFactorAction action = Actions.action(SetBounceFactorAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setBounceFactor(bounceFactor);
		return action;
	}

	@Override
	public Action createSetFrictionAction(Sprite sprite, Formula friction) {
		SetFrictionAction action = Actions.action(SetFrictionAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setFriction(friction);
		return action;
	}

	@Override
	public Action createSetGravityAction(Sprite sprite, Formula gravityX, Formula gravityY) {
		SetGravityAction action = Actions.action(SetGravityAction.class);
		action.setSprite(sprite);
		action.setPhysicWorld(getPhysicWorld());
		action.setGravity(gravityX, gravityY);
		return action;
	}

	@Override
	public Action createSetMassAction(Sprite sprite, Formula mass) {
		SetMassAction action = Actions.action(SetMassAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setMass(mass);
		return action;
	}

	@Override
	public Action createSetPhysicObjectTypeAction(Sprite sprite, Type type) {
		SetPhysicObjectTypeAction action = Actions.action(SetPhysicObjectTypeAction.class);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setType(type);
		return action;
	}

	@Override
	public Action createSetVelocityAction(Sprite sprite, Formula velocityX, Formula velocityY) {
		SetVelocityAction action = Actions.action(SetVelocityAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setVelocity(velocityX, velocityY);
		return action;
	}

	@Override
	public Action createTurnLeftSpeedAction(Sprite sprite, Formula speed) {
		TurnLeftSpeedAction action = Actions.action(TurnLeftSpeedAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setSpeed(speed);
		return action;
	}

	@Override
	public Action createTurnRightSpeedAction(Sprite sprite, Formula speed) {
		TurnRightSpeedAction action = Actions.action(TurnRightSpeedAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setSpeed(speed);
		return action;
	}

	@Override
	public Action createPlaceAtAction(Sprite sprite, Formula x, Formula y) {
		PlaceAtPhysicsAction action = Actions.action(PlaceAtPhysicsAction.class);
		action.setSprite(sprite);
		action.setPhysicObject(getPhysicObject(sprite));
		action.setX(x);
		action.setY(y);
		return action;
	}
}
