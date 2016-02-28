package main;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;


public class EditTemplateDialog extends Dialog {
	private Text text;
	private String currentValue;
	private String newValue;

	public EditTemplateDialog(Shell parentShell, String currentValue) {
		super(parentShell);
		this.currentValue = currentValue;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		
		Label lblEnterNewValue = new Label(container, SWT.NONE);
		lblEnterNewValue.setText("Enter new value:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText(currentValue);
		return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit template");
	}
	
	@Override
	protected void okPressed() {
		newValue = text.getText();
		super.okPressed();
	}
	
	public String getValue(){
		return newValue;
	}
}
