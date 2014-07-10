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
package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ExtendedActions;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.adapter.UserListAdapter;
import org.catrobat.catroid.ui.adapter.UserListAdapterWrapper;
import org.catrobat.catroid.ui.dialogs.NewUserListDialog;
import org.catrobat.catroid.ui.dialogs.NewUserListDialog.NewUserListDialogListener;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

import java.util.List;

public class AddItemToUserListBrick extends BrickBaseType implements OnClickListener, NewUserListDialogListener,
		FormulaBrick {
	private static final long serialVersionUID = 1L;
	private UserList userList;
	private Formula userListFormula;
	private transient AdapterView<?> adapterView;

	public AddItemToUserListBrick(Sprite sprite, Formula userListFormula, UserList userList) {
		this.sprite = sprite;
		this.userListFormula = userListFormula;
		this.userList = userList;
	}

	public AddItemToUserListBrick(Sprite sprite, double value) {
		this.sprite = sprite;
		this.userListFormula = new Formula(value);
		this.userList = null;
	}

	@Override
	public Formula getFormula() {
		return userListFormula;
	}

	@Override
	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	@Override
	public List<SequenceAction> addActionToSequence(SequenceAction sequence) {
		sequence.addAction(ExtendedActions.addItemToUserList(sprite, userListFormula, userList));
		return null;
	}

	@Override
	public View getView(final Context context, int brickId, BaseAdapter baseAdapter) {
		if (animationState) {
			return view;
		}
		if (view == null) {
			alphaValue = 255;
		}

		view = View.inflate(context, R.layout.brick_add_item_to_userlist, null);
		view = getViewWithAlpha(alphaValue);
		setCheckboxView(R.id.brick_add_item_to_userlist_checkbox);

		final Brick brickInstance = this;
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				checked = isChecked;
				adapter.handleCheck(brickInstance, isChecked);
			}
		});

		TextView prototypeText = (TextView) view.findViewById(R.id.brick_add_item_to_userlist_prototype_view);
		TextView textField = (TextView) view.findViewById(R.id.brick_add_item_to_userlist_edit_text);
		prototypeText.setVisibility(View.GONE);
		userListFormula.setTextFieldId(R.id.brick_add_item_to_userlist_edit_text);
		userListFormula.refreshTextField(view);
		textField.setVisibility(View.VISIBLE);
		textField.setOnClickListener(this);

		Spinner userListSpinner = (Spinner) view.findViewById(R.id.add_item_to_userlist_spinner);
		UserListAdapter userListAdapter = ProjectManager.getInstance().getCurrentProject().getUserLists()
				.createUserListAdapter(context, sprite);
		UserListAdapterWrapper userListAdapterWrapper = new UserListAdapterWrapper(context, userListAdapter);
		userListAdapterWrapper.setItemLayout(android.R.layout.simple_spinner_item, android.R.id.text1);

		userListSpinner.setAdapter(userListAdapterWrapper);

		if (!(checkbox.getVisibility() == View.VISIBLE)) {
			userListSpinner.setClickable(true);
			userListSpinner.setEnabled(true);
		} else {
			userListSpinner.setClickable(false);
			userListSpinner.setFocusable(false);
		}

		setSpinnerSelection(userListSpinner, null);

		userListSpinner.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP
						&& (((Spinner) view).getSelectedItemPosition() == 0 && ((Spinner) view).getAdapter().getCount() == 1)) {
					NewUserListDialog dialog = new NewUserListDialog((Spinner) view);
					dialog.addUserListDialogListener(AddItemToUserListBrick.this);
					dialog.show(((SherlockFragmentActivity) view.getContext()).getSupportFragmentManager(),
							NewUserListDialog.DIALOG_FRAGMENT_TAG);
					return true;
				}

				return false;
			}
		});
		userListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (position == 0 && ((UserListAdapterWrapper) parent.getAdapter()).isTouchInDropDownView()) {
					NewUserListDialog dialog = new NewUserListDialog((Spinner) parent);
					dialog.addUserListDialogListener(AddItemToUserListBrick.this);
					dialog.show(((SherlockFragmentActivity) view.getContext()).getSupportFragmentManager(),
							NewUserListDialog.DIALOG_FRAGMENT_TAG);
				}
				((UserListAdapterWrapper) parent.getAdapter()).resetIsTouchInDropDownView();
				userList = (UserList) parent.getItemAtPosition(position);
				adapterView = parent;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				userList = null;
			}
		});

		return view;
	}

	@Override
	public View getPrototypeView(Context context) {
		View prototypeView = View.inflate(context, R.layout.brick_add_item_to_userlist, null);
		Spinner userListSpinner = (Spinner) prototypeView.findViewById(R.id.add_item_to_userlist_spinner);
		userListSpinner.setFocusableInTouchMode(false);
		userListSpinner.setFocusable(false);
		UserListAdapter userListAdapter = ProjectManager.getInstance().getCurrentProject().getUserLists()
				.createUserListAdapter(context, sprite);

		UserListAdapterWrapper userListAdapterWrapper = new UserListAdapterWrapper(context, userListAdapter);

		userListAdapterWrapper.setItemLayout(android.R.layout.simple_spinner_item, android.R.id.text1);
		userListSpinner.setAdapter(userListAdapterWrapper);
		setSpinnerSelection(userListSpinner, null);

		TextView textAddItemToList = (TextView) prototypeView
				.findViewById(R.id.brick_add_item_to_userlist_prototype_view);
		textAddItemToList.setText(String.valueOf(BrickValues.ADD_ITEM_TO_USERLIST));

		return prototypeView;
	}

	@Override
	public View getViewWithAlpha(int alphaValue) {

		if (view != null) {

			TextView textAddItemToList = (TextView) view.findViewById(R.id.brick_add_item_to_userlist_label);
			TextView textTo = (TextView) view.findViewById(R.id.brick_add_item_to_userlist_textview);
			TextView editVariable = (TextView) view.findViewById(R.id.brick_add_item_to_userlist_edit_text);
			Spinner userListSpinner = (Spinner) view.findViewById(R.id.add_item_to_userlist_spinner);

			ColorStateList color = textAddItemToList.getTextColors().withAlpha(alphaValue);
			userListSpinner.getBackground().setAlpha(alphaValue);
			if (adapterView != null) {
				((TextView) adapterView.getChildAt(0)).setTextColor(color);
			}
			textAddItemToList.setTextColor(textAddItemToList.getTextColors().withAlpha(alphaValue));
			textTo.setTextColor(textTo.getTextColors().withAlpha(alphaValue));
			editVariable.setTextColor(editVariable.getTextColors().withAlpha(alphaValue));
			editVariable.getBackground().setAlpha(alphaValue);

			this.alphaValue = (alphaValue);

		}

		return view;
	}

	@Override
	public Brick clone() {
		AddItemToUserListBrick clonedBrick = new AddItemToUserListBrick(getSprite(), userListFormula.clone(), userList);
		return clonedBrick;
	}

	@Override
	public void onClick(View view) {
		if (checkbox.getVisibility() == View.VISIBLE) {
			return;
		}
		FormulaEditorFragment.showFragment(view, this, userListFormula);
	}

	@Override
	public Brick copyBrickForSprite(Sprite sprite, Script script) {
		Project currentProject = ProjectManager.getInstance().getCurrentProject();
		if (!currentProject.getSpriteList().contains(this.sprite)) {
			throw new RuntimeException("this is not the current project");
		}

		AddItemToUserListBrick copyBrick = (AddItemToUserListBrick) clone();
		copyBrick.sprite = sprite;
		copyBrick.userList = currentProject.getUserLists().getUserList(userList.getName(), sprite);
		return copyBrick;
	}

	private void updateUserListIfDeleted(UserListAdapterWrapper userListAdapterWrapper) {
		if (userList != null && (userListAdapterWrapper.getPositionOfItem(userList) == 0)) {
			userList = null;
		}
	}

	private void setSpinnerSelection(Spinner userListSpinner, UserList newUserList) {
		UserListAdapterWrapper userListAdapterWrapper = (UserListAdapterWrapper) userListSpinner.getAdapter();

		updateUserListIfDeleted(userListAdapterWrapper);

		if (userList != null) {
			userListSpinner.setSelection(userListAdapterWrapper.getPositionOfItem(userList), true);
		} else if (newUserList != null) {
			userListSpinner.setSelection(userListAdapterWrapper.getPositionOfItem(newUserList), true);
			userList = newUserList;
		} else {
			userListSpinner.setSelection(userListAdapterWrapper.getCount() - 1, true);
			userList = userListAdapterWrapper.getItem(userListAdapterWrapper.getCount() - 1);
		}
	}

	@Override
	public void onFinishNewUserListDialog(Spinner spinnerToUpdate, UserList newUserList) {
		UserListAdapterWrapper userListAdapterWrapper = ((UserListAdapterWrapper) spinnerToUpdate.getAdapter());
		userListAdapterWrapper.notifyDataSetChanged();
		setSpinnerSelection(spinnerToUpdate, newUserList);
	}

}