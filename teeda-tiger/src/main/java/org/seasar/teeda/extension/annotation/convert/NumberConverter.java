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
package org.seasar.teeda.extension.annotation.convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shot
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD })
@Converter("TNumberConverter")
public @interface NumberConverter {

	String pattern() default "";

	String type() default "";

	String currencyCode() default "";

	String currencySymbol() default "";

	boolean isIntegerOnly() default false;

	boolean isGroupingUsed() default false;

	int maxFractionDigits() default 0;

	int maxIntegerDigits() default 0;

	int minFractionDigits() default 0;

	int minIntegerDigits() default 0;

	String target() default "";

	String objectMessageId() default "";

	String stringMessageId() default "";

}
