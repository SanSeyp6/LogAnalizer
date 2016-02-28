package main;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class TemplatesComposite extends Composite {

	private ListViewer listViewer;
	private org.eclipse.swt.widgets.List list;
	private List<String> templates;
	private MenuItem mntmSaveTemplates;
	
	protected TemplatesComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		listViewer = new ListViewer(this, SWT.BORDER | SWT.V_SCROLL);
		list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new ColumnLabelProvider());
		
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		btnNewButton.setText("Edit selected template");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddTemplateDialog dialog=new AddTemplateDialog(getShell());
				int result=dialog.open();
				if (result==Dialog.OK ){
					templates.add(dialog.getValue());	
					listViewer.refresh();
				}
			}
		});
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		btnNewButton_1.setText("Add template");
		
		Button btnNewButton_2 = new Button(this, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex=list.getSelectionIndex();
				if(selectionIndex ==-1) {
					return;
				}
				boolean sure = MessageDialog.openConfirm(getShell(), "Remove template", "Are you sure to remove selected template?");
				if(sure){
					templates.remove(selectionIndex);
					listViewer.refresh();
				}
			}
		});
		btnNewButton_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnNewButton_2.setText("Remove Template");
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex=list.getSelectionIndex();
				if(selectionIndex ==-1) {
					return;
				}
				EditTemplateDialog dialog=new EditTemplateDialog(getShell(), templates.get(selectionIndex));
				int result=dialog.open();
				if (result==Dialog.OK ){
					templates.set(selectionIndex, dialog.getValue());	
					listViewer.refresh();
				}
			}
		});
		
		this.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				mntmSaveTemplates.dispose();
			}
		});
	}
	
	public TemplatesComposite(Composite parent, int style, List<String> templates){
		this(parent,style);
		this.templates=templates;
		listViewer.setInput(templates);
	}

	public List<String> getTemplates() {
		return templates;
	}
	
	public void addSaveTemplatesToFileMenuItem(Menu menu) {
		mntmSaveTemplates= new MenuItem(menu, SWT.NONE);
		mntmSaveTemplates.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				String fileName = fd.open();
				if (fileName != null) {
					try {
						System.out.println(fileName);
						Collections.sort(templates);
						Files.write(Paths.get(fileName), templates, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", SourceDataComposite.getStackTrace(e1));
					}
				}
			}
		});
		mntmSaveTemplates.setText("Save templates to file");
	}

}
