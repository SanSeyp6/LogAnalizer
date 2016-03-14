package parse.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.simple.parser.ParseException;

import old.ParseMessagesComposite;
import old.ParseMessagesComposite.Entry;
import util.JsonReadWriteUtils;
import util.ParseMessage;
import util.Util;

public class StandaloneParseComposite extends Composite {
	private Text text;
	private Text text_1;
	private String jsonMessagesFileName;
	private String templatesFileName;
	private String unparsedMessagesFileName;
	private String unparsedHead100FileName;
	private List<String> messages;
	private List<String> templates;
	private Text text_2;
	private Text text_3;
	
	private int totalMessages;
	private int parsedMessagesCount;
	private int unparsedMessagesCount;
	private Set<String> unparsedMessages;
	private List<Entry> parsedMessagesEntries;
	private Text text_4;

	
	public StandaloneParseComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));
		
		Label lblJsonMessagesFile = new Label(this, SWT.NONE);
		lblJsonMessagesFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblJsonMessagesFile.setText("JSON Messages File");
		
		text = new Text(this, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				jsonMessagesFileName = text.getText();
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnExplore = new Button(this, SWT.NONE);
		btnExplore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jsonMessagesFileName = new FileDialog(getShell()).open();
				if(jsonMessagesFileName !=null){
					text.setText(jsonMessagesFileName);	
				}
			}
		});
		btnExplore.setText("Explore");
		
		Label lblTemplatesFile = new Label(this, SWT.NONE);
		lblTemplatesFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTemplatesFile.setText("Templates file");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				templatesFileName = text_1.getText();
			}
		});
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnExplore_1 = new Button(this, SWT.NONE);
		btnExplore_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				templatesFileName = new FileDialog(getShell()).open();
				if(templatesFileName !=null){
					text_1.setText(templatesFileName);	
				}
			}
		});
		btnExplore_1.setText("Explore");
		
		Label lblUnparsedMessagesFile = new Label(this, SWT.NONE);
		lblUnparsedMessagesFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUnparsedMessagesFile.setText("Unparsed Messages File");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_2.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				unparsedMessagesFileName = text_2.getText();
			}
		});
		
		Button btnExplore_2 = new Button(this, SWT.NONE);
		btnExplore_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				unparsedMessagesFileName = new FileDialog(getShell(), SWT.SAVE).open();
				if(unparsedMessagesFileName !=null){
					text_2.setText(unparsedMessagesFileName);	
				}
			}
		});
		btnExplore_2.setText("Explore");
		
		Label lblUnparsedHead = new Label(this, SWT.NONE);
		lblUnparsedHead.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblUnparsedHead.setText("Unparsed Head 100");
		
		text_4 = new Text(this, SWT.BORDER);
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_4.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				unparsedHead100FileName = text_4.getText();
			}
		});
		
		Button btnExplore_3 = new Button(this, SWT.NONE);
		btnExplore_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				unparsedHead100FileName = new FileDialog(getShell(), SWT.SAVE).open();
				if(unparsedHead100FileName !=null){
					text_4.setText(unparsedHead100FileName);	
				}
			}
		});
		btnExplore_3.setText("Explore");
		
		Button btnRunParsing = new Button(this, SWT.NONE);
		btnRunParsing.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("jsonMessagesFileName: "+ jsonMessagesFileName);
				System.out.println("templatesFileName: "+ templatesFileName);
				text_3.setText("Processing in progress...");
				getShell().getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						try{
							readFiles(jsonMessagesFileName, templatesFileName);
							parseMessages(messages, templates);
							Files.write(Paths.get(unparsedMessagesFileName), unparsedMessages, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
							Files.write(Paths.get(unparsedHead100FileName), getFirstMessagesFromSet(unparsedMessages, 100), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
							text_3.setText("Total messages processed: " + totalMessages + "\nParsed messages: " + parsedMessagesCount + "\nUnparsed Messages: " + unparsedMessagesCount);
							} catch (Exception ex) {
								text_3.setText(Util.getStackTrace(ex));
								ex.printStackTrace();
							}
						messages = null;
						templates = null;
						System.gc();
						}
					}
				);
			}
		});
		btnRunParsing.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		btnRunParsing.setText("Run parsing");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		lblNewLabel.setText("Results");
		
		text_3 = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		text_3.setEditable(false);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
	}

	private void readFiles(String jsonMessagesFileName, String templatesFileName) throws IOException, ParseException{
			JsonReadWriteUtils.readJsonLogFile(jsonMessagesFileName);
			messages = JsonReadWriteUtils.getMessages();
			templates = Files.readAllLines(Paths.get(templatesFileName));
	}
	
	public List<Entry> parseMessages(List<String> messages, List<String> templates) {
		parsedMessagesEntries = new ArrayList<ParseMessagesComposite.Entry>(messages.size());
		unparsedMessages = new LinkedHashSet<String>(messages.size()/2);
		Map<String, String> map;
		Entry entry;
		boolean parsed;

		/*
		 * Запоминаем количество всех сообщений, разобранных и неразобранных
		 */
		totalMessages = messages.size();
		parsedMessagesCount = 0;
		unparsedMessagesCount = 0;

		for (String message : messages) {
			parsed = false;
			for (String template : templates) {
				map = ParseMessage.parseMessageAgainstTemplate(message, template);
				if (!map.isEmpty()) {
					parsed = true;
					entry = new Entry(message, template, map);
					parsedMessagesCount++;
					parsedMessagesEntries.add(entry);
					break;
				}
			}
			if (!parsed) {
				unparsedMessagesCount++;
				unparsedMessages.add(message);
			}
		}

		System.out.println("Total messages processed: " + totalMessages + "\nParsed messages: " + parsedMessagesCount + "\nUnparsed Messages: " + unparsedMessagesCount);

		return parsedMessagesEntries;
	}
	
	private List<String> getFirstMessagesFromSet(Set<String> set, int n){
		List<String> list = new LinkedList<String>();
		Iterator<String> iterator = set.iterator();
		String s;
		
		for(int i=0; i<n && iterator.hasNext(); i++){
			s=iterator.next();
			list.add(s);
		}
		return list;
	}

}
