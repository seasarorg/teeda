/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.util;

import java.io.File;
import java.util.LinkedList;

/**
 * @author manhole
 */
public class FileSystemTraversal {

    public static interface Handler {

        public void acceptFile(File file);

        public void acceptDirectory(File file);

    }

    public static void traverse(File rootDirectory,
            FileSystemTraversal.Handler handler) {
        LinkedList dirs = new LinkedList();
        addAll(dirs, rootDirectory.listFiles());
        while (!dirs.isEmpty()) {
            File file = (File) dirs.removeFirst();
            if (file.isFile()) {
                handler.acceptFile(file);
            } else if (file.isDirectory()) {
                handler.acceptDirectory(file);
                addAll(dirs, file.listFiles());
            }
        }
    }

    private static void addAll(LinkedList dirs, File[] files) {
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            dirs.add(file);
        }
    }

}
