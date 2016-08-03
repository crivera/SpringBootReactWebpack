package com.config.sitemesh;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;

public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

  @Override
  protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
    builder.setCustomDecoratorSelector(new MetaTagDecoratorSelector(builder.getDecoratorSelector()));
    builder.addTagRuleBundle(new Sm2TagRuleBundle());
  }
}
