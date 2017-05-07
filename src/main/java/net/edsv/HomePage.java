package net.edsv;

import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.BorderPanel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.Application;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebPage;
//import org.apache.wicket.markup.html.panel.*;


public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
		
		// TODO Add your page's components here
		//this.
		add(new Label("Hey_ya","yo hooo! Hoooray!!!"));
		add(new SidePanel("side_panel"));
		RequestCycle r;
		Application ap;
		MarkupContainer mc =(MarkupContainer)this;
		
		
	}
}

/*class MyForm extends Form {

	public MyForm(String id) {
		super(id);

	}

	@Override
	protected void onSubmit() {
		// TODO Auto-generated method stub
		super.onSubmit();
	}
	
}*/