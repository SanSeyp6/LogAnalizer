package old;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;

import util.Metrics;
import util.StringComparison;

public class SimilarMessagesComposite extends Composite {
	public static final int METRIC_THRESHOLD = 20;

	private ListViewer listViewer;
	private TreeViewer placeholdersTreeViewer;
	private List<String> messages;
	private Button changeGroupButton;
	private Iterator<String> iterator;
	private Text offeredTemplateText;
	private Text userTemplateText;
	private List<String> templates;
	private TreeNode placeholdersRoot;

	public SimilarMessagesComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(this, SWT.NONE);

		Composite composite = new Composite(sashForm, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));

		listViewer = new ListViewer(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		org.eclipse.swt.widgets.List list = listViewer.getList();
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label offeredTemplateLabel = new Label(composite, SWT.NONE);
		offeredTemplateLabel.setText("Offered template:");

		offeredTemplateText = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.CANCEL);
		offeredTemplateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		offeredTemplateText.setText("Test");
		offeredTemplateText.setEditable(false);

		Label lblUserTemplate = new Label(composite, SWT.NONE);
		lblUserTemplate.setText("User template:");

		userTemplateText = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.CANCEL);
		userTemplateText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		listViewer.setContentProvider(ArrayContentProvider.getInstance());
		listViewer.setLabelProvider(new ColumnLabelProvider());

		Composite composite_1 = new Composite(sashForm, SWT.BORDER);
		composite_1.setLayout(new GridLayout(1, false));

		placeholdersTreeViewer = new TreeViewer(composite_1, SWT.BORDER);
		Tree tree = placeholdersTreeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		// TreeNodeContentProvider работает только с массивами! Никаких списков
		// Поэтому я освоил JFace DataBinding было непросто, но вроде разобрался 

		Button insertPlacehoderButton = new Button(composite_1, SWT.NONE);
		insertPlacehoderButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (placeholdersTreeViewer.getSelection().isEmpty()) {
					return;
				}
				if (placeholdersTreeViewer.getSelection() instanceof StructuredSelection) {
					StructuredSelection selection = (StructuredSelection) placeholdersTreeViewer.getSelection();
					String placeholder = ((TreeNode)selection.getFirstElement()).getText();
					int caretPosition = userTemplateText.getCaretPosition();
					if (userTemplateText.getSelectionCount() != 0) {
						StringBuilder sb = new StringBuilder(userTemplateText.getText());
						sb.replace(userTemplateText.getSelection().x, userTemplateText.getSelection().y, "{" + placeholder + "}");
						userTemplateText.setText(sb.toString());
						// нету setCaretPosition(), +2 так как ещё скобки
						userTemplateText.setSelection(caretPosition + placeholder.length() + 2); 
					} else {
						StringBuilder sb = new StringBuilder(userTemplateText.getText());
						sb.insert(userTemplateText.getSelection().x, "{" + placeholder + "}");
						userTemplateText.setText(sb.toString());
						userTemplateText.setSelection(caretPosition + placeholder.length() + 2);
					}
				} else {
					System.out.println("placeholdersTreeViewer.getSelection().getClass(): " + placeholdersTreeViewer.getSelection().getClass());
				}
			}
		});
		insertPlacehoderButton.setText("Insert placehoder");

		changeGroupButton = new Button(composite_1, SWT.NONE);
		changeGroupButton.setText("Change group");
		changeGroupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("changeGroupButton pressed");
				userTemplateText.setText("");
				List<String> similarMessages = getNextSimilarGroup();
				while (similarMessages.size() == 1) {
					similarMessages = getNextSimilarGroup();
					System.out.println("similarMessages.size==1");
				}
				if (!similarMessages.isEmpty()) {
					setInput(similarMessages);
				} else {
					listViewer.setInput(new String[] { "No more similar groups. Seems you are stuck here. =)" });
				}
			}
		});

		sashForm.setWeights(new int[] { 3, 1 });
	}

	public SimilarMessagesComposite(Composite parent, int style, java.util.List<String> messages) {
		this(parent, style);
		this.messages = messages;
	}

	public void setMessages(java.util.List<String> messages) {
		this.messages = messages;
	}

	public void setInput(java.util.List<String> list) {
		listViewer.setInput(list);
	}

