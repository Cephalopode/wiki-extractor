package preprocessing.wikipedia;

import java.util.Vector;

import preprocessing.util.ChineseUtil;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;

public class FRLinkExtractor extends Extractor {

	public FRLinkExtractor() {
		super();
	}

	public FRLinkExtractor(String xmlName, String outputFile) {
		super();
		setParser(xmlName);
		setWriter(outputFile);

	}

	@Override
	public void extract() {
		try {
			parser.setPageCallback(new PageCallbackHandler() {
				@Override
				public void process(WikiPage page) {
					StringBuilder line = new StringBuilder();
					String title = page.getTitle().replaceAll("\n", "").trim();
					if (!validTitle(title))
						return;
					if (page.isRedirect())
						return;

					line.append(title);
					line.append("\t\t");

					Vector<String> links = page.getLinks();
					if (links != null) {
						for (int i = 0; i < links.size(); i++) {
							String temp = links.get(i).trim();
							line.append(temp + ";");
						}
					}
					write(line.toString().replaceAll("\n", ""));
				}
			});
			parser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
