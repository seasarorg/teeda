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
package examples.teeda.web.sample;

public class SeasarUser11056Page {

    public void initialize() {
        // System.out.println("#####initialize#####");
    }

    public void prerender() {
        System.out.println("#####prerender#####");
    }

    public Class doTest() {
        System.out.println("<<<<<Called doTest>>>>>");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class doTest2() {
        System.out.println("[[[[[Called doTest2]]]]]");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String getLayout() {
//        return null;
//    }
}
