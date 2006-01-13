package org.seasar.teeda.core.config.assembler.impl;

import java.util.Map;

import org.seasar.teeda.core.config.assembler.ConverterChildAssembler;
import org.seasar.teeda.core.config.assembler.ConvertersAssembler;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.util.ClassUtil;

public class SimpleConvertersAssembler extends ConvertersAssembler {

    private ConverterChildAssembler converterIdAssembler_;
    private ConverterChildAssembler conveterClassAssembler_;
    public SimpleConvertersAssembler(Map convertersByClass, Map convertersById) {
        super(convertersByClass, convertersById);
    }

    protected void setupChildAssembler() {
        converterIdAssembler_ = new ConverterChildAssembler(getConvertersById()){
            protected void doAssemble(String converterId, ConverterElement converterElement) {
                String converterClass = converterElement.getConverterClass();
                if(converterClass != null){
                    getApplication().addConverter(converterId, converterClass);
                }
            }
        };
        conveterClassAssembler_ = new ConverterChildAssembler(getConvertersByClass()){
            protected void doAssemble(String converterForClass, ConverterElement converterElement) {
                Class converterClazz = ClassUtil.forName(converterForClass);
                String converterClassName = converterElement.getConverterClass();
                if(converterClassName != null){
                    getApplication().addConverter(converterClazz, converterClassName);
                }
            }
        };
    }

    public void assemble() {
        converterIdAssembler_.assemble();
        conveterClassAssembler_.assemble();
    }

}
