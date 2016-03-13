package composites;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import main.TestingFrame;
import util.ParseMessage;
import util.Util;

public class InverseReplacementComposite extends GeneralComposite {
	private Map<String, Set<String>> inverseMap;
	private Map<String, String> replacements;
	private TableViewer tableViewer;
	private CheckboxTableViewer checkboxTableViewer;
	private Table table;
	private Table table_2;

	public InverseReplacementComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void createContent(Composite content, int style) {
		content.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabFolder tabFolder = new TabFolder(content, SWT.NONE);

		TabItem tbtmCheckReplacedValues_1 = new TabItem(tabFolder, SWT.NONE);
		tbtmCheckReplacedValues_1.setText("Check Replaced Values");

		Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmCheckReplacedValues_1.setControl(composite);
		composite.setLayout(new GridLayout(2, false));

		checkboxTableViewer = CheckboxTableViewer.newCheckList(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		checkboxTableViewer.setAllChecked(false);
		checkboxTableViewer.setContentProvider(ArrayContentProvider.getInstance());
		checkboxTableViewer.setComparator(new ViewerComparator()); //By default, it will sort based on the toString() 
		table_2 = checkboxTableViewer.getTable();
		table_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		table_2.setSize(444, 265);
		table_2.setLinesVisible(true);
		table_2.setHeaderVisible(true);

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
		TableColumn tblclmnBeforeReplacement_1 = tableViewerColumn_5.getColumn();
		tblclmnBeforeReplacement_1.setWidth(100);
		tblclmnBeforeReplacement_1.setText("Before Replacement");
		tableViewerColumn_5.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry<?, ?>) {
					Map.Entry<String, String> entry = (Entry<String, String>) element;
					return entry.getKey();
				}
				return super.getText(element);
			}
		});

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(checkboxTableViewer, SWT.NONE);
		TableColumn tblclmnAfterReplacement_1 = tableViewerColumn_6.getColumn();
		tblclmnAfterReplacement_1.setWidth(100);
		tblclmnAfterReplacement_1.setText("After Replacement");
		tableViewerColumn_6.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry<?, ?>) {
					Map.Entry<String, String> entry = (Entry<String, String>) element;
					return entry.getValue();
				}
				return super.getText(element);
			}
		});

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnNewButton.setText("Check All");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkboxTableViewer.setAllChecked(true);
			}
		});

		Button btnUncheckAll = new Button(composite, SWT.NONE);
		btnUncheckAll.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1));
		btnUncheckAll.setText("Uncheck All");
		btnUncheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkboxTableViewer.setAllChecked(false);
			}
		});

		TabItem tbtmInversemap = new TabItem(tabFolder, SWT.NONE);
		tbtmInversemap.setText("Inverse Map");

		tableViewer = new TableViewer(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		tbtmInversemap.setControl(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnValue = tableViewerColumn.getColumn();
		tblclmnValue.setWidth(100);
		tblclmnValue.setText("Value");
		tableViewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry<?, ?>) {
					Map.Entry<String, Set<String>> entry = (Entry<String, Set<String>>) element;
					return entry.getKey();
				} else {
					return super.getText(element);
				}
			}
		});

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnAttributes = tableViewerColumn_1.getColumn();
		tblclmnAttributes.setWidth(100);
		tblclmnAttributes.setText("Attributes");
		tableViewerColumn_1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Map.Entry<?, ?>) {
					Map.Entry<String, Set<String>> entry = (Entry<String, Set<String>>) element;
					return entry.getValue().toString();
				} else {
					return super.getText(element);
				}
			}
		});

		nextButton.setEnabled(true);
	}

	// TODO вообще Map<String,String> parsedMessagesCandidates и List<ParseMessagesComposite.Entry> entries дублируют информацию. Надо что-то придумать 
	public void setInput(List<ParseMessagesComposite.Entry> entries, Set<String> unparsedMessages){
		inverseMap = buildInverseMap(entries);
		Map<String, String> tmpMap = buildUnparsedMessagesReplacementCandidates(unparsedMessages);
		replacements = buildParsedMessagesReplacementCandidates(entries);
		replacements.putAll(tmpMap);
		
		System.out.println("List<ParseMessagesComposite.Entry> entries: "+ entries.size() +"; UnparsedMessagesReplacementCandidates:"+ tmpMap.size());
		
		tableViewer.setInput(inverseMap.entrySet());
		for (TableColumn column : table.getColumns()) {
			column.pack();
		}
		
		checkboxTableViewer.setInput(replacements.entrySet());
		for (TableColumn column : table_2.getColumns()) {
			column.pack();
		}
	}

	
	private Map<String, Set<String>> buildInverseMap(List<ParseMessagesComposite.Entry> entries) {
		Map<String, Set<String>> returnMap = new HashMap<String, Set<String>>();
		Map<String, String> valuesMap;
		Set<String> attributesSet; 

		for (ParseMessagesComposite.Entry pEntry : entries) {
			valuesMap = pEntry.getParsedValues();
			for (Entry<String, String> entry : valuesMap.entrySet()) {
				if(!Util.isInteger(entry.getKey())) {
					attributesSet = returnMap.get(entry.getValue());
					
					// случилось, что пустые значения добавляются в placeholdersRoot и подставляются в обратной замене
					// Так я от этого защищаюсь.
					if(entry.getValue().isEmpty()){
//						System.out.println("entry.getValue() is \"\"");
						continue;
					}
					
					if (attributesSet == null) {
						attributesSet = new HashSet<String>();
						returnMap.put(entry.getValue(), attributesSet);
						attributesSet.add(entry.getKey());
					} else {
						attributesSet.add(entry.getKey());
					}
				}
			}
		}

		return returnMap;
	}
	
	/**
	 * Метод на основе построенных обратных соответствий и переданного списка неразобранных сообщений
	 * строит кандидаты-соответствия для замены.
	 * @param unparsedMessages
	 */
	// TODO надо переименовать метод, так как он плохо соответстсвует тому, что на самом деле происходит
	private Map<String, String> buildUnparsedMessagesReplacementCandidates(Set<String> unparsedMessages) {
		Map <String, String> replacementCandidates = new HashMap<String, String>();
		String template;
		String regexp;

		for(String message: unparsedMessages){
			for (Entry<String, Set<String>> entry : inverseMap.entrySet()) {
				// надо понять как-то, что нам надо сейчас заменять или же
				// не нужно
				if (entry.getValue().size() == 1) {
					/*
					 * Текущее решение заключается в том, чтобы просто втупую
					 * заменять, а ошибки обнаруживать и исправлять руками,
					 * "дообучая" разбор =)
					 */
					regexp = ParseMessage.escapeSpecialRegexChars(entry.getKey());
					template = message.replaceAll(regexp, "{" + entry.getValue().iterator().next() + "}");
					if (!message.equals(template)) {
						replacementCandidates.put(message, template);
					}
				}
			}
		}
		
		return replacementCandidates;
	}

	/**
	 * Метод строит на основе разобранных сообщений обратные соответствия "сообщение - шаблон". 
	 * При этом учитывается, что в шаблоне не должно быть нумерованных плейсхолдеров, только именованые. 
	 * Нумерованные плейсхолдеры, оставшиеся после генерации шаблона, заменяются на значение - 
	 * типа ничего не было. 
	 * @param entries
	 * @return
	 */
	private Map<String, String> buildParsedMessagesReplacementCandidates(List<ParseMessagesComposite.Entry> entries) {
		Map <String, String> replacementCandidates = new HashMap<String, String>();
		Map<String, String> valuesMap;
		String template; 

		for (ParseMessagesComposite.Entry pEntry : entries) {
			valuesMap = pEntry.getParsedValues();
			template = pEntry.getTemplate();
			for (Entry<String, String> entry : valuesMap.entrySet()) {
				if(Util.isInteger(entry.getKey())){
					template = template.replace("{"+entry.getKey()+"}", valuesMap.get(entry.getKey()));
				} 
			}
			replacementCandidates.put(pEntry.getMessage(), template);
		}
		return replacementCandidates;
	}

	
	@Override
	protected void nextPressed() {
		replaceCheckedMessages(TestingFrame.messages);

		Composite parent = getParent();
		this.dispose();

		SourceDataComposite sdc = new SourceDataComposite(parent, SWT.NONE);
		sdc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sdc.setInput(TestingFrame.messages);
	}

	/**
	 * Заменяет в переданном списке сообщения, отмеченные в таблице, шаблоны 
	 * @param messages
	 */
	public void replaceCheckedMessages(List<String> messages) {
		Object[] checkedEntriesArray = checkboxTableViewer.getCheckedElements();
		Map.Entry<String, String> entry;
		for (Object o : checkedEntriesArray) {
			if (o instanceof Map.Entry<?, ?>) {
				entry = (Map.Entry<String, String>) o;
				messages.set(messages.indexOf(entry.getKey()), entry.getValue());
			} else {
				throw new RuntimeException("object class is: " + o.getClass());
			}
		}
	}

}
