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

package net.mcreator.ui.component.tree;

import javax.swing.tree.DefaultTreeModel;

public class FilteredTreeModel extends DefaultTreeModel {

	private String filter = "";

	/**
	 * @param node the node to apply filtering to
	 */
	public FilteredTreeModel(FilterTreeNode node) {
		this(node, null);
	}

	/**
	 * @param node   the node to apply filtering to
	 * @param filter the actual filter we will apply
	 */
	private FilteredTreeModel(FilterTreeNode node, String filter) {
		super(node);
		if (node != null)
			node.setFilter(filter);
	}

	public boolean setFilter(String filter) {
		String current = ((FilterTreeNode) root).getFilter();
		boolean changed = !filter.equals(current);

		this.filter = filter;
		if (root != null && changed) {
			((FilterTreeNode) root).setFilter(filter);
			Object[] path = { root };
			fireTreeStructureChanged(this, path, null, null);
		}

		return changed;
	}

	public void refilter() {
		setFilter(filter);
	}

	@Override public int getChildCount(Object parent) {
		if (parent instanceof FilterTreeNode) {
			return (((FilterTreeNode) parent).getChildCount());
		}
		return 0;
	}

	@Override public Object getChild(Object parent, int index) {
		if (parent instanceof FilterTreeNode) {
			return (((FilterTreeNode) parent).getChildAt(index));
		}
		return null;
	}

}