/*	
	public void setPlaceholders(List<TreeNode> placeholders) {
		this.placeholders = placeholders;
		placeholdersTreeViewer.setInput(placeholders);
	}

	public List<TreeNode> getPlaceholders() {
		return placeholders;
	}
*/
	
	public TreeNode getPlaceholdersRoot() {
		return placeholdersRoot;
	}
	
	public void setPlaceholdersRoot(TreeNode placeholdersRoot) {
		this.placeholdersRoot = placeholdersRoot;
		ViewerSupport.bind(placeholdersTreeViewer, this.placeholdersRoot, BeanProperties.set("children", TreeNode.class), 
				BeanProperties.value(TreeNode.class,  "text"));
	}
	
	public java.util.List<String> getNextSimilarGroup() {
		List<String> similarStrings = new ArrayList<String>();
		if (iterator == null) {
			iterator = messages.iterator();
		}
		if (iterator.hasNext()) {
			String currentMessage = iterator.next();

			// Построение списка похожих строк
			for (String s : messages) {
				if (Metrics.checkMetric(currentMessage, s, Metrics.LevenshteinDistance, METRIC_THRESHOLD)) {
					similarStrings.add(s);
				}
			}
			// Нахождение LCS для списка похожих строк
			String lcs = similarStrings.get(0);
			for (String s : similarStrings) {
				lcs = StringComparison.computeLCSubsequence(lcs, s);
			}

			// На основе полученного LCS строим шаблоны сообщений по списку
			// похожих сообщений.
			List<String> templateList = new ArrayList<String>();
			for (String s : similarStrings) {
				templateList.add(getTemplate(s, lcs));
			}

			// объединяем шаблоны, получая один общий шаблон
			String unitedTemplate = uniteTemplates(templateList);
			offeredTemplateText.setText(unitedTemplate);
		} else {
			changeGroupButton.setEnabled(false);
		}

		return similarStrings;
	}

	private String getTemplate(String s, String lcs) {
		StringBuilder sb = new StringBuilder();
		int i = 0, k = 0;

		while (i < s.length() && k < lcs.length()) {
			if (s.charAt(i) == lcs.charAt(k)) {
				sb.append(lcs.charAt(k));
				i++;
				k++;
			} else {
				sb.append("{&}");
				while (s.charAt(i) != lcs.charAt(k)) {
					i++;
				}
			}
		}

		if (i < s.length()) {
			sb.append("{&}");
		}

		return sb.toString();
	}

	private String uniteTemplates(List<String> templates) {
		String unitedTemplate = templates.get(0);
		String tmp;
		int i = 0, j = 0;
		int placeholdersCount = 0;

		for (String s : templates) {
			if (!unitedTemplate.equals(s)) {
				i = 0;
				j = 0;
				StringBuilder sb = new StringBuilder();
				while (i < unitedTemplate.length() && j < s.length()) {
					if (unitedTemplate.charAt(i) == s.charAt(j)) {
						sb.append(unitedTemplate.charAt(i));
						i++;
						j++;
					} else {
						if (unitedTemplate.charAt(i) == '{' && unitedTemplate.charAt(i + 1) == '&' && unitedTemplate.charAt(i + 2) == '}') {
							sb.append("{&}");
							i += 3;
						} else if (s.charAt(j) == '{' && s.charAt(j + 1) == '&' && s.charAt(j + 2) == '}') {
							sb.append("{&}");
							j += 3;
						} else {
							System.err.println("Templates:" + templates);
							System.err.println("UnitedTemplate:" + unitedTemplate);
							System.err.println("CurrentTemplate:" + s);
							System.err.println("i:" + i);
							System.err.println("j:" + j);
							throw new IllegalStateException("Unexpected chars in templates");
						}
					}
				}
				// если что-то длинней другого, добавляем плейсхолдер
				if ((i < unitedTemplate.length() && j == s.length()) || (i == unitedTemplate.length() && j < s.length())) {
					sb.append("{&}");
				}
				unitedTemplate = sb.toString();
			}
		}

		// now replace placehoders with numbers in united template
		do {
			tmp = unitedTemplate;
			unitedTemplate = tmp.replaceFirst("\\{&\\}", "{" + placeholdersCount + "}");
			placeholdersCount++;
		} while (!tmp.equals(unitedTemplate));

		return unitedTemplate;
	}

	private List<String> generateTemplates() {
		List<String> templates = new ArrayList<String>();
		if (userTemplateText.getText().trim().isEmpty()) {
			throw new RuntimeException("User template shuldn't be null or empty");
		}
		// в первую очередь в шаблоны добавляем то, что написал человек
		templates.add(userTemplateText.getText());

		// идея в том, чтобы искать плейсхолдеры вида {placehoder_name} внутри
		// сообщений, они там могут быть.
		// если есть, то перед нами шаблон, который должен быть исопользован при
		// разборе.
		// шаблоны будут уникальны, так как в начале производится удаление
		// дублей
		// шаблоны вида {1}, {2} и прочие цифры выбираться не должны
		Pattern pattern = Pattern.compile(".*\\{\\w*\\}.*");

		for (String s : messages) {
			Matcher matcher = pattern.matcher(s);
			if (matcher.matches()) {
				templates.add(s);
			}

		}
		return templates;
	}

	public List<String> getTemplates() {
		if (templates == null) {
			templates = generateTemplates();
		}

		return templates;
	}

	public static class TreeNode{
		private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
		private String text;
		private final Set<TreeNode> children;
		
		public TreeNode(String text) {
			this.text = text;
			this.children = new HashSet<TreeNode>();
		}

		public void addPropertyChangeListener(PropertyChangeListener listener) {
			changeSupport.addPropertyChangeListener(listener);
		}

		public void removePropertyChangeListener(PropertyChangeListener listener) {
			changeSupport.removePropertyChangeListener(listener);
		}

		protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
			changeSupport.firePropertyChange(propertyName, oldValue, newValue);
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			firePropertyChange("text", this.text, this.text = text);
		}

		public void addChild(TreeNode child){
			if(children.add(child)){
				firePropertyChange("children", null, child);
			}
		}
		
		public void removeChild(TreeNode child){
			if(children.remove(child)){
				firePropertyChange("children", child, null);
			}
		}
		
		/**
		 * Для добавления/удаления в список используйте методы {@link #addChild(TreeNode)} и {@link #removeChild(TreeNode)}
		 * @return копия списка потомков
		 */
		public Set<TreeNode> getChildren() {
			return children;
		}
		
		public int getChildrenSize(){
			return children.size();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((text == null) ? 0 : text.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof TreeNode)) {
				return false;
			}
			TreeNode other = (TreeNode) obj;
			if (text == null) {
				if (other.text != null) {
					return false;
				}
			} else if (!text.equals(other.text)) {
				return false;
			}
			return true;
		}
		
		
	}

}
