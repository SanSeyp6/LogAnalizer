package composites;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;


public class AddTemplateDialog extends Dialog {
	private Text text;
	private String newValue;

	public AddTemplateDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		
		Label lblEnterNewValue = new Label(container, SWT.NONE);
		lblEnterNewValue.setText("Enter value:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText("New template");
		text.selectAll();;
		return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Add template");
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
