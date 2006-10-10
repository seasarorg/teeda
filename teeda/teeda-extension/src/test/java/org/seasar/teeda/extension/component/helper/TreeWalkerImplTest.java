/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component.helper;

import junit.framework.TestCase;

import org.seasar.teeda.extension.component.html.THtmlTree;

/**
 * @author shot
 */
public class TreeWalkerImplTest extends TestCase {

    public void testNext_emptyTree() throws Exception {
        THtmlTree tree = new THtmlTree();
        TreeWalkerImpl walker = new TreeWalkerImpl();
        walker.setTree(tree);
        assertTrue(walker.next());
    }

    public void testNext_oneNode() throws Exception {
        THtmlTree tree = new THtmlTree();
        TreeWalkerImpl walker = new TreeWalkerImpl();
        walker.setTree(tree);
        assertTrue(walker.next());
    }
}
