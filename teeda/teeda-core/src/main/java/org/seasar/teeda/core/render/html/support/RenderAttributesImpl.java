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
package org.seasar.teeda.core.render.html.support;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.render.html.RenderAttributes;

public class RenderAttributesImpl implements RenderAttributes {

    private static final String RENDER_ON = "1";

    private Map map = new HashMap();

    private List path = new ArrayList();

    public RenderAttributesImpl() {
        String a = ClassUtil.getPackageName(RenderAttributesImpl.class)
                .replace('.', '/')
                + "/renderAttributes.xls";
        path.add(a);
    }

    public void initialize() {
        for (final Iterator it = getPath().iterator(); it.hasNext();) {
            final String path = (String) it.next();
            initWorkBook(createWorkbook(path));
        }
    }

    private void initWorkBook(final HSSFWorkbook workbook) {
        final HSSFSheet sheet = workbook.getSheetAt(0);
        final HSSFRow componentFamilyRow = sheet.getRow(0);
        final HSSFRow rendererTypeRow = sheet.getRow(1);
        for (short colIndex = 1;; colIndex++) {
            final HSSFCell componentFamilyCell = componentFamilyRow
                    .getCell(colIndex);
            final HSSFCell rendererTypeCell = rendererTypeRow.getCell(colIndex);
            if (componentFamilyCell == null || rendererTypeCell == null) {
                break;
            }
            final List names = new ArrayList();
            for (int rowIndex = 2;; rowIndex++) {
                final HSSFRow row = sheet.getRow(rowIndex);
                if (row == null) {
                    break;
                }
                final HSSFCell cell = row.getCell(colIndex);
                if (cell == null) {
                    continue;
                }
                final String value = getValueAsString(cell);
                if (RENDER_ON.equals(value)) {
                    final String name = (String) row.getCell((short) 0)
                            .getStringCellValue().trim();
                    names.add(name);
                }
            }
            final String componentFamily = componentFamilyCell
                    .getStringCellValue().trim();
            final String rendererType = rendererTypeCell.getStringCellValue()
                    .trim();
            final String key = createKey(componentFamily, rendererType);
            map.put(key, names.toArray(new String[names.size()]));
        }
    }

    private HSSFWorkbook createWorkbook(String path) {
        final InputStream is = ResourceUtil.getResourceAsStream(path);
        try {
            return new HSSFWorkbook(is);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private String getValueAsString(HSSFCell cell) {
        Object value = getValue(cell);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public String[] getAttributeNames(String componentFamily,
            String rendererType) {
        final String[] names = (String[]) map.get(createKey(componentFamily,
                rendererType));
        return names;
    }

    private String createKey(String componentFamily, String rendererType) {
        return componentFamily + "\f" + rendererType;
    }

    // TODO refactor
    public Object getValue(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
            final double numericCellValue = cell.getNumericCellValue();
            if (isInt(numericCellValue)) {
                int num = (int) numericCellValue;
                return new BigDecimal(Integer.toString(num));
            }
            return new BigDecimal(Double.toString(numericCellValue));
        case HSSFCell.CELL_TYPE_STRING:
            String s = cell.getStringCellValue();
            if (s != null) {
                s = StringUtil.rtrim(s);
            }
            if ("".equals(s)) {
                s = null;
            }
            return s;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            boolean b = cell.getBooleanCellValue();
            return Boolean.valueOf(b);
        default:
            return null;
        }
    }

    private boolean isInt(final double numericCellValue) {
        return ((int) numericCellValue) == numericCellValue;
    }

    public void addPath(String path) {
        this.path.add(path);
    }

    public List getPath() {
        return path;
    }

    public void setPath(List path) {
        this.path = path;
    }

}
