package net.edsv;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SidePanel extends Panel {
	//private Label status;// = new Label("Status");
	private static int counter = 0;
	public SidePanel(String id) {
		super(id);
		
		setDefaultModel(new CompoundPropertyModel(this));
		
		Form f = new Form("myForm"){
			private Label status;
			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				status = new Label("status", Model.of(""));
				add(status);
				
			}
			@Override
			protected void onSubmit() {
				// TODO Auto-generated method stub
				super.onSubmit();
				//IModel<?> ml = getDefaultModel().filter((p) -> p.getName().equals("Jane"));
				//.filter((m) -> m.getName().equals("status"));
				status.setDefaultModelObject("OK" + counter++);
			}
			
		};
		
		//f.add(new Label("status", Model.of("")));
		add(f);
		add(new Link<String>("goToNewPage") {
			@Override
			public void onClick() {
				setResponsePage(CalcPage.class);
			}
			});
		Label label = new Label("result", "  ...?");
		label.setOutputMarkupId(true);
	
		AjaxLink al;
		add(al = new AjaxLink<String>("ajaxLink"){
	        @Override	
	        public void onClick(AjaxRequestTarget target) {
	            //modify the model of a label and refresh it on client side
	            //label.setDefaultModelObject("Another value 4 label.");
	        	String gets = label.getDefaultModelObjectAsString();
	        	label.setDefaultModelObject(gets + " Jaxx-" + target.getLastFocusedElementId());
	        	//Adds a component to the list of components to be rendered!
	            target.add(label);//add(label);
	            //target.getLastFocusedElementId();
	        }
	});
		//al.setOutputMarkupId(true);
		al.add(label);
		
/*		add(new AjaxLink("ajaxLink") {
	        @Override	
	        public void onClick(AjaxRequestTarget target) {
	            target.appendJavaScript(";alert('another listener ajax event');");
	        }
	});*/
	
		
	}
	
	@Override
	public void onEvent(IEvent event){
		event.getType().name();
		
	}

}
