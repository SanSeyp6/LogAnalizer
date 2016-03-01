package composites;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import composites.ParseMessagesComposite.Entry;
import composites.SimilarMessagesComposite.TreeNode;
import main.TestingFrame;
import util.Util;

public class TemplatesComposite extends GeneralComposite {
	private ListViewer listViewer;
	private org.eclipse.swt.widgets.List list;
	private List<String> templates;
	private MenuItem mntmSaveTemplates;

	public TemplatesComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void createContent(Composite content, int style) {
		content.setLayout(new GridLayout(2, false));
		
		addSaveTemplatesToFileMenuItem(mainMenuBar);
		
		listViewer = new ListViewer(content, SWT.BORDER | SWT.V_SCROLL);
		list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new ColumnLabelProvider());
		
		
		Button btnNewButton = new Button(content, SWT.NONE);
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnNewButton.setText("Edit selected template");
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

		
		Button btnNewButton_1 = new Button(content, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		btnNewButton_1.setText("Add template");
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				AddTemplateDialog dialog=new AddTemplateDialog(getShell());
				int result=dialog.open();
				if (result==Dialog.OK ){
					templates.add(dialog.getValue());	
					listViewer.refresh();
				}
				checkAndModifyNextButton();
			}
		});
		
		Button btnNewButton_2 = new Button(content, SWT.NONE);
		btnNewButton_2.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1));
		btnNewButton_2.setText("Remove Template");
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
				checkAndModifyNextButton();
			}
		});
		
		this.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				mntmSaveTemplates.dispose();
			}
		});
	}

	public void setInput(List<String> templates){
		this.templates=templates;
		listViewer.setInput(templates);
		checkAndModifyNextButton();
	}
	
	public List<String> getTemplates() {
		return templates;
	}
	
	private void addSaveTemplatesToFileMenuItem(Menu menu) {
		mntmSaveTemplates= new MenuItem(menu, SWT.NONE);
		mntmSaveTemplates.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell(), SWT.SAVE);
				String fileName = fd.open();
				if (fileName != null) {
					try {
						System.out.println(fileName);
						Collections.sort(templates);
						Files.write(Paths.get(fileName), templates, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", Util.getStackTrace(e1));
					}
				}
			}
		});
		mntmSaveTemplates.setText("Save templates to file");
	}

	private void checkAndModifyNextButton(){
		if (this.templates.isEmpty()) {
			nextButton.setEnabled(false);
		} else {
			nextButton.setEnabled(true);
		}
	}
	
	@Override
	protected void nextPressed() {
		System.out.println("activeComposite instanceof TemplatesComposite");
		TestingFrame.templates = getTemplates();
		Util.computePlaceholders(templates, TestingFrame.placeholdersRoot);
		
		Composite parent = getParent();
		this.dispose();
		
		ParseMessagesComposite pmc=new ParseMessagesComposite(parent, SWT.NONE);
		pmc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		TestingFrame.entries=pmc.parseMessages(TestingFrame.messages, templates);
		pmc.setInput(TestingFrame.entries);
		addValuesToPlaceholders(TestingFrame.entries, TestingFrame.placeholdersRoot);
	}
	
	private void addValuesToPlaceholders(List<Entry> entries, TreeNode placeholdersRoot) {
		Set<TreeNode> set = placeholdersRoot.getChildren();
		TreeNode newTreeNode;
		for(Entry entry: entries){
			for(Map.Entry<String, String> mapEntry: entry.getParsedValues().entrySet()){
				for(TreeNode treeNode: set){
					if(treeNode.getText().equals(mapEntry.getKey())
							// и защищаемся, чтобы {placeholder} не попадали в дерево как значения
							&& !("{"+treeNode.getText()+"}").equals(mapEntry.getValue())){
						newTreeNode = new TreeNode(mapEntry.getValue());
						treeNode.addChild(newTreeNode);
					}
				}
			}
			
		}
	}

}
