package main;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import composites.SourceDataComposite;

public class BackupTestingFrame {
	private final Display display;
	private final Shell shell;
	private final Menu mainMenu;
	
	public BackupTestingFrame() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Testing JFace/SWT Frame");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
	}
	
	public Shell getShell(){
		return shell;
	}
	
	public Menu getMainMenu(){
		return mainMenu;
	}
	
	public void run(){
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public static void main(String[] args) {
		BackupTestingFrame tf=new BackupTestingFrame();

		// use this to quickly test Composites
		//new YourComposite(tf.getShell(), SWT.NONE);
		
		new SourceDataComposite(tf.getShell(), SWT.NONE);

		// runs testing frame
		tf.run();
	}
}
