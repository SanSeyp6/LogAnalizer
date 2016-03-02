package composites;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import main.TestingFrame;
import util.ParseMessage;

public class ParseMessagesComposite extends GeneralComposite {
	private Table table;
	private TableViewer tableViewer;
	private int totalMessages;
	private int parsedMessages;
	private int unparsedMessages;
	private Label countersLabel;

	public ParseMessagesComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void createContent(Composite content, int style) {
		content.setLayout(new GridLayout(1, false));
		
		countersLabel = new Label(content, SWT.NONE);
		countersLabel.setText("");
		
		tableViewer = new TableViewer(content, SWT.BORDER);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnMessage = tableViewerColumn.getColumn();
		tblclmnMessage.setWidth(100);
		tblclmnMessage.setText("Message");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof Entry){
					return ((Entry)element).getMessage();
				} 
				return super.getText(element);
			}
		});
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnParsedValues = tableViewerColumn_1.getColumn();
		tblclmnParsedValues.setWidth(100);
		tblclmnParsedValues.setText("Parsed values");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof Entry){
					Entry entry = (Entry) element;
					if(entry.getParsedValues().isEmpty()){
						return "Unparsed";
					}
					StringBuilder sb = new StringBuilder();
					for (String s : entry.getParsedValues().keySet()) {
						sb.append(s);
						sb.append(": ");
						sb.append(entry.getParsedValues().get(s));
						sb.append(";  ");
					}
					return sb.toString();
				} else {
					return super.getText(element);
				}
			}
		});
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnTemplate = tableViewerColumn_2.getColumn();
		tblclmnTemplate.setWidth(100);
		tblclmnTemplate.setText("Template");
		tableViewerColumn_2.setLabelProvider(new ColumnLabelProvider(){
			@Override
			public String getText(Object element) {
				if(element instanceof Entry){
					if(((Entry)element).getParsedValues().isEmpty()){
						return "Unparsed";
					}
					return ((Entry)element).getTemplate();
				} 
				return super.getText(element);
			}
		});
		
		nextButton.setEnabled(true);
	}
	
	public void setInput(List<Entry> entries){
		countersLabel.setText("Total messages processed: "+ totalMessages + 
				"\nParsed messages: "+ parsedMessages + "\nUnparsed Messages: " + unparsedMessages);
		tableViewer.setInput(entries);
		for (TableColumn column : table.getColumns()){
			column.pack();
		}
	}

	public List<Entry> parseMessages(List<String> messages, List<String> templates){
		List<Entry> returnList=new ArrayList<ParseMessagesComposite.Entry>();
		Map<String, String> map;
		Entry entry;
		boolean parsed;
		
		/*
		 * Запоминаем количество всех сообщений, разобранных и неразобранных
		 */
		totalMessages = messages.size();
		parsedMessages=0;
		unparsedMessages=0;
		
		for(String message: messages){
			parsed=false;
			for(String template: templates){
				map = ParseMessage.parseMessageAgainstTemplate(message, template);
				if (!map.isEmpty()) {
					System.out.println(map);
					parsed=true;
					entry=new Entry(message, template, map);
					returnList.add(entry);
					break;
				} 
			}
			if(parsed){
				parsedMessages++;
			} else {
				unparsedMessages++;
			}
		}
		
		return returnList;
	}

	@Override
	protected void nextPressed() {
		Composite parent = getParent();
		this.dispose();
		
		InverseReplacementComposite irc=new InverseReplacementComposite(parent, SWT.NONE);
		irc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		irc.buildInverseMap(TestingFrame.entries);
		irc.replaceValuesWithNames(TestingFrame.messages);
		parent.layout(); // SWT caches layout, so we clear that cache in this way
	}

	public static class Entry{
		private String message;
		private String template;
		private Map<String,String> parsedValues;
		
		public Entry(String message, String template, Map<String, String> parsedValues) {
			this.message = message;
			this.template = template;
			this.parsedValues = parsedValues;
		}
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getTemplate() {
			return template;
		}
		public void setTemplate(String template) {
			this.template = template;
		}
		public Map<String, String> getParsedValues() {
			return parsedValues;
		}
		public void setParsedValues(Map<String, String> parsedValues) {
			this.parsedValues = parsedValues;
		}
	} 

}
