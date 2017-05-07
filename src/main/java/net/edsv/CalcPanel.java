package net.edsv;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.PackageResourceReference;
//2017, EdSv.
public class CalcPanel extends Panel {
	private Label display;
	private Label hint;
	CalcState state;
	public CalcPanel(String id, IModel<?> model) {
		super(id, model);
	}

	public CalcPanel(String id) {
		super(id);
		state = new CalcState();
		AjaxLink axl;
		add(axl = new AjaxLink<CalcState>("ajax_root", new Model<CalcState>(state)) {
			@Override
			protected AjaxEventBehavior newAjaxEventBehavior(String event) {
				return super.newAjaxEventBehavior(event);
			}
			
			@Override
			protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
				super.updateAjaxAttributes(attributes);
				attributes.getAjaxCallListeners().add(new MyListener());
			}

			@Override
			public void onClick(AjaxRequestTarget target) {
				String elem_id = target.getLastFocusedElementId();
				CalcState m = (CalcState) getDefaultModelObject();
				m.handleInput(elem_id);
				String r = m.getResult();
				String allowa = String.valueOf(m.inputOverflow);
				display.add(AttributeModifier.replace("data-counter", allowa));
				target.add(display);
				target.add(hint);
			}

		});
		add(display = new Label("result", new PropertyModel(state, "result")));
		display.add(new AttributeModifier("data-counter","false"));
		display.setOutputMarkupId(true);
		add(hint = new Label("hint", new PropertyModel(state, "hint")));
		hint.setOutputMarkupId(true);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		// super.renderHead(response);
		PackageResourceReference cssFile = new PackageResourceReference(this.getClass(), "calc_style.css");
		CssHeaderItem chi = CssHeaderItem.forReference(cssFile);
		response.render(chi);
	}

}

class MyListener extends AjaxCallListener {

	@Override
	public CharSequence getPrecondition(Component component) {
		//c -The id of the component that wants to start the AJAX call. But if we need event target id?
		//return "if(attrs['c'] =='kb'){alert('for Kb deny ajax');return;}else{alert('something pass..');}";
		return "if(attrs['c'] =='kb') {if('true' == document.getElementById('display').firstChild.getAttribute('data-counter'))return false;return true;}";
	}

}