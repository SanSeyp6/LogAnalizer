package composites;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

public abstract class GeneralComposite extends Composite {

	protected Button nextButton;
	private Composite content;
	protected Menu mainMenuBar;

	public GeneralComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		createMainMenuBar();
		content = new Composite(this, SWT.NONE);
		content.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		nextButton = new Button(this, SWT.NONE);
		nextButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		nextButton.setText("Next step");
		nextButton.setEnabled(false);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				nextPressed();
			}
		});

		// Делаем это в конце, чтобы nextButton был доступен и разметка была правильной - всё на content
		createContent(content, SWT.NONE);
	}

	abstract protected void createContent(Composite content, int style);

	/**
	 * Возвращает основную панель меню в lazy-style (если ещё не создано, то создаёт его)
	 * @return основную панель меню
	 */
	private void createMainMenuBar(){
		if (getShell().getMenuBar() == null){
			mainMenuBar = new Menu(getShell(), SWT.BAR);
			getShell().setMenuBar(mainMenuBar);
		} else {
			mainMenuBar = getShell().getMenuBar();
		}
	}
	
	abstract protected void nextPressed();
}
