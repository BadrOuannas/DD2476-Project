47572
https://raw.githubusercontent.com/spring-projects/spring-boot/master/spring-boot-project/spring-boot/src/test/java/org/springframework/boot/LazyInitializationExcludeFilterTests.java
/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.config.BeanDefinition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link LazyInitializationExcludeFilter}.
 *
 * @author Phillip Webb
 */
class LazyInitializationExcludeFilterTests {

	@Test
	void forBeanTypesMatchesTypes() {
		LazyInitializationExcludeFilter filter = LazyInitializationExcludeFilter.forBeanTypes(CharSequence.class,
				Number.class);
		String beanName = "test";
		BeanDefinition beanDefinition = mock(BeanDefinition.class);
		assertThat(filter.isExcluded(beanName, beanDefinition, CharSequence.class)).isTrue();
		assertThat(filter.isExcluded(beanName, beanDefinition, String.class)).isTrue();
		assertThat(filter.isExcluded(beanName, beanDefinition, StringBuilder.class)).isTrue();
		assertThat(filter.isExcluded(beanName, beanDefinition, Number.class)).isTrue();
		assertThat(filter.isExcluded(beanName, beanDefinition, Long.class)).isTrue();
		assertThat(filter.isExcluded(beanName, beanDefinition, Boolean.class)).isFalse();
	}

}