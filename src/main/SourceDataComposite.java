package main;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.json.simple.parser.ParseException;

public class SourceDataComposite extends Composite {
	private ListViewer listViewer;
	private List<String> messages;
	private MenuItem mntmOpenDataFile;
	private MenuItem openMessagesFlatMenuItem;
	
	public SourceDataComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		listViewer = new ListViewer(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new ColumnLabelProvider());
		
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				if(mntmOpenDataFile !=null){
					mntmOpenDataFile.dispose();
				}
				if(openMessagesFlatMenuItem !=null){
					openMessagesFlatMenuItem.dispose();
				}
			}
		});
	}

	public void setInput(List<String> list) {
		messages = list;
		listViewer.setInput(list);
	}

	public void addOpenDataFileMenuItem(Menu menu) {
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
						//messages=removeDuplicatesFromStringList(messages);
						setInput(messages);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", getStackTrace(e1));
					} catch (ParseException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error while parsing file", getStackTrace(e1));
					}
				}
			}
		});
		mntmOpenDataFile.setText("Open data file");
	}

	public void addOpenFlatDataFileMenuItem(Menu menu) {
		openMessagesFlatMenuItem = new MenuItem(menu, SWT.NONE);
		openMessagesFlatMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				String fileName = fd.open();
				if (fileName != null) {
					try {
						System.out.println(fileName);
						messages=Files.readAllLines(Paths.get(fileName));
						setInput(messages);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", getStackTrace(e1));
					}
				}
			}
		});
		openMessagesFlatMenuItem.setText("Open flat data file");
	}

	
	/**
	 * Преобразует вывод метода {@link Exception#getStackTrace()} в строку для дальнейшего использования
	 * @param ex исключение, вывод которого необходимо получить как строку
	 * @return строка, содержащая вывод метода {@link Exception#getStackTrace()}
	 */
	public static String getStackTrace(Exception ex) {
		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	/**
	 * Удаляет дублирующиеся элементы из переданного списка строк. Возвращает
	 * новый список строк без дублирующихся элементов. Исходный порядок
	 * элементов в возвращаемом списке сохраняется
	 * 
	 * @param list передаваемый список для удаления дублирующихся элементов
	 * @return список без дублирующихся элементов
	 */
	public static List<String> removeDuplicatesFromStringList(List<String> list) {
		Set<String> set = new LinkedHashSet<String>(list);
		return new ArrayList<String>(set);
	}
	
	public List<String> getMessages() {
		return messages;
	}
}
