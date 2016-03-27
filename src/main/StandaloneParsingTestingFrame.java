package main;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import parse.standalone.StandaloneParseComposite;

public class StandaloneParsingTestingFrame {
	private final Display display;
	private final Shell shell;
	private final Menu mainMenu;
	
	public StandaloneParsingTestingFrame() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Testing JFace/SWT Frame");
		shell.setSize(500, 380);
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
		StandaloneParsingTestingFrame tf=new StandaloneParsingTestingFrame();

		// use this to quickly test Composites
		//new YourComposite(tf.getShell(), SWT.NONE);
		
		new StandaloneParseComposite(tf.getShell(), SWT.NONE);

		// runs testing frame
		tf.run();
	}
}
