8
https://raw.githubusercontent.com/Cognifide/aem-stubs/master/core/src/main/java/com/cognifide/aem/stubs/core/StubReload.java
package com.cognifide.aem.stubs.core;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.NumberFormat;
import java.util.Locale;

class StubReload {

  private final long startedAt = System.currentTimeMillis();

  protected int scriptsTotal;

  protected int scriptsFailed;

  protected int mappingsTotal;

  protected int mappingsFailed;

  public int scriptsSucceeded() {
    return scriptsTotal - scriptsFailed;
  }

  public int mappingsSucceeded() {
    return mappingsTotal - mappingsFailed;
  }

  public String scriptsPercent() {
    return formatPercent((double) (scriptsSucceeded()) / ((double) scriptsTotal));
  }

  public String mappingsPercent() {
    return formatPercent((double) (mappingsSucceeded()) / ((double) mappingsTotal));
  }

  private String formatPercent(double value) {
    return NumberFormat.getPercentInstance(Locale.US).format(value);
  }

  public String duration() {
    return DurationFormatUtils.formatDurationHMS(System.currentTimeMillis() - startedAt);
  }

  public String summary() {
    return String.format("AEM Stubs reloaded in %s | Mappings: %s/%s=%s | Scripts: %s/%s=%s",
      duration(),
      mappingsSucceeded(), mappingsTotal, mappingsPercent(),
      scriptsSucceeded(), scriptsTotal, scriptsPercent()
    );
  }

  @Override
  public String toString() {
    return summary();
  }
}
