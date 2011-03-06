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
public class TreePage {

	private TreeNode tree;

	public String prerender() {
		tree = new TreeNodeImpl("folder-B", "ROOT", false);
		TreeNodeImpl personNode = new TreeNodeImpl("folder-A", "AAA", false);
		TreeNodeImpl document1 = new TreeNodeImpl("document", "BBB", true);
		document1.setValue("../hello/hello.html");
		TreeNodeImpl document2 = new TreeNodeImpl("document", "CCC", true);
		document2.setValue("../start/index.html");
		personNode.getChildren().add(document1);
		personNode.getChildren().add(document2);
		tree.getChildren().add(personNode);
		return null;
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
