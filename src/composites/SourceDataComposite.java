package composites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.json.simple.parser.ParseException;

import main.ReadJsonLogFile;
import main.TestingFrame;
import util.Util;

public class SourceDataComposite extends GeneralComposite {
	private MenuItem fileMenuItem;
	private Menu fileMenu;
	private MenuItem mntmOpenDataFile;
	private MenuItem openMessagesFlatMenuItem;
	private ListViewer listViewer;
	private List<String> messages;

	public SourceDataComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void createContent(Composite content, int style) {
		content.setLayout(new FillLayout(SWT.HORIZONTAL));

		fileMenuItem = new MenuItem(mainMenuBar, SWT.CASCADE);
		fileMenuItem.setText("File");
		fileMenu = new Menu(fileMenuItem);
		fileMenuItem.setMenu(fileMenu);

		addOpenJsonDataFileMenuItem(fileMenu);
		addOpenFlatDataFileMenuItem(fileMenu);

		listViewer = new ListViewer(content, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new ColumnLabelProvider());
		
		this.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				if (mntmOpenDataFile != null) {
					mntmOpenDataFile.dispose();
				}
				if (openMessagesFlatMenuItem != null) {
					openMessagesFlatMenuItem.dispose();
				}
				if (fileMenuItem != null){
					if(fileMenu != null){
						fileMenu.dispose();
					}
					fileMenuItem.dispose();
				}
			}
		});
	}

	private void addOpenJsonDataFileMenuItem(Menu menu) {
		mntmOpenDataFile = new MenuItem(menu, SWT.NONE);
		mntmOpenDataFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				String fileName = fd.open();
				if (fileName != null) {
					try {
						System.out.println(fileName);
						ReadJsonLogFile.readJsonLogFile(fileName);
						messages = ReadJsonLogFile.getMessages();
						setInput(messages);
						nextButton.setEnabled(true);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", Util.getStackTrace(e1));
					} catch (ParseException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error while parsing file", Util.getStackTrace(e1));
					}
				}
			}
		});
		mntmOpenDataFile.setText("Open JSON data file");
	}

	private void addOpenFlatDataFileMenuItem(Menu menu) {
		openMessagesFlatMenuItem = new MenuItem(menu, SWT.NONE);
		openMessagesFlatMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				String fileName = fd.open();
				if (fileName != null) {
					try {
						System.out.println(fileName);
						messages = Files.readAllLines(Paths.get(fileName));
						setInput(messages);
						nextButton.setEnabled(true);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", Util.getStackTrace(e1));
					}
				}
			}
		});
		openMessagesFlatMenuItem.setText("Open flat data file");
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setInput(List<String> list) {
		messages = list;
		listViewer.setInput(list);
	}

	@Override
	protected void nextPressed() {
		if(messages == null){
			throw new IllegalStateException("messages in null");
		}
		messages=Util.removeDuplicatesFromStringList(messages);
		TestingFrame.messages=messages;
		
		Composite parent = getParent();
		this.dispose();
		
		SimilarMessagesComposite smc=new SimilarMessagesComposite(parent, SWT.NONE);
		smc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		smc.setInput(Arrays.asList(new String[]{"one", "two","three"}));
		smc.setMessages(messages);
		smc.setPlaceholdersRoot(TestingFrame.placeholdersRoot);


	}

}
