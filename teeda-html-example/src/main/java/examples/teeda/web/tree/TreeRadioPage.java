/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package examples.teeda.web.tree;

import org.seasar.teeda.extension.component.TreeNode;
import org.seasar.teeda.extension.component.TreeNodeImpl;

/**
 * @author shot
 */
public class TreeRadioPage {

	private String aaa;

	private TreeNode tree;

	public String initialize() {
		tree = new TreeNodeImpl("folder-B", "ROOT", "0", false);
		TreeNodeImpl aaaNode1 = new TreeNodeImpl("folder-A", "A", "10", false);
		TreeNodeImpl document11 = new TreeNodeImpl("document", "A1", "11", true);
		TreeNodeImpl document12 = new TreeNodeImpl("document", "A2", "12", true);
		aaaNode1.getChildren().add(document11);
		aaaNode1.getChildren().add(document12);
		tree.getChildren().add(aaaNode1);

		TreeNodeImpl aaaNode2 = new TreeNodeImpl("folder-A", "B", "20", false);
		TreeNodeImpl document21 = new TreeNodeImpl("document", "B1", "21", true);
		TreeNodeImpl document22 = new TreeNodeImpl("document", "B2", "22", true);
		aaaNode2.getChildren().add(document21);
		aaaNode2.getChildren().add(document22);
		tree.getChildren().add(aaaNode2);
		return null;
	}

	public String prerender() {
		outTree(tree);
		return null;
	}

	protected void outTree(TreeNode node) {
		if (node == null) {
			return;
		}
		System.out.println("child : " + node.getDescription() + ", "
				+ (node.getValue().equals(aaa)));
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode n = node.getChild(i);
			outTree(n);
		}
	}

	public String doHoge() {
		return null;
	}

	public String getAaa() {
		return aaa;
	}

	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

	public TreeNode getTree() {
		return tree;
	}

	public void setTree(TreeNode tree) {
		this.tree = tree;
	}

}
