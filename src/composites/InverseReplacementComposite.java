package composites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
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

public class InverseReplacementComposite extends GeneralComposite {
	private Map<String, List<String>> inverseMap;
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
					Map.Entry<String, List<String>> entry = (Entry<String, List<String>>) element;
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
					Map.Entry<String, List<String>> entry = (Entry<String, List<String>>) element;
					return entry.getValue().toString();
				} else {
					return super.getText(element);
				}
			}
		});

		nextButton.setEnabled(true);
	}

	public void replaceValuesWithNames(List<String> messages) {
		replacements = new HashMap<String, String>();
		String oldMessage, newMessage;
		// int oldMessageIndex;
		String regexp;

		for (int i = 0; i < messages.size(); i++) {
			for (Entry<String, List<String>> entry : inverseMap.entrySet()) {
				// надо понять как-то, что нам надо сейчас заменять или же
				// не нужно
				if (entry.getValue().size() == 1) {
					/*
					 * Пока решение такое. Делаем разбивку на токены с
					 * сохранением разделителей как токены для обеих строк. И
					 * если набор токенов из inverseMap полностью содержится в
					 * том же порядке внутри набора токенов из template, то
					 * тогда выполняем замену. При этом походу нельзя делать
					 * replaceAll, надо будет делать replaceFirst и повторять
					 * процедуру При этом в replace-ах надо делать эскейпинг
					 * моих строк. Для этого мною сделан ParseMessage
					 */

					/*
					 * Текущее решение заключается в том, чтобы просто втупую
					 * заменять и ошибки обнаруживать и исправлять руками,
					 * "дообучая" разбор =)
					 */
					regexp = ParseMessage.escapeSpecialRegexChars(entry.getKey());
					oldMessage = messages.get(i);
					newMessage = oldMessage.replaceAll(regexp, "{" + entry.getValue().get(0) + "}");
					// messages.set(i, messages.get(i).replaceAll(regexp, "{" +
					// entry.getValue().get(0) + "}"));
					if (!oldMessage.equals(newMessage)) {
						replacements.put(oldMessage, newMessage);
					}
					continue;
				} else {
					// System.out.println("Ambigious value: " + entry.getKey());
				}
			}
		}

		// return replacements;
		checkboxTableViewer.setInput(replacements.entrySet());
		for (TableColumn column : table_2.getColumns()) {
			column.pack();
		}
	}

	public void buildInverseMap(List<ParseMessagesComposite.Entry> entries) {
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		Map<String, String> valuesMap;
		List<String> list;

		for (ParseMessagesComposite.Entry pEntry : entries) {
			valuesMap = pEntry.getParsedValues();
			for (Entry<String, String> entry : valuesMap.entrySet()) {
				// TODO надо найти нормальный способ проверки, что
				// строка является числом.
				try {
					Integer.parseInt(entry.getKey());
				} catch (NumberFormatException e) {
					list = returnMap.get(entry.getValue());
					if (list == null) {
						list = new ArrayList<String>();
						returnMap.put(entry.getValue(), list);
						list.add(entry.getKey());
					} else {
						if (!list.contains(entry.getKey())) {
							list.add(entry.getKey());
						}
					}
				}
			}
		}

		inverseMap = returnMap;
		tableViewer.setInput(inverseMap.entrySet());
		for (TableColumn column : table.getColumns()) {
			column.pack();
		}
	}

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

	@Override
	protected void nextPressed() {
		replaceCheckedMessages(TestingFrame.messages);

		Composite parent = getParent();
		this.dispose();

		SourceDataComposite sdc = new SourceDataComposite(parent, SWT.NONE);
		sdc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		sdc.setInput(TestingFrame.messages);
	}

}
