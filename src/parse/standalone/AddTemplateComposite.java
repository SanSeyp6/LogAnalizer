package parse.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import ru.hse.performance.PerformanceUtil;
import util.JsonReadWriteUtils;
import util.ParseMessage;
import util.Util;

public class AddTemplateComposite extends Composite {
	private Text messagesText;
	private Text templatesText;
	private Text statusText;
	private String jsonMessagesFileName;
	private String templatesFileName;
	private String parsedMessagesFileName;
	private int parsedMessagesCount;
	private int unparsedMessagesCount;
	private int totalMessagesCount;
	private Text parsedMessagesText;

	public AddTemplateComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		Label lblParsedJsonMessages = new Label(this, SWT.NONE);
		lblParsedJsonMessages.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblParsedJsonMessages.setText("JSON Messages File");

		messagesText = new Text(this, SWT.BORDER);
		messagesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnExplore = new Button(this, SWT.NONE);
		btnExplore.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnExplore.setText("Explore");
		btnExplore.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jsonMessagesFileName = new FileDialog(getShell()).open();
				if (jsonMessagesFileName != null && !jsonMessagesFileName.trim().isEmpty()) {
					messagesText.setText(jsonMessagesFileName);
				}
			}
		});

		Label lblResultsJsonFile = new Label(this, SWT.NONE);
		lblResultsJsonFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblResultsJsonFile.setText("Templates File");

		templatesText = new Text(this, SWT.BORDER);
		templatesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		Button resultsSaveAs = new Button(this, SWT.NONE);
		resultsSaveAs.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		resultsSaveAs.setText("Explore");
		resultsSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				templatesFileName = new FileDialog(getShell()).open();
				if (templatesFileName != null && !templatesFileName.trim().isEmpty()) {
					templatesText.setText(templatesFileName);
				}
			}
		});
		
		Label lblParsedJsonMessages_1 = new Label(this, SWT.NONE);
		lblParsedJsonMessages_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblParsedJsonMessages_1.setText("Parsed JSON Messages File");
		
		parsedMessagesText = new Text(this, SWT.BORDER);
		parsedMessagesText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnSaveAs = new Button(this, SWT.NONE);
		btnSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				parsedMessagesFileName = new FileDialog(getShell(), SWT.SAVE).open();
				if (parsedMessagesFileName != null && !parsedMessagesFileName.trim().isEmpty()) {
					parsedMessagesText.setText(parsedMessagesFileName);
				}
			}
		});
		btnSaveAs.setText("Save As");

		Button runComputation = new Button(this, SWT.NONE);
		runComputation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				run();
			}
		});
		runComputation.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		runComputation.setText("Run message parsing");

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));

		statusText = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		statusText.setEditable(false);
		statusText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
	}

	private void run() {
		if (jsonMessagesFileName == null || jsonMessagesFileName.trim().isEmpty() || templatesFileName == null
				|| templatesFileName.trim().isEmpty() || parsedMessagesFileName == null || parsedMessagesFileName.trim().isEmpty()) {
			statusText.setText("Invalid file names. Check file paths and provide the correct ones, then restart computations");
		}
		statusText.setText("Processing in progress...");
		getShell().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					PerformanceUtil.initialize();
					
					JsonReadWriteUtils.readJsonLogFile(jsonMessagesFileName);
					List<String> templates=Files.readAllLines(Paths.get(templatesFileName));
					parseMessagesAndAddTemplates(JsonReadWriteUtils.getJsonArray(), templates);
					JsonReadWriteUtils.saveJsonArrayToFile(JsonReadWriteUtils.getJsonArray(), parsedMessagesFileName);
					System.out.println("Total messages processed: " + totalMessagesCount + "\nParsed messages: " + parsedMessagesCount + "\nUnparsed Messages: " + unparsedMessagesCount);
					statusText.setText("Finished!\n");

					PerformanceUtil.printTotalTime();
				} catch (IOException e) {
					statusText.setText(Util.getStackTrace(e));
					e.printStackTrace();
				} catch (ParseException e) {
					statusText.setText(Util.getStackTrace(e));
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Метод производит разбор переданного списка сообщений (как JSON-объектов)
	 * с помощью переданных шаблонов. Если сообщение было разборано, то в его
	 * JSON-объект добавляется ключ "template" в со значением равным
	 * разобравшему шаблону. Если сообщение не разобрано, то ключ не добавляется.
	 * 
	 * @param messages
	 * @param templates
	 */
	private void parseMessagesAndAddTemplates(JSONArray messages, List<String> templates) {
		boolean parsed;
		String msg;
		Map<String, String> placeholderValueMap;
		JSONObject message;
		
		parsedMessagesCount = 0;
		unparsedMessagesCount = 0;
		totalMessagesCount = messages.size();
		
		for (Object o : messages) {
			message = (JSONObject) o;
			parsed = false;
			msg = ((String)message.get("msg"));
			for (String template : templates) {
				placeholderValueMap = ParseMessage.parseMessageAgainstTemplate(msg, template);
				if (!placeholderValueMap.isEmpty()) {
					parsed = true;
					message.put("template", template);
					parsedMessagesCount++;
					break;
				}
			}
			if (!parsed) {
				unparsedMessagesCount++;
			}
		}
	}

}
