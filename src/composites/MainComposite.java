package composites;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import main.ParseMessagesComposite;
import main.SimilarMessagesComposite.TreeNode;

public class MainComposite extends Composite{

	private Button nextButton;
	private Composite activeComposite;
	private static List<String> sourceMessages;
	private static List<String> messages;
	private static List<String> templates;
	private static List<ParseMessagesComposite.Entry> entries;
	private static TreeNode placeholdersRoot = new TreeNode("placeholdersRoot");
	private MenuItem openTemplatesMenuItem;
	private Menu mainMenuBar;
	
	public MainComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		mainMenuBar = getShell().getMenuBar();
		if (mainMenuBar == null){
			mainMenuBar = new Menu(getShell(), SWT.BAR);
			getShell().setMenuBar(mainMenuBar);
			//throw new RuntimeException("Main menu bar is not set for current window/shell!");
		}
		
		nextButton = new Button(this, SWT.NONE);
		nextButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		nextButton.setText("Next step");

		SourceDataComposite sdt=new SourceDataComposite(this, SWT.NONE);
		sdt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		tf.setActiveComposite(sdt);
//		tf.getActiveComposite().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		tf.setActiveComposite(sdt);

		

		
		/*					
		openTemplatesMenuItem = new MenuItem(mainMenuBar, SWT.NONE);
		openTemplatesMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(((SourceDataComposite)activeComposite).getMessages() == null){
					MessageDialog.openInformation(getShell(), "Open templates file", "Messages file should be opened first");
					return;
				}
				FileDialog fd = new FileDialog(getShell());
				String fileName = fd.open();
			if (fileName != null) {
					try {
						System.out.println(fileName);
						templates=Files.readAllLines(Paths.get(fileName));
						// Находим все плэйсхолдеры и заносим их в TreeNode-s
						computePlaceholders(templates, placeholdersRoot);
						
						//Теперь надо бы пропустить поиск похожих
						if (activeComposite instanceof SourceDataComposite){
							if(sourceMessages==null){
								sourceMessages=((SourceDataComposite)activeComposite).getMessages();
								messages=sourceMessages;
							}
							messages=SourceDataComposite.removeDuplicatesFromStringList(messages);
							activeComposite.dispose();
							
							TemplatesComposite tc=new TemplatesComposite(shell, SWT.NONE, templates);
							tc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
							tc.addSaveTemplatesToFileMenuItem(mainMenu);

							activeComposite = tc;
							shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
							shell.setSize(shell.getSize().x-1,shell.getSize().y);
						}
						
						// И в случае полного успеха убираем этот MenuItem
						openTemplatesMenuItem.dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
						MessageDialog.openError(getShell(), "Error opening file", SourceDataComposite.getStackTrace(e1));
					}
				}
				
			}
		});
		openTemplatesMenuItem.setText("Open templates file");
*/
	//////////////////////////
/*		
		
		nextButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (activeComposite instanceof SourceDataComposite){
					System.out.println("activeComposite instanceof SourceDataComposite");
					if(sourceMessages==null){
						sourceMessages=((SourceDataComposite)activeComposite).getMessages();
						messages=sourceMessages;
					}
					messages=SourceDataComposite.removeDuplicatesFromStringList(messages);
					activeComposite.dispose();
					openTemplatesMenuItem.dispose();
					
					SimilarMessagesComposite smc=new SimilarMessagesComposite(shell, SWT.NONE);
					smc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					smc.setInput(Arrays.asList(new String[]{"one", "two","three"}));
					smc.setMessages(messages);
					smc.setPlaceholdersRoot(placeholdersRoot);

					activeComposite = smc;
					shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
					shell.setSize(shell.getSize().x-1,shell.getSize().y);
				} else if (activeComposite instanceof SimilarMessagesComposite) {
					System.out.println("activeComposite instanceof SimilarMessagesComposite");
					templates = ((SimilarMessagesComposite)activeComposite).getTemplates();
					placeholdersRoot = ((SimilarMessagesComposite)activeComposite).getPlaceholdersRoot();
					activeComposite.dispose();
					
					TemplatesComposite tc=new TemplatesComposite(shell, SWT.NONE, templates);
					tc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					tc.addSaveTemplatesToFileMenuItem(mainMenu);

					activeComposite = tc;
					shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
					shell.setSize(shell.getSize().x-1,shell.getSize().y);
				} else if (activeComposite instanceof TemplatesComposite) {
					System.out.println("activeComposite instanceof TemplatesComposite");
					templates = ((TemplatesComposite)activeComposite).getTemplates();
					computePlaceholders(templates, placeholdersRoot);
					activeComposite.dispose();
					
					ParseMessagesComposite pmc=new ParseMessagesComposite(shell, SWT.NONE);
					pmc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					entries=pmc.parseMessages(messages, templates);
					pmc.setInput(entries);
					addValuesToPlaceholders(entries, placeholdersRoot);
					
					activeComposite = pmc;
					shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
					shell.setSize(shell.getSize().x-1,shell.getSize().y);
				} else if (activeComposite instanceof ParseMessagesComposite) {
					System.out.println("activeComposite instanceof ParseMessagesComposite");
					activeComposite.dispose();
					
					InverseReplacementComposite irc=new InverseReplacementComposite(shell, SWT.NONE);
					irc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					irc.buildInverseMap(entries);
					irc.replaceValuesWithNames(messages);
					
					activeComposite = irc;
					shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
					shell.setSize(shell.getSize().x-1,shell.getSize().y);
				} else if (activeComposite instanceof InverseReplacementComposite) {
					System.out.println("activeComposite instanceof InverseReplacementComposite");
					((InverseReplacementComposite)activeComposite).replaceCheckedMessages(messages);
					activeComposite.dispose();
					
					SourceDataComposite sdc=new SourceDataComposite(shell, SWT.NONE);
					sdc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
					sdc.setInput(messages);
					
					activeComposite = sdc;
					shell.setSize(shell.getSize().x+1,shell.getSize().y); // такой хак для перерисовки окна. Ибо redraw не работает(
					shell.setSize(shell.getSize().x-1,shell.getSize().y);
				} else {
					throw new RuntimeException("unknown current composite");
				}
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
		});
*/
		
		///////////////////////
	}

	public Composite getActiveComposite() {
		return activeComposite;
	}
	
	public void setActiveComposite(Composite activeComposite) {
		this.activeComposite = activeComposite;
	}

}
