package main;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import statistics.StatisticsComposite;

public class StatisticsFrame {
	private final Display display;
	private final Shell shell;
	private final Menu mainMenu;
	
	public StatisticsFrame() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Statistics Frame");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
		shell.setSize(600, 300);
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
		StatisticsFrame tf=new StatisticsFrame();

		// use this to quickly test Composites
		//new YourComposite(tf.getShell(), SWT.NONE);
		new StatisticsComposite(tf.getShell(), SWT.NONE);
		

		// runs testing frame
		tf.run();
	}
}
