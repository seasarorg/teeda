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
public class Tree2Page {

	private TreeNode tree;

	public String initialize() {
		tree = new TreeNodeImpl("folder-B", "ROOT", false);
		TreeNodeImpl aaaNode1 = new TreeNodeImpl("folder-A", "AAA", false);
		TreeNodeImpl document1 = new TreeNodeImpl("document", "CCC", true);
		document1.setValue("true");
		aaaNode1.getChildren().add(document1);
		tree.getChildren().add(aaaNode1);

		TreeNodeImpl aaaNode2 = new TreeNodeImpl("folder-A", "BBB", false);
		TreeNodeImpl document2 = new TreeNodeImpl("document", "DDD", true);
		aaaNode2.getChildren().add(document2);
		document2.setValue("true");
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
		for (int i = 0; i < node.getChildCount(); i++) {
			TreeNode n = node.getChild(i);
			System.out.println("child : " + n.getDescription() + ", "
					+ n.getValue());
			if (n.getChildCount() >= 0) {
				outTree(n);
			}
		}
	}

	public String doHoge() {
		return null;
	}

	public TreeNode getTree() {
		return tree;
	}

	public void setTree(TreeNode tree) {
		this.tree = tree;
	}

}
