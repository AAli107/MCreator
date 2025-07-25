/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2020 Pylo and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.mcreator.ui.dialogs.wysiwyg;

import net.mcreator.element.parts.gui.GUIComponent;
import net.mcreator.element.parts.gui.TextField;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.validation.Validator;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.ui.validation.validators.JavaMemberNameValidator;
import net.mcreator.ui.validation.validators.UniqueNameValidator;
import net.mcreator.ui.wysiwyg.WYSIWYGEditor;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;

public class TextFieldDialog extends AbstractWYSIWYGDialog<TextField> {

	public TextFieldDialog(WYSIWYGEditor editor, @Nullable TextField textField) {
		super(editor, textField);
		setModal(true);
		setSize(480, 150);
		setLocationRelativeTo(editor.mcreator);

		VTextField nameField = new VTextField(20);
		nameField.setPreferredSize(new Dimension(200, 28));
		UniqueNameValidator validator = new UniqueNameValidator(L10N.t("dialog.gui.textfield_name_validator"),
				nameField::getText, () -> editor.getComponentList().stream().map(GUIComponent::getName),
				new JavaMemberNameValidator(nameField, false));
		validator.setIsPresentOnList(textField != null);
		nameField.setValidator(validator);
		nameField.enableRealtimeValidation();

		JTextField deft = new JTextField(20);
		deft.setPreferredSize(new Dimension(200, 28));
		JPanel options = new JPanel();

		if (textField == null)
			add("North", PanelUtils.centerInPanel(L10N.label("dialog.gui.textfield_change_width")));
		else
			add("North", PanelUtils.centerInPanel(L10N.label("dialog.gui.textfield_resize")));

		options.setLayout(new BoxLayout(options, BoxLayout.PAGE_AXIS));
		options.add(PanelUtils.join(L10N.label("dialog.gui.textfield_input_name"), nameField));
		add("Center", options);
		setTitle(L10N.t("dialog.gui.textfield_add"));
		options.add(PanelUtils.join(L10N.label("dialog.gui.textfield_initial_text"), deft));

		JButton ok = new JButton(UIManager.getString("OptionPane.okButtonText"));
		JButton cancel = new JButton(UIManager.getString("OptionPane.cancelButtonText"));
		add("South", PanelUtils.join(ok, cancel));

		getRootPane().setDefaultButton(ok);

		if (textField != null) {
			ok.setText(L10N.t("dialog.common.save_changes"));
			nameField.setText(textField.name);
			deft.setText(textField.placeholder);
		}

		cancel.addActionListener(arg01 -> dispose());
		ok.addActionListener(arg01 -> {
			if (nameField.getValidationStatus().getValidationResultType() != Validator.ValidationResultType.ERROR) {
				dispose();
				String text = nameField.getText();
				if (!text.isEmpty()) {
					if (textField == null) {
						TextField component = new TextField(text, 0, 0, 120, 20, deft.getText());

						setEditingComponent(component);
						editor.editor.addComponent(component);
						editor.list.setSelectedValue(component, true);
						editor.editor.moveMode();
					} else {
						int idx = editor.components.indexOf(textField);
						editor.components.remove(textField);
						TextField textfieldNew = new TextField(text, textField.getX(), textField.getY(),
								textField.width, textField.height, deft.getText());
						editor.components.add(idx, textfieldNew);
						setEditingComponent(textfieldNew);
					}
				}
			}
		});

		setVisible(true);
	}

}
