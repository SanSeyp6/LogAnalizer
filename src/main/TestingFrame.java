package main;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import composites.ParseMessagesComposite;
import composites.SimilarMessagesComposite;
import composites.SimilarMessagesComposite.TreeNode;
import composites.SourceDataComposite;

public class TestingFrame {
	private final Display display;
	private final Shell shell;
	private final Menu mainMenu;
	
	////////////
	public static List<String> messages;
	public static List<String> templates;
	public static List<ParseMessagesComposite.Entry> entries;
	public static SimilarMessagesComposite.TreeNode placeholdersRoot = new TreeNode("placeholdersRoot");
	/** Соответствие message-template */
	public static Map<String, String> messageTemplateMap;
	public static Set<String> unparsedMessages;
//	public static List<String> parsedMessages;
	
	public TestingFrame() {
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
		TestingFrame tf=new TestingFrame();

		// use this to quickly test Composites
		//new YourComposite(tf.getShell(), SWT.NONE);
		
		new SourceDataComposite(tf.getShell(), SWT.NONE);

		// runs testing frame
		tf.run();
	}
}
