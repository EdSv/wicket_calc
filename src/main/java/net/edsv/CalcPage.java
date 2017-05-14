package net.edsv;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CalcPage extends WebPage{

	public CalcPage(PageParameters pp) {
		super(pp);
		add( new CalcPanel("calc"));
	}

}
