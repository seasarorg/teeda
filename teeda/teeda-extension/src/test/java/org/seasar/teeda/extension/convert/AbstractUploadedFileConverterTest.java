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
package org.seasar.teeda.extension.convert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.component.html.THtmlInputFile;
import org.seasar.teeda.extension.util.UploadedFile;

/**
 * @author koichik
 */
public abstract class AbstractUploadedFileConverterTest extends TeedaTestCase {

    protected abstract AbstractUploadedFileConverter getConverter();

    protected abstract Object getValue();

    public void testGetAsString() throws Exception {
        AbstractUploadedFileConverter converter = getConverter();
        MockUploadedFile uploadedFile = new MockUploadedFile();
        uploadedFile.setString("aaa");
        THtmlInputFile inputFile = new THtmlInputFile();
        inputFile.setSubmittedValue(uploadedFile);
        String s = converter.getAsString(getFacesContext(), inputFile,
                getValue());
        assertEquals("", s);
    }

    public void testConvertTargetPointed() throws Exception {
        AbstractUploadedFileConverter converter = getConverter();
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        MockUploadedFile uploadedFile = new MockUploadedFile();
        uploadedFile.setString("aaa");
        uploadedFile.setStoreLocation(new File("aaa"));
        THtmlInputFile inputFile = new THtmlInputFile();
        inputFile.setSubmittedValue(uploadedFile);
        Object o = converter.getAsObject(getFacesContext(), inputFile, "");
        assertNotNull(o);
    }

    public void testConvertTargetNotPointed() throws Exception {
        AbstractUploadedFileConverter converter = getConverter();
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "bbb");
        THtmlInputFile inputFile = new THtmlInputFile();
        inputFile.setSubmittedValue(new MockUploadedFile());
        Object o = converter.getAsObject(getFacesContext(), inputFile, "");
        assertNull(o);
    }

    public static class MockFileItem implements FileItem {
        protected String name;

        /**
         * @param name
         */
        public MockFileItem(String name) {
            this.name = name;
        }

        public void delete() {
        }

        public byte[] get() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public String getFieldName() {
            return null;
        }

        public InputStream getInputStream() throws IOException {
            return null;
        }

        public String getName() {
            return name;
        }

        public OutputStream getOutputStream() throws IOException {
            return null;
        }

        public long getSize() {
            return 0;
        }

        public String getString() {
            return null;
        }

        public String getString(String encoding)
                throws UnsupportedEncodingException {
            return null;
        }

        public boolean isFormField() {
            return false;
        }

        public boolean isInMemory() {
            return false;
        }

        public void setFieldName(String name) {
        }

        public void setFormField(boolean state) {
        }

        public void write(File file) throws Exception {
        }
    }

    public static class MockUploadedFile implements UploadedFile {

        protected String originalName;

        protected String contentType;

        protected long size;

        protected boolean isInMemory;

        protected String string;

        protected File storeLocation;

        /**
         * @return Returns the name.
         */
        public String getName() {
            final File file = new File(originalName);
            return file.getName();
        }

        /**
         * @return Returns the originalName.
         */
        public String getOriginalName() {
            return originalName;
        }

        /**
         * @param originalName The originalName to set.
         */
        public void setOriginalName(String originalName) {
            this.originalName = originalName;
        }

        /**
         * @return Returns the size.
         */
        public long getSize() {
            return size;
        }

        /**
         * @param size The size to set.
         */
        public void setSize(long size) {
            this.size = size;
        }

        /**
         * @return Returns the contentType.
         */
        public String getContentType() {
            return contentType;
        }

        /**
         * @param contentType The contentType to set.
         */
        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        /**
         * @return Returns the isInMemory.
         */
        public boolean isInMemory() {
            return isInMemory;
        }

        /**
         * @param isInMemory The isInMemory to set.
         */
        public void setInMemory(boolean isInMemory) {
            this.isInMemory = isInMemory;
        }

        public byte[] get() {
            return string.getBytes();
        }

        /**
         * @return Returns the string.
         */
        public String getString() {
            return string;
        }

        public String getString(String encoding)
                throws UnsupportedEncodingException {
            return string;
        }

        /**
         * @param string The string to set.
         */
        public void setString(String string) {
            this.string = string;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(get());
        }

        /**
         * @return Returns the storeLocation.
         */
        public File getStoreLocation() {
            return storeLocation;
        }

        /**
         * @param storeLocation The storeLocation to set.
         */
        public void setStoreLocation(File storeLocation) {
            this.storeLocation = storeLocation;
        }

        public void write(File file) throws Exception {
        }

        public void delete() {
        }
    }

}
