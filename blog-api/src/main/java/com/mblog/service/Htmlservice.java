package com.mblog.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

@Service
public class Htmlservice {

  public String markdownToHtml(String markdown) {
    Parser parser = Parser.builder().build();
    Node document = parser.parse(markdown);
    return HtmlRenderer.builder().build().render(document);
  }
}
