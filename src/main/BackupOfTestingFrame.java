package main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;


public class BackupOfTestingFrame {
	private final Display display;
	private final Shell shell;
	private final Menu mainMenu;
	private Button nextButton;
	private Composite activeComposite;
	
	public BackupOfTestingFrame() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Testing JFace/SWT Frame");
		shell.setLayout(new GridLayout(1, false));
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
		nextButton = new Button(shell, SWT.NONE);
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (activeComposite instanceof SourceDataComposite){
					
				} else {
					nextButton.setEnabled(false);
				}
			}
		});
		nextButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		nextButton.setText("Next step");

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	public static void main(String[] args) {
		BackupOfTestingFrame tf=new BackupOfTestingFrame();
		List<String> messages;
		
		// use this to quickly test Composites
		//new YourComposite(tf.getShell(), SWT.NONE);

/*		
		SourceDataComposite sdt=new SourceDataComposite(tf.getShell(), SWT.NONE);
		sdt.setInput(Arrays.asList(new String[]{"one", "two","three"}));
		sdt.addOpenDataFileMenuItem(tf.getMainMenu());
		
*/


/*		
		try {
			ReadJsonLogFile.readJsonLogFile("/home/sansey/Магистратура/Курс 2/ВКР/my_all_messages_20151228/my_all_messages2.json");
			messages = ReadJsonLogFile.getMessages();
			messages = SourceDataComposite.removeDuplicatesFromStringList(messages);
			SimilarMessagesComposite smc=new SimilarMessagesComposite(tf.getShell(), SWT.NONE);
			smc.setInput(Arrays.asList(new String[]{"one", "two","three"}));
			smc.setMessages(messages);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/

/*		
		List<String> templates = new ArrayList<String> (Arrays.asList(new String[]{"one", "two","three"}));
		TemplatesComposite tc=new TemplatesComposite(tf.getShell(), SWT.NONE, templates);
		tc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
*/		
		// runs testing frame
		tf.run();
	}
}
