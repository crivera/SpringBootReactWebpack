package com.config.sitemesh;

import java.io.IOException;

import org.sitemesh.DecoratorSelector;
import org.sitemesh.content.Content;
import org.sitemesh.webapp.WebAppContext;

public class MetaTagDecoratorSelector implements DecoratorSelector<WebAppContext> {

  private final DecoratorSelector<WebAppContext> fallbackSelector;

  public MetaTagDecoratorSelector(DecoratorSelector<WebAppContext> fallbackSelector) {
    this.fallbackSelector = fallbackSelector;
  }

  @Override
  public String[] selectDecoratorPaths(Content content, WebAppContext context) throws IOException {

    // Fetch <meta name=decorator> value.
    // The default HTML processor already extracts these into 'meta.NAME' properties.
    String decorator = content.getExtractedProperties().getChild("meta").getChild("decorator").getValue();

    if (decorator != null) {
      // If present, return it.
      // Multiple chained decorators can be specified using commas.
      return decorator.split(",");
    } else {
      // Otherwise, fallback to the standard configuration.
      return fallbackSelector.selectDecoratorPaths(content, context);
    }
  }
}
