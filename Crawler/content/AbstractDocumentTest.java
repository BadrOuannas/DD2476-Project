https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/abstract-document/src/test/java/com/iluwatar/abstractdocument/AbstractDocumentTest.java
/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.abstractdocument;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * AbstractDocument test class
 */
public class AbstractDocumentTest {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  private class DocumentImplementation extends AbstractDocument {

    DocumentImplementation(Map<String, Object> properties) {
      super(properties);
    }
  }

  private DocumentImplementation document = new DocumentImplementation(new HashMap<>());

  @Test
  public void shouldPutAndGetValue() {
    document.put(KEY, VALUE);
    assertEquals(VALUE, document.get(KEY));
  }

  @Test
  public void shouldRetrieveChildren() {
    var children = List.of(Map.of(), Map.of());

    document.put(KEY, children);

    var childrenStream = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(2, childrenStream.count());
  }

  @Test
  public void shouldRetrieveEmptyStreamForNonExistingChildren() {
    var children = document.children(KEY, DocumentImplementation::new);
    assertNotNull(children);
    assertEquals(0, children.count());
  }

  @Test
  public void shouldIncludePropsInToString() {
    var props = Map.of(KEY, (Object) VALUE);
    var document = new DocumentImplementation(props);
    assertTrue(document.toString().contains(KEY));
    assertTrue(document.toString().contains(VALUE));
  }

}