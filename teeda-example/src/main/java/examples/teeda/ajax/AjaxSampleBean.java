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
package examples.teeda.ajax;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yone
 */
public class AjaxSampleBean {

    private static final String FRUIT = "100\tオレンジ\n200\tリンゴ\n"
            + "300\t桃\n400\t葡萄\n500\tメロン\n600\tすいか\n";

    private static final String DRINK = "0\t水\n120\tコーラ\n"
            + "240\tコーヒー\n200\t紅茶\n120\tスプライト\n80\tペプシ\n";

    private static final String NOODLE = "250\tうどん\n300\tそば\n"
            + "550\tラーメン\n180\tソーメン\n880\tスパゲティ\n30\t焼きそば\n"
            + "440\tナポリタン\n1280\t長崎チャンポン\n";

    private static final String JSON_FRUIT = "{ \"detail\" : [\n "
            + "{ \"menuName\" : \"オレンジ\", \"price\" : \"100\" },\n"
            + "{ \"menuName\" : \"リンゴ\", \"price\" : \"200\" },\n"
            + "{ \"menuName\" : \"桃\", \"price\" : \"300\" },\n"
            + "{ \"menuName\" : \"葡萄\", \"price\" : \"400\" },\n"
            + "{ \"menuName\" : \"メロン\", \"price\" : \"500\" },\n"
            + "{ \"menuName\" : \"すいか\", \"price\" : \"600\" } \n" + "]\n"
            + "}";

    private static final String JSON_DRINK = "{ \"detail\" : [\n "
            + "{ \"menuName\" : \"水\", \"price\" : \"0\" },\n"
            + "{ \"menuName\" : \"コーラ\", \"price\" : \"120\" },\n"
            + "{ \"menuName\" : \"コーヒー\", \"price\" : \"240\" },\n"
            + "{ \"menuName\" : \"紅茶\", \"price\" : \"200\" },\n"
            + "{ \"menuName\" : \"スプライト\", \"price\" : \"120\" },\n"
            + "{ \"menuName\" : \"ペプシ\", \"price\" : \"120\" }\n" + "]\n" + "}";

    private static final String JSON_NOODLE = "{ \"detail\" : [\n "
            + "{ \"menuName\" : \"うどん\", \"price\" : \"250\" },\n"
            + "{ \"menuName\" : \"そば\", \"price\" : \"300\" },\n"
            + "{ \"menuName\" : \"ラーメン\", \"price\" : \"550\" },\n"
            + "{ \"menuName\" : \"ソーメン\", \"price\" : \"180\" },\n"
            + "{ \"menuName\" : \"スパゲティ\", \"price\" : \"880\" },\n"
            + "{ \"menuName\" : \"焼きそば\", \"price\" : \"30\" },\n"
            + "{ \"menuName\" : \"ナポリタン\", \"price\" : \"440\" },\n"
            + "{ \"menuName\" : \"長崎チャンポン\", \"price\" : \"1280\" } \n" + "]\n"
            + "}";

    private static final String XML_FRUIT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<detail>\n"
            + " <item><menuName>オレンジ</menuName><price>100</price></item>\n"
            + " <item><menuName>リンゴ</menuName><price>200</price></item>\n"
            + " <item><menuName>桃</menuName><price>300</price></item>\n"
            + " <item><menuName>葡萄</menuName><price>400</price></item>\n"
            + " <item><menuName>メロン</menuName><price>500</price></item>\n"
            + " <item><menuName>すいか</menuName><price>600</price></item>\n"
            + "</detail>\n";

    private static final String XML_DRINK = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<detail>\n"
            + " <item><menuName>水</menuName><price>0</price></item>\n"
            + " <item><menuName>コーラ</menuName><price>120</price></item>\n"
            + " <item><menuName>コーヒー</menuName><price>240</price></item>\n"
            + " <item><menuName>紅茶</menuName><price>200</price></item>\n"
            + " <item><menuName>スプライト</menuName><price>120</price></item>\n"
            + " <item><menuName>ペプシ</menuName><price>120</price></item>\n"
            + "</detail>\n";

    private static final String XML_NOODLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<detail>\n"
            + " <item><menuName>うどん</menuName><price>250</price></item>\n"
            + " <item><menuName>そば</menuName><price>300</price></item>\n"
            + " <item><menuName>ラーメン</menuName><price>550</price></item>\n"
            + " <item><menuName>ソーメン</menuName><price>180</price></item>\n"
            + " <item><menuName>スパゲティ</menuName><price>880</price></item>\n"
            + " <item><menuName>焼きそば</menuName><price>30</price></item>\n"
            + " <item><menuName>ナポリタン</menuName><price>440</price></item>\n"
            + " <item><menuName>長崎チャンポン</menuName><price>1280</price></item>\n"
            + "</detail>\n";

    private final String[] ORG_TEXT = { "", FRUIT, DRINK, NOODLE };

    private final String[] JSON_TEXT = { "", JSON_FRUIT, JSON_DRINK,
            JSON_NOODLE };

    private final String[] XML_TEXT = { "", XML_FRUIT, XML_DRINK, XML_NOODLE };

    private String arg1;

    private String arg2;

    private int food;

    private HttpServletResponse response;

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        System.out.println("setArg1[" + arg1 + "]");
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        System.out.println("setArg2[" + arg2 + "]");
        this.arg2 = arg2;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public String ajaxOrg() {
        return this.createResponseOrg();
    }

    public String ajaxJson() {
        return this.createResponseJson();
    }

    public String ajaxXml() {
        return this.createResponseXml();
    }

    public String ajaxAction() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_TEXT);
        return this.arg2;
    }

    private String createResponseOrg() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_TEXT);
        return ORG_TEXT[this.food];
    }

    private String createResponseJson() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_JSON);
        return JSON_TEXT[this.food];
    }

    private String createResponseXml() {
        this.response.setContentType(AjaxConstants.CONTENT_TYPE_XML);
        return XML_TEXT[this.food];
    }
